package inno.innocv.ui.fragment.addUser;

import android.content.Context;

import inno.innocv.data.model.UserInfoValue;
import inno.innocv.ui.activity.BaseActivity;

import java.util.List;


/**
 * @author eladiofreire
 */

public interface AddView {


    Context getContextPref();

    BaseActivity getBaseActivity();

    void onUpdateData(UserInfoValue data);

    void removeUserName();

    void onShowProgressDialog();

    void onHideProgressDialog();

}
