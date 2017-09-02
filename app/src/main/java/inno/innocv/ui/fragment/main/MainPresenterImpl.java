package inno.innocv.ui.fragment.main;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import inno.innocv.R;
import inno.innocv.data.loader.DataCallback;
import inno.innocv.data.loader.DeleteLoader;
import inno.innocv.data.loader.MainLoader;
import inno.innocv.data.loader.UpdateLoader;
import inno.innocv.data.model.NewUserRequest;
import inno.innocv.data.model.UserInfoValue;
import inno.innocv.listener.SubmitListener;
import inno.innocv.utils.Constants;

/**
 * @author eladio freire
 */

public class MainPresenterImpl implements MainPresenter {
    private MainView mView;
    private Handler mHandler;
    private Context mContext;
    private ConnectivityManager mConnectivityManager;
    public static List<UserInfoValue> dataValues = new ArrayList<>();
    private SubmitListener mListener;


    /**
     * Default constructor.
     */


    public MainPresenterImpl(Context context, SubmitListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public void onCreate(MainView view) {
        mView = view;
        mHandler = new Handler();
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        showProgressDialog();

    }

    /**
     * Get all users.
     *
     * @param context class context.
     */
    @Override
    public void onGetUsers(final Context context) {

        if (mHandler == null) {
            mHandler = new Handler();

        }
        try {
            final MainLoader loader = new MainLoader();
            loader.setCallback(new DataCallback<ArrayList<UserInfoValue>>() {
                @Override
                public void onResponse(final ArrayList<UserInfoValue> userInfoValues) {
                    loader.removeCallBack();
                    hideProgressDialog();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            dataValues = userInfoValues;
                            updateData(userInfoValues);
                            mListener.onSubmit(userInfoValues);
                        }
                    });
                }

                @Override
                public void onFail(int message) {
                    //Utils.createDialog(context, "Error", "Netwwork error, try again later", R.drawable.ic_error);
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
                                    .setMessage(context.getResources().getString(R.string.error_network))
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            ((Activity) context).onBackPressed();
                                        }

                                    })

                                    .setIcon(R.drawable.ic_error)
                                    .show();
                        }
                    });

                    hideProgressDialog();
                }

            });

            loader.load(context);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Update user.
     *
     * @param context class context.
     * @param date    birthdate.
     * @param name    name user.
     * @param id      id.
     */
    @Override
    public void onUpdateUser(Context context, String date, String name, int id) {
        try {


            UpdateLoader updateLoader = new UpdateLoader();
            updateLoader.setCallback(new DataCallback<UserInfoValue>() {

                @Override
                public void onResponse(UserInfoValue userInfoValue) {

                }

                @Override
                public void onFail(int message) {

                }
            });
            NewUserRequest newUserRequest = new NewUserRequest(name, date);

            updateLoader.load(context, newUserRequest.getRequestJSONserialized());
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
                    Log.d("Code Result ", code.toString());
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


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


    /**
     * Update the data in the recycler view.
     *
     * @param data user list.
     */
    public void updateData(List<UserInfoValue> data) {
        if (mView != null) {
            mView.onUpdateData(data);
        }
    }

    private void dialog(final Context context) {
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
