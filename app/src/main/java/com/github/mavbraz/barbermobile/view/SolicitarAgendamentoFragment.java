package com.github.mavbraz.barbermobile.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Agendamento;
import com.github.mavbraz.barbermobile.model.basicas.Servico;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SolicitarAgendamentoFragment extends Fragment
        implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        ListarServicosAdapter.ListarServicosAdapterListener {

    public static final String TAG = "solicitarAgendamento";

    TextView txtChosenDate;
    TextView txtPreferredTime;
    TextView txtQuantidade;
    TextView txtTotal;
    ListView listView;
    AutoCloseBottomSheetBehavior bottomSheet;

    private double total;
    private ListarServicosAdapter mListarServicosAdapter;
    private DateFormat mDateFormat;
    private DateFormat mTimeFormat;
    private DateTime mDateTime;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private Context mContext;
    private SolicitarServicoFragmentListener mListener;

    public SolicitarAgendamentoFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
        if (context instanceof SolicitarServicoFragmentListener) {
            mListener = (SolicitarServicoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SolicitarServicoFragmentListener");
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

        if (savedInstanceState == null) {
            mDateTime = new DateTime();

            mDateFormat = android.text.format.DateFormat.getDateFormat(mContext);
            mTimeFormat = android.text.format.DateFormat.getTimeFormat(mContext);

            GregorianCalendar now = new GregorianCalendar();

            mDatePickerDialog = new DatePickerDialog(mContext, this,
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

            mListarServicosAdapter = new ListarServicosAdapter(mContext, null, null,this);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_solicitar_servico, container, false);
        txtChosenDate = view.findViewById(R.id.chosen_date);
        txtPreferredTime = view.findViewById(R.id.preferred_time);
        listView = view.findViewById(R.id.list_servicos);
        Button btnDatePicker = view.findViewById(R.id.btn_datepicker);
        Button btnTimePicker = view.findViewById(R.id.btn_timepicker);
        Button btnSolicitar = view.findViewById(R.id.btn_solicitar);
        LinearLayout layoutBottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheet = (AutoCloseBottomSheetBehavior) AutoCloseBottomSheetBehavior.from(layoutBottomSheet);
        RelativeLayout bottomSheetHeader = view.findViewById(R.id.bottom_sheet_header);
        txtQuantidade = view.findViewById(R.id.txtQuantidade);
        txtTotal = view.findViewById(R.id.txtTotal);

        txtChosenDate.setText(R.string.empty_chosen_date);
        txtPreferredTime.setText(R.string.empty_preferred_time);
        if (savedInstanceState == null) {
            txtQuantidade.setText("0");
        } else {
            txtQuantidade.setText(String.valueOf(mListarServicosAdapter.getServicosSelecionados().size()));
            if (mDateTime.getDateCalendar() != null) {
                txtChosenDate.setText(getString(R.string.chosen_date, mDateFormat.format(mDateTime.getDateCalendar().getTime())));
            }
            if (mDateTime.getTimeCalendar() != null) {
                txtPreferredTime.setText(getString(R.string.preferred_time, mTimeFormat.format(mDateTime.getTimeCalendar().getTime())));
            }
        }
        txtTotal.setText(String.format(Locale.getDefault(), "R$%.2f", total));

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSolicitar.setOnClickListener(this);
        bottomSheetHeader.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            final ProgressDialog progressDialog = ProgressDialog.show(mContext, "Serviços", "Carregando...");
            progressDialog.show();

            mListener.carregarServicos(mListarServicosAdapter, progressDialog);
        }

        listView.setAdapter(mListarServicosAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_datepicker) {
            mDatePickerDialog.show();
        } else if (v.getId() == R.id.btn_timepicker) {
            mTimePickerDialog.show();
        } else if (v.getId() == R.id.bottom_sheet_header) {
            if (bottomSheet.getState() == AutoCloseBottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheet.setState(AutoCloseBottomSheetBehavior.STATE_EXPANDED);
            } else if (bottomSheet.getState() == AutoCloseBottomSheetBehavior.STATE_EXPANDED) {
                bottomSheet.setState(AutoCloseBottomSheetBehavior.STATE_COLLAPSED);
            }
        } else if (v.getId() == R.id.btn_solicitar) {
            if (mDateTime.isValid() && mListarServicosAdapter != null &&
                    mListarServicosAdapter.getServicosSelecionados() != null && !mListarServicosAdapter.getServicosSelecionados().isEmpty()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setHorario(mDateTime.getUnix());
                agendamento.setServicos(new ArrayList<>(mListarServicosAdapter.getServicosSelecionados().values()));

                mListener.solicitarAgendamento(agendamento);
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
    public void itemMarcado(Servico servico) {
        total += servico.getValor();
        txtTotal.setText(String.format(Locale.getDefault(), "R$%.2f", total));
        txtQuantidade.setText(String.valueOf(mListarServicosAdapter.getServicosSelecionados().size()));
    }

    @Override
    public void itemDesmarcado(Servico servico) {
        total -= servico.getValor();
        txtTotal.setText(String.format(Locale.getDefault(), "R$%.2f", total));
        txtQuantidade.setText(String.valueOf(mListarServicosAdapter.getServicosSelecionados().size()));
    }

    public interface SolicitarServicoFragmentListener {
        void createSnackBarMessage(String message);
        void solicitarAgendamento(Agendamento agendamento);
        void carregarServicos(ListarServicosAdapter adapter, ProgressDialog progressDialog);
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
