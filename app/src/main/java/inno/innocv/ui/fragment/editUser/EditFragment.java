package inno.innocv.ui.fragment.editUser;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import inno.innocv.R;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.ui.activity.BaseActivity;
import inno.innocv.ui.fragment.BaseFragment;
import inno.innocv.ui.fragment.DatePickerFragment;
import inno.innocv.ui.fragment.addUser.date.DateFragment;
import inno.innocv.utils.Constants;
import inno.innocv.utils.Utils;

import java.util.Calendar;


public class EditFragment extends BaseFragment implements EditView, View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    public static final String TAG = "EditFragment";
    private EditText _dateTime;

    private EditPresenter mPresenter;

    private EditText mEditTextCalendar;
    private EditText mEditTextUser;
    private Button mButtonCreate;
    private Button mButtonDelete;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance() {
        return new EditFragment();
    }


    @Override
    public Context getContextPref() {
        return getContext();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onUpdateData(UserInfoValue data) {


    }


    @Override
    public void removeUserName() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set action to textbox
        _dateTime = (EditText) getActivity().findViewById(R.id.edit_text_date);
        _dateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String newMonth = String.valueOf(month);
                String newDay = String.valueOf(day);

                if (month < 10) {
                    newMonth = "0" + month;
                }
                ;
                if (day < 10) {
                    newDay = "0" + day;
                }
                ;
                final String selectedDate = year + " / " + (newMonth) + " / " + newDay;
                _dateTime.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    protected Dialog onCreateDialog() {
        DialogFragment newFragment = new DateFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EditPresenterImpl();
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        initViews(view);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onCreate(this);
        configureViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initViews(View view) {
        mEditTextCalendar = (EditText) view.findViewById(R.id.edit_text_date);
        mEditTextUser = (EditText) view.findViewById(R.id.edit_name);

        mButtonCreate = (Button) view.findViewById(R.id.buttonCreate);
        mButtonDelete = (Button) view.findViewById(R.id.buttonDelete);

    }

    private void configureViews() {
        mEditTextUser.setText(getActivity().getIntent().getExtras().getString("name"));
        mEditTextCalendar.setText(Utils.changeDate(getActivity().getIntent().getExtras().getString("date")));
        mButtonDelete.setVisibility(View.VISIBLE);
        mButtonDelete.setOnClickListener(this);
        mButtonCreate.setText(R.string.update);
        mButtonCreate.setOnClickListener(this);
    }

    private void editUser(Context context, String birthday, String name) {
        mPresenter.onUpdateUser(context, birthday, name, getActivity().getIntent().getExtras().getInt("id"));
    }

    private void deleteUser(Context context) {
        mPresenter.onDeleteUser(context, getActivity().getIntent().getExtras().getInt("id"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreate:

                if (mEditTextCalendar.getText().toString().equalsIgnoreCase("") || mEditTextUser.getText().toString().equalsIgnoreCase("")) {
                    Utils.createDialog(getContextPref(), getContextPref().getResources().getString(R.string.fill_data), getContextPref().getResources().getString(R.string.error), R.drawable.ic_error);
                } else {
                    editUser(getContextPref(), mEditTextCalendar.getText().toString(), mEditTextUser.getText().toString());
                }
                break;
            case R.id.buttonDelete:

                deleteUser(getContextPref());

                break;
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
