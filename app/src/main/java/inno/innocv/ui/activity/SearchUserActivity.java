package inno.innocv.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import inno.innocv.R;
import inno.innocv.ui.fragment.editUser.EditFragment;
import inno.innocv.ui.fragment.search.SearchUserFragment;

public class SearchUserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        configureFragments(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_search_content);
        if (fragment != null) {
            if (fragment instanceof EditFragment) {
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
                    //.replace(R.id.layout_add_content, EditFragment.newInstance(), EditFragment.TAG)

                    .add(R.id.layout_search_content, SearchUserFragment.newInstance(), SearchUserFragment.TAG)

                    .commit();
        }
    }
}
