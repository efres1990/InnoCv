package inno.innocv.ui.fragment.main;

import android.content.Context;

import java.util.List;

import inno.innocv.data.model.UserInfoValue;
import inno.innocv.ui.activity.BaseActivity;


/**
 * @author eladiofreire
 */

public interface MainView {


    Context getContextPref();

    BaseActivity getBaseActivity();

    void onUpdateData(List<UserInfoValue> data);

    void removeUserName();

    void onShowProgressDialog();

    void onHideProgressDialog();

}
