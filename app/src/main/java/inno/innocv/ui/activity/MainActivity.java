package inno.innocv.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import inno.innocv.R;
import inno.innocv.ui.fragment.main.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureFragments(savedInstanceState);
    }

    /**
     * Result return activity.
     *
     * @param requestCode code.
     * @param resultCode  result code.
     * @param data        data values.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_main_content);
        if (fragment != null) {
            if (fragment instanceof MainFragment) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * Configure fragment.
     *
     * @param savedInstanceState if null create fragment.
     */
    private void configureFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_main_content, MainFragment.newInstance(), MainFragment.TAG)
                    .commit();
        }
    }

}
