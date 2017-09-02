package inno.innocv.ui.fragment.main;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import inno.innocv.R;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.listener.SubmitListener;
import inno.innocv.ui.activity.AddUserActivity;
import inno.innocv.ui.activity.BaseActivity;
import inno.innocv.ui.activity.SearchUserActivity;
import inno.innocv.ui.adapter.MainAdapter;
import inno.innocv.ui.fragment.BaseFragment;

public class MainFragment extends BaseFragment implements MainView, View.OnClickListener {
    public static final String TAG = "MainFragment";

    private MainPresenter mPresenter;
    private FloatingActionsMenu mFloatingMenu;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private FloatingActionButton mFloatingSearch;
    private FloatingActionButton mFloatingAdd;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
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
    public void onUpdateData(List<UserInfoValue> data) {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (mRecyclerView.getAdapter() != null) {

            MainAdapter adapter = (MainAdapter) mRecyclerView.getAdapter();
            adapter.setData(data);
            adapter.notifyDataSetChanged();


        } else {
            MainAdapter adapter = new MainAdapter(getActivity(), data);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.onGetUsers(getContextPref());
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

        mPresenter = new MainPresenterImpl(getContextPref(), new SubmitListener() {
            @Override
            public void onSubmit(ArrayList<UserInfoValue> userInfoValues) {
                onUpdateData(userInfoValues);
            }
        });
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
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
    public void onDetach() {
        super.onDetach();
    }

    private void initViews(View view) {
        mFloatingMenu = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        mFloatingAdd = (FloatingActionButton) view.findViewById(R.id.add_button);
        mFloatingSearch = (FloatingActionButton) view.findViewById(R.id.search_button);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


    }

    @Override
    public void onShowProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
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

    private void configureViews() {
        mFloatingMenu.setOnClickListener(this);
        mFloatingAdd.setOnClickListener(this);
        mFloatingAdd.setColorNormalResId(R.color.colorPrimaryDark);
        mFloatingAdd.setColorPressedResId(R.color.colorPrimaryDark);
        mFloatingAdd.setIcon(R.drawable.add_button);
        mFloatingAdd.setStrokeVisible(false);
        mFloatingSearch.setOnClickListener(this);
        mFloatingSearch.setOnClickListener(this);
        mFloatingSearch.setColorNormalResId(R.color.colorPrimary);
        mFloatingSearch.setColorPressedResId(R.color.colorPrimary);
        mFloatingSearch.setIcon(R.drawable.ic_search);
        mFloatingSearch.setStrokeVisible(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // if (dy > 0 || dy < 0 && mFloatingMenu.isShown())
                //  mFloatingMenu.setEnabled(false);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //  mFloatingMenu.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivity(intent);
                break;
            case R.id.search_button:
                Intent intentSearch = new Intent(getActivity(), SearchUserActivity.class);
                startActivity(intentSearch);
                break;
        }
    }


}
