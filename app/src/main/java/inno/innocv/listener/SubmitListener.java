package inno.innocv.listener;


import java.util.ArrayList;

import inno.innocv.data.model.UserInfoValue;

/**
 * @author eladiofreire.
 */

public interface SubmitListener {
    /**
     * To update the recycler data values.
     *
     * @param userInfoValues array values user.
     */
    void onSubmit(ArrayList<UserInfoValue> userInfoValues);


}


