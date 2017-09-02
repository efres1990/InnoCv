package inno.innocv.ui.fragment.search;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import inno.innocv.R;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.ui.activity.BaseActivity;
import inno.innocv.ui.fragment.BaseFragment;
import inno.innocv.utils.Constants;
import inno.innocv.utils.Utils;


public class SearchUserFragment extends BaseFragment implements SearchUserView, View.OnClickListener {
    public static final String TAG = "SearchUserFragment";

    private SearchUserPresenter mPresenter;
    private TextView mIdUser;
    private TextView mNameUser;
    private TextView mBirthdateUser;
    private EditText mId;
    private LinearLayout mLinearInfoUser;
    private Button mButtonSearch;
    private ProgressDialog mProgressDialog;

    public SearchUserFragment() {
    }

    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
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
        mId.setVisibility(View.GONE);
        mButtonSearch.setVisibility(View.GONE);
        mIdUser.setText(String.valueOf(data.getId()));
        mNameUser.setText(data.getName());
        mBirthdateUser.setText(Utils.changeDate(data.getBrithdate()));
        mLinearInfoUser.setVisibility(View.VISIBLE);

    }


    @Override
    public void removeUserName() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new SearchUserPresenterImpl(getContextPref());
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        initViews(view);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onCreate(this);
        configureViews();
    }

    public MenuInflater getMenuInflater() {
        return new MenuInflater(getContextPref());
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
    public void onShowProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.searching));
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initViews(View view) {

        mIdUser = (TextView) view.findViewById(R.id.id_user);
        mNameUser = (TextView) view.findViewById(R.id.name_user);
        mBirthdateUser = (TextView) view.findViewById(R.id.birth_user);
        mButtonSearch = (Button) view.findViewById(R.id.buttonSearch);
        mId = (EditText) view.findViewById(R.id.edit_id);
        mLinearInfoUser = (LinearLayout) view.findViewById(R.id.lyUserPannel);

    }

    private void configureViews() {
        mButtonSearch.setOnClickListener(this);
    }

    private void searchUser(Context context, Bundle id) {
        mPresenter.onSearchUser(context, id);

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
            case R.id.buttonSearch:
                //mId.setInputType(0);
                View view = getBaseActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getContextPref().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (mId.getText().toString().equalsIgnoreCase("")) {
                    Utils.createDialog(getContextPref(), getContextPref().getResources().getString(R.string.fill_data), getContextPref().getResources().getString(R.string.error), R.drawable.ic_error);
                } else {
                    final Bundle bundleId = new Bundle();
                    bundleId.putInt("id", Integer.parseInt(mId.getText().toString()));

                    searchUser(getContextPref(), bundleId);

                }
                break;
        }
    }


}
