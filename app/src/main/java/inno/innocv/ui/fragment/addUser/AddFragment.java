package inno.innocv.ui.fragment.addUser;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import inno.innocv.R;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.ui.activity.BaseActivity;
import inno.innocv.ui.fragment.BaseFragment;
import inno.innocv.ui.fragment.DatePickerFragment;
import inno.innocv.utils.Constants;
import inno.innocv.utils.Utils;


public class AddFragment extends BaseFragment implements AddView, View.OnClickListener {
    public static final String TAG = "EditFragment";
    private EditText _dateTime;

    private AddPresenter mPresenter;

    private EditText mEditTextCalendar;
    private EditText mEditTextUser;
    private Button mButtonCreate;
    private ProgressDialog mProgressDialog;

    /**
     * Constructor class.
     */
    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Singleton.
     *
     * @return AddFragment.
     */
    public static AddFragment newInstance() {
        return new AddFragment();
    }

    /**
     * Get context.
     *
     * @return context.
     */
    @Override
    public Context getContextPref() {
        return getContext();
    }

    /**
     * +
     * Get Activity.
     *
     * @return BaseActivity.
     */
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

    /**
     * Show data calendar.
     */
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddPresenterImpl();
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

    @Override
    public void onShowProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Creating Users");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void onHideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void initViews(View view) {
        mEditTextCalendar = (EditText) view.findViewById(R.id.edit_text_date);
        mEditTextUser = (EditText) view.findViewById(R.id.edit_name);

        mButtonCreate = (Button) view.findViewById(R.id.buttonCreate);

    }

    private void configureViews() {
        mButtonCreate.setOnClickListener(this);
    }

    private void createUser(Context context, String birthday, String name) {
        mPresenter.onResultSuscess(context, birthday, name);
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
                    Utils.createDialog(getContextPref(), Constants.MESSAGE_EMPTY, Constants.TITTLE_ERROR, R.drawable.ic_error);
                } else {
                    createUser(getContextPref(), mEditTextCalendar.getText().toString(), mEditTextUser.getText().toString());
                }
                break;
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
    }
}
