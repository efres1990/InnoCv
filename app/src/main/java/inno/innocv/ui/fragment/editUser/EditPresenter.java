package inno.innocv.ui.fragment.editUser;


import android.content.Context;

/**
 * @author eladiofreire
 */

public interface EditPresenter {
    void onCreate(EditView view);

    void onUpdateUser(Context context, String birthday, String name, int id);
    void onDeleteUser(Context context, int id);

    void onDestroy();
}
