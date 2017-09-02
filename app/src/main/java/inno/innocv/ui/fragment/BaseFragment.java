package inno.innocv.ui.fragment;

import android.support.v4.app.Fragment;

import inno.innocv.ui.activity.BaseActivity;

/**
 * Created by eladiofreire on 21/8/17.
 */

public class BaseFragment extends Fragment {


    /**
     * Gets the base activity
     *
     * @return activity
     */
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
