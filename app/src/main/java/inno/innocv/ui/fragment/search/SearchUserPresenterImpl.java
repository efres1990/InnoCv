package inno.innocv.ui.fragment.search;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import inno.innocv.R;
import inno.innocv.data.loader.DataCallback;
import inno.innocv.data.loader.SearchLoader;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.utils.Constants;


/**
 * @author eladio freire
 */

public class SearchUserPresenterImpl implements SearchUserPresenter {
    private SearchUserView mView;
    private Handler mHandler;
    private Context mContext;


    /**
     * Default constructor.
     */


    public SearchUserPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(SearchUserView view) {
        mView = view;
        mHandler = new Handler();

    }

    /**
     * Get all users.
     *
     * @param context class context.
     */
    @Override
    public void onSearchUser(final Context context, Bundle idUser) {
        if (mHandler == null) {
            mHandler = new Handler();

        }
        try {

            final SearchLoader loader = new SearchLoader();
            loader.setCallback(new DataCallback<UserInfoValue>() {
                @Override
                public void onResponse(final UserInfoValue userInfoValues) {
                    loader.removeCallBack();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            hideProgressDialog();
                            updateData(userInfoValues);

                        }
                    });
                }

                @Override
                public void onFail(int message) {
                    if (message == 10) {
                        dialog(context, context.getResources().getString(R.string.no_data));
                    }
                    hideProgressDialog();
                }
            });

            showProgressDialog();
            loader.load(context, idUser);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Show progress Dialog.
     */
    private void showProgressDialog() {
        if (mView != null) {
            mView.onShowProgressDialog();
        }
    }

    /**
     * Hide progress dialog.
     */
    private void hideProgressDialog() {
        if (mView != null) {
            mView.onHideProgressDialog();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }


    /**
     * Update the data in the recycler view.
     *
     * @param data user list.
     */
    public void updateData(UserInfoValue data) {
        if (mView != null) {
            mView.onUpdateData(data);
        }
    }

    private void dialog(final Context context, final String message) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle(context.getResources().getString(R.string.error))
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }

                        })

                        .setIcon(R.drawable.ic_error)
                        .show();
            }
        });
    }


}
