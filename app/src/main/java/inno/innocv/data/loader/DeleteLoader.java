package inno.innocv.data.loader;

import android.content.Context;
import android.os.Bundle;

import inno.innocv.network.WebService;
import inno.innocv.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;

import static inno.innocv.utils.Constants.RESPONSE_CODE_200;
import static inno.innocv.utils.Constants.RESPONSE_UNKNOWN_ERROR;

/**
 * @author eladiofreire on 24/8/17.
 */

public class DeleteLoader {
    private ExecutorService mThreadExecutor;
    private DataCallback<Integer> mCallback;

    /**
     * Constructor class.
     */
    public DeleteLoader() {
        mThreadExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * Set callback user info.
     *
     * @param callback callback.
     */
    public void setCallback(DataCallback<Integer> callback) {
        mCallback = callback;
    }

    /**
     * Remove callback.
     */
    public void removeCallBack() {
        mCallback = null;
    }

    /**
     * Load the webService to get the info user created.
     *
     * @param context context app.
     * @param data    bundle with the id.
     */
    public void load(final Context context, final Bundle data) {
        mThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                loadData(context, data);
            }
        });
    }

    /**
     * Load the webService to get the info user created.
     *
     * @param context context app.
     * @param data    bundle with the id.
     */
    private void loadData(final Context context, Bundle data) {
        Response response = WebService.getRequest(Constants.HOST_DELETE_USER, data);

        if (response != null && response.code() == RESPONSE_CODE_200) {
            try {

                response(response.code());


            } catch (Exception e) {
                e.printStackTrace();
                failure(RESPONSE_UNKNOWN_ERROR);
            }
        } else if (response != null) {
            failure(response.code());
        } else {
            failure(RESPONSE_UNKNOWN_ERROR);
        }
    }

    /**
     * User request response.
     *
     * @param code code response data
     */
    private void response(int code) {
        if (mCallback != null) {
            mCallback.onResponse(code);
        }
    }

    /**
     * Fail response.
     *
     * @param error id.
     */
    private void failure(int error) {
        if (mCallback != null) {
            mCallback.onFail(error);
        }
    }
}
