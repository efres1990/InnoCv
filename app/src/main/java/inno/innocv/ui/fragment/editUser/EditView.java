package inno.innocv.ui.fragment.editUser;

import android.content.Context;

import inno.innocv.data.model.UserInfoValue;
import inno.innocv.ui.activity.BaseActivity;


/**
 * @author eladiofreire
 */

public interface EditView {


    Context getContextPref();

    BaseActivity getBaseActivity();

    void onUpdateData(UserInfoValue data);

    void removeUserName();


}
