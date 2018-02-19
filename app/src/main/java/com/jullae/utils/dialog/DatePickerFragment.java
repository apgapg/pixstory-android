package com.jullae.utils.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;


/**
 * Calling for Time picker
 * <p>
 * TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
 * <p>
 * <p>
 * public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
 * <p>
 * Do whatever you want to do with selected time
 * <p>
 * }
 * }).show(getSupportFragmentManager(), "timePicker");
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog.OnDateSetListener listener;
    private Calendar calendarSelectedDate;
    private Calendar calendarMinDate;
    private Calendar calendarMaxDate;

    /**
     * @param listener             instance of DatePickerDialog.OnDateSetListener
     * @param calendarSelectedDate calendar Selected Date
     * @param calendarMinDate      calendar Min Date
     * @param calendarMaxDate      calendar Max Date
     * @return object of DatePickerFragment
     */
    public static DatePickerFragment newInstance(final Calendar calendarSelectedDate, final Calendar calendarMinDate,
                                                 final Calendar calendarMaxDate, final DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.calendarSelectedDate = calendarSelectedDate;
        fragment.calendarMinDate = calendarMinDate;
        fragment.calendarMaxDate = calendarMaxDate;
        fragment.listener = listener;
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the current date as the progressBar date in the picker
        int minYear, minMonth, minDay;
        final Calendar selectedDate = calendarSelectedDate;
        if (selectedDate != null) {
            minYear = selectedDate.get(Calendar.YEAR);
            minMonth = selectedDate.get(Calendar.MONTH);
            minDay = selectedDate.get(Calendar.DAY_OF_MONTH);
        } else {
            minYear = calendarMaxDate.get(Calendar.YEAR);
            minMonth = calendarMaxDate.get(Calendar.MONTH);
            minDay = calendarMaxDate.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, minYear, minMonth, minDay);
        if (calendarMinDate != null) {
            dialog.getDatePicker().setMinDate(calendarMinDate.getTime().getTime());
        }
        if (calendarMaxDate != null) {
            dialog.getDatePicker().setMaxDate(calendarMaxDate.getTime().getTime());
        }
        return dialog;

        // Create a new instance of DatePickerDialog and return it
        //return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * @param view  view of DatePicker
     * @param year  year that set on DatePicker
     * @param month month that set on DatePicker
     * @param day   day that set on DatePicker
     */
    public void onDateSet(final DatePicker view, final int year, final int month, final int day) {
        // Do something with the date chosen by the user
        listener.onDateSet(view, year, month, day);
    }
}
