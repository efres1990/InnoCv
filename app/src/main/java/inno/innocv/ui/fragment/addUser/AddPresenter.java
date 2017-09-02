package inno.innocv.ui.fragment.addUser;


import android.content.Context;

/**
 * @author eladiofreire
 */

public interface AddPresenter {
    void onCreate(AddView view);

    void onResultSuscess(Context context, String birthday, String name);

    void onDestroy();
}
