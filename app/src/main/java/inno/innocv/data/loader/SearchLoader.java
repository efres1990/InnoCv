package inno.innocv.data.loader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import inno.innocv.data.model.UserInfoValue;
import inno.innocv.network.WebService;
import inno.innocv.utils.Constants;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;

import static inno.innocv.utils.Constants.RESPONSE_CODE_200;
import static inno.innocv.utils.Constants.RESPONSE_NO_DATA;
import static inno.innocv.utils.Constants.RESPONSE_UNKNOWN_ERROR;

/**
 * @author eladiosuarez on 29/08/2017.
 */

public class SearchLoader {
    private ExecutorService mThreadExecutor;
    private DataCallback<UserInfoValue> mCallback;
    private UserInfoValue mResult;

    /**
     * Constructor with executor.
     */
    public SearchLoader() {
        mThreadExecutor = Executors.newSingleThreadExecutor();

    }

    /**
     * Set callback user info.
     *
     * @param callback callback.
     */
    public void setCallback(DataCallback<UserInfoValue> callback) {
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
     * @param id      id user
     */
    public void load(final Context context, final Bundle id) {

        mThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                loadData(context, id);
            }
        });
    }

    /**
     * Load the webService to get the info user created.
     *
     * @param context context app.
     * @param id      id user
     */
    private void loadData(final Context context, Bundle id) {

        Response response = WebService.getRequest(Constants.HOST_GET_USER, id);

        if (response != null && response.code() == RESPONSE_CODE_200) {
            try {
                InputStream is = response.body().byteStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder atrResponse = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    atrResponse.append(line);
                    atrResponse.append('\r');
                }
                rd.close();
                String resultString = atrResponse.toString();
                if (resultString.contains("{")) {
                    JSONObject jsonObject = new JSONObject(resultString);
                    if (jsonObject != null) {

                        Type listType = new TypeToken<UserInfoValue>() {
                        }.getType();
                        UserInfoValue responseUser = new GsonBuilder().create().fromJson(jsonObject.toString(), listType);
                        Log.d("result", jsonObject.toString());
                        response(responseUser);

                    } else {
                        failure(RESPONSE_UNKNOWN_ERROR);

                    }
                } else {
                    failure(RESPONSE_NO_DATA);

                }

            } catch (IOException | JSONException e) {
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
     * @param result response data
     */

    private void response(UserInfoValue result) {
        if (mCallback != null) {
            mCallback.onResponse(result);
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
