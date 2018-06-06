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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.model.basicas.Servico;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.List;

public class SolicitarServicoFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "solicitarServico";

    private static final int REQUEST_CODE_SERVICOS = 1;

    TextView txtChosenDate;
    TextView txtPreferredTime;

    DateTime dateTime;
    DateFormat dateFormat;
    DateFormat timeFormat;
    DatePickerDialog mDatePickerDialog;
    TimePickerDialog mTimePickerDialog;
    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateTime = new DateTime();

        GregorianCalendar now = new GregorianCalendar();
        dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
        timeFormat = android.text.format.DateFormat.getTimeFormat(mContext);

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

        txtChosenDate.setText(R.string.empty_chosen_date);
        txtPreferredTime.setText(R.string.empty_preferred_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnServicos.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_datepicker) {
            mDatePickerDialog.show();
        } else if (v.getId() == R.id.btn_timepicker) {
            mTimePickerDialog.show();
        } else if (v.getId() == R.id.btn_servicos) {
            startActivityForResult(new Intent(mContext, SelecionarServicoActivity.class), REQUEST_CODE_SERVICOS);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateTime.year = year;
        dateTime.month = month;
        dateTime.day = dayOfMonth;
        txtChosenDate.setText(getString(R.string.chosen_date, dateFormat.format(dateTime.getDateCalendar().getTime())));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dateTime.hour = hourOfDay;
        dateTime.minute = minute;
        txtPreferredTime.setText(getString(R.string.preferred_time, timeFormat.format(dateTime.getTimeCalendar().getTime())));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SERVICOS && resultCode == Activity.RESULT_OK) {
            List<Servico> servicos = data.getParcelableArrayListExtra(SelecionarServicoActivity.RESULT_SERVICOS);
            Log.d("MAV", servicos.toString());
        }
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

        Long getDateTime() {
            GregorianCalendar calendar = getFullCalendar();

            if (calendar == null) {
                return null;
            }

            return calendar.getTimeInMillis();
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
