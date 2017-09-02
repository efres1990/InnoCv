package inno.innocv.ui.fragment.addUser.date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import inno.innocv.R;

import java.util.Calendar;

public class DateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private int year;
    private int month;
    private int day;
    private EditText _dateTime;

    /**
     * Create Dialog calendar.
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * If data have changed.
     *
     * @param view   view.
     * @param _year  year.
     * @param _month month.
     * @param _day   day.
     */
    public void onDateSet(DatePicker view, int _year, int _month, int _day) {
        // Do something with the date chosen by the user
        year = _year;
        month = _month;
        day = _day;
        _dateTime = (EditText) getActivity().findViewById(R.id.edit_text_date);
        // set current date into textview
        _dateTime.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("/")
                .append(month + 1).append("/")
                .append(year).append(" "));
    }
}