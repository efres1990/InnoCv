package inno.innocv.ui.fragment.search;

import android.content.Context;

import inno.innocv.data.model.UserInfoValue;
import inno.innocv.ui.activity.BaseActivity;


/**
 * @author eladiofreire@
 */

public interface SearchUserView {


    Context getContextPref();

    BaseActivity getBaseActivity();

    void onUpdateData(UserInfoValue data);

    void removeUserName();


    void onShowProgressDialog();

    void onHideProgressDialog();

}
