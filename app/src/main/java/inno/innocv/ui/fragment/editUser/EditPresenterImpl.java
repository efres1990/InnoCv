package inno.innocv.ui.fragment.editUser;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import inno.innocv.R;
import inno.innocv.data.loader.DataCallback;
import inno.innocv.data.loader.DeleteLoader;
import inno.innocv.data.loader.EditLoader;
import inno.innocv.data.model.EditUserRequest;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.listener.SubmitListener;
import inno.innocv.utils.Constants;
import inno.innocv.utils.Utils;

/**
 * @author eladiofreire
 */

public class EditPresenterImpl implements EditPresenter {
    private EditView mView;
    private Handler mHandler;
    private SubmitListener mListener;

    /**
     * Default constructor.
     */
    public EditPresenterImpl() {

    }

    /**
     * Constructor with listener.
     *
     * @param listener listener update recycler data.
     */
    public EditPresenterImpl(SubmitListener listener) {
        mListener = listener;

    }

    @Override
    public void onCreate(EditView view) {
        mView = view;
        mHandler = new Handler();

    }

    /**
     * Update the data value.
     *
     * @param context  context app.
     * @param birthday birthdate.
     * @param name     name user.
     * @param id       id user.
     */
    @Override
    public void onUpdateUser(final Context context, final String birthday, final String name, final int id) {
        try {
            final EditLoader loader = new EditLoader();
            loader.setCallback(new DataCallback<UserInfoValue>() {
                @Override
                public void onResponse(final UserInfoValue userInfoValues) {
                    loader.removeCallBack();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Utils.createDialog(context, context.getResources().getString(R.string.user_update), context.getResources().getString(R.string.sucess_update), R.drawable.ic_check_mark_button);

                            updateData(userInfoValues);


                        }
                    });
                }

                @Override
                public void onFail(int message) {
                    dialog(context);
                }
            });

            EditUserRequest editUserRequest = new EditUserRequest(name, birthday, id);

            loader.load(context, editUserRequest.getRequestJSONserialized());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Delete User.
     *
     * @param context context.
     * @param id      user id.
     */
    @Override
    public void onDeleteUser(final Context context, int id) {
        try {
            Bundle data = new Bundle();
            data.putInt("id", id);

            DeleteLoader deleteLoader = new DeleteLoader();
            deleteLoader.setCallback(new DataCallback<Integer>() {

                @Override
                public void onResponse(Integer code) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Utils.createDialog(context, context.getResources().getString(R.string.sucess_delete), context.getResources().getString(R.string.user_delete), R.drawable.ic_check_mark_button);
                        }
                    });
                }

                @Override
                public void onFail(int message) {
                    dialog(context);
                }
            });
            deleteLoader.load(context, data);
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * Create dialog to responses.
     *
     * @param context context.
     */
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
                builder.setTitle(context.getResources().getString(R.string.error))
                        .setMessage(context.getResources().getString(R.string.error_network))
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
