package inno.innocv.ui.fragment.search;


import android.content.Context;
import android.os.Bundle;

/**
 * @author eladiofreire
 */

public interface SearchUserPresenter {
    void onCreate(SearchUserView view);

    void onSearchUser(Context context, Bundle id);

    void onDestroy();
}
