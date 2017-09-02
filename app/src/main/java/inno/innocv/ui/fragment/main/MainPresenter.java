package inno.innocv.ui.fragment.main;


import android.content.Context;

/**
 * @author eladiofreire
 */

public interface MainPresenter {
    void onCreate(MainView view);

    void onGetUsers(Context context);

    void onUpdateUser(Context context, String date, String name, int id);

    void onDeleteUser(Context context, int id);

    void onDestroy();
}
