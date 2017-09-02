package inno.innocv.ui.fragment.addUser;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;

import inno.innocv.R;
import inno.innocv.data.loader.AddLoader;
import inno.innocv.data.loader.DataCallback;
import inno.innocv.data.model.NewUserRequest;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.utils.Constants;
import inno.innocv.utils.Utils;

/**
 * @author eladiofreire
 */

public class AddPresenterImpl implements AddPresenter {
    private AddView mView;
    private Handler mHandler;


    public AddPresenterImpl() {
    }

    @Override
    public void onCreate(AddView view) {
        mView = view;
        mHandler = new Handler();

    }

    @Override
    public void onResultSuscess(final Context context, final String birthday, final String name) {

        try {
            final AddLoader loader = new AddLoader();
            loader.setCallback(new DataCallback<UserInfoValue>() {
                @Override
                public void onResponse(final UserInfoValue userInfoValues) {
                    loader.removeCallBack();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Utils.createDialog(context, Constants.TITTLE_ADDED, Constants.MESSAGE_ADDED, R.drawable.ic_check_mark_button);
                            hideProgressDialog();
                            updateData(userInfoValues);
                        }
                    });
                }

                @Override
                public void onFail(int message) {
                    dialog(context);
                    hideProgressDialog();

                }
            });
            NewUserRequest userRequest = new NewUserRequest(name, birthday);
            showProgressDialog();

            loader.load(context, userRequest.getRequestJSONserialized());

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


    public void updateData(UserInfoValue data) {
        if (mView != null) {
            mView.onUpdateData(data);
        }
    }

    private void dialog(final Context context) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle(Constants.TITTLE_ERROR)
                        .setMessage(Constants.MESSAGE_ERROR)
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
