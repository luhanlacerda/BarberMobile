package com.github.mavbraz.barbermobile.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.controller.NegocioAgendamento;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Resposta;
import com.github.mavbraz.barbermobile.model.basicas.Servico;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitarServicoFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "solicitarServico";

    private static final int REQUEST_CODE_SERVICOS = 1;

    TextView txtChosenDate;
    TextView txtPreferredTime;

    private List<Servico> mServicos;
    private DateTime mDateTime;
    private DateFormat mDateFormat;
    private DateFormat mTimeFormat;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private Context mContext;
    private SolicitarServicoFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
        if (context instanceof SolicitarServicoFragmentListener) {
            mListener = (SolicitarServicoFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDateTime = new DateTime();

        GregorianCalendar now = new GregorianCalendar();
        mDateFormat = android.text.format.DateFormat.getDateFormat(mContext);
        mTimeFormat = android.text.format.DateFormat.getTimeFormat(mContext);

        mDatePickerDialog = new DatePickerDialog(mContext,this,
                now.get(GregorianCalendar.YEAR),
                now.get(GregorianCalendar.MONTH),
                now.get(GregorianCalendar.DAY_OF_MONTH));

        mTimePickerDialog = new TimePickerDialog(mContext, this,
                now.get(GregorianCalendar.HOUR_OF_DAY),
                now.get(GregorianCalendar.MINUTE),
                true);

        mDatePickerDialog.getDatePicker().setMinDate(now.getTimeInMillis());
        now.add(GregorianCalendar.MONTH, 1);
        mDatePickerDialog.getDatePicker().setMaxDate(now.getTimeInMillis());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_solicitar_servico, container, false);
        txtChosenDate = view.findViewById(R.id.chosen_date);
        txtPreferredTime = view.findViewById(R.id.preferred_time);
        Button btnDatePicker = view.findViewById(R.id.btn_datepicker);
        Button btnTimePicker = view.findViewById(R.id.btn_timepicker);
        Button btnServicos = view.findViewById(R.id.btn_servicos);
        Button btnSolicitar = view.findViewById(R.id.btn_solicitar);

        txtChosenDate.setText(R.string.empty_chosen_date);
        txtPreferredTime.setText(R.string.empty_preferred_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnServicos.setOnClickListener(this);
        btnSolicitar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_datepicker) {
            mDatePickerDialog.show();
        } else if (v.getId() == R.id.btn_timepicker) {
            mTimePickerDialog.show();
        } else if (v.getId() == R.id.btn_servicos) {
            startActivityForResult(new Intent(getActivity(), SelecionarServicoActivity.class), REQUEST_CODE_SERVICOS);
        } else if (v.getId() == R.id.btn_solicitar) {
            if (mDateTime.isValid() && mServicos != null && !mServicos.isEmpty()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setHorario(mDateTime.getUnix());
                agendamento.setServicos(mServicos);

                new NegocioAgendamento(mContext).solicitarAgendamento(agendamento).enqueue(
                        new Callback<Resposta>() {
                            @Override
                            public void onResponse(@NonNull Call<Resposta> call, @NonNull Response<Resposta> response) {
                                if (response.isSuccessful() && response.body() != null && response.body().getMessage() != null) {
                                    mListener.createSnackBarMessage("Agendamento solicitado com sucesso");
                                } else {
                                    mListener.createSnackBarMessage("Falha ao solicitar servico");
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Resposta> call, @NonNull Throwable t) {
                                if (t instanceof SocketTimeoutException) {
                                    mListener.createSnackBarMessage("Erro ao tentar conectar com o servidor");;
                                } else {
                                    mListener.createSnackBarMessage(t.getMessage());
                                }
                            }
                        }
                );
            } else {
                mListener.createSnackBarMessage("É necessário a escolha da data, hora e serviços");
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDateTime.year = year;
        mDateTime.month = month;
        mDateTime.day = dayOfMonth;
        txtChosenDate.setText(getString(R.string.chosen_date, mDateFormat.format(mDateTime.getDateCalendar().getTime())));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mDateTime.hour = hourOfDay;
        mDateTime.minute = minute;
        txtPreferredTime.setText(getString(R.string.preferred_time, mTimeFormat.format(mDateTime.getTimeCalendar().getTime())));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SERVICOS && resultCode == Activity.RESULT_OK) {
            mServicos = data.getParcelableArrayListExtra(SelecionarServicoActivity.RESULT_SERVICOS);
        }
    }

    public interface SolicitarServicoFragmentListener {
        void createSnackBarMessage(String message);
    }

    private class DateTime {
        Integer year;
        Integer month;
        Integer day;
        Integer hour;
        Integer minute;

        boolean isValid() {
            return isDateValid() && isTimeValid();
        }

        boolean isDateValid() {
            return year != null && month != null && day != null;
        }

        boolean isTimeValid() {
            return hour != null && minute != null;
        }

        Long getUnix() {
            GregorianCalendar calendar = getFullCalendar();

            if (calendar == null) {
                return null;
            }

            return calendar.getTimeInMillis() / 1000L;
        }

        GregorianCalendar getFullCalendar() {
            if (!isValid()) {
                return null;
            }

            return new GregorianCalendar(year, month, day, hour, minute, 0);
        }

        GregorianCalendar getDateCalendar() {
            if (!isDateValid()) {
                return null;
            }

            return new GregorianCalendar(year, month, day);
        }

        GregorianCalendar getTimeCalendar() {
            if (!isTimeValid()) {
                return null;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(GregorianCalendar.HOUR_OF_DAY, hour);
            calendar.set(GregorianCalendar.MINUTE, minute);
            calendar.set(GregorianCalendar.SECOND, 0);
            calendar.set(GregorianCalendar.MILLISECOND, 0);

            return calendar;
        }
    }
}
