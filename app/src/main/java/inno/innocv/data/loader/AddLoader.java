package inno.innocv.data.loader;

import android.content.Context;
import android.util.Log;


import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import inno.innocv.data.model.UserInfoValue;
import inno.innocv.network.WebService;
import inno.innocv.utils.Constants;
import okhttp3.Response;

import static inno.innocv.utils.Constants.RESPONSE_CODE_200;
import static inno.innocv.utils.Constants.RESPONSE_UNKNOWN_ERROR;


/**
 * @author eladiofreire on 23/8/17.
 */

public class AddLoader {
    private ExecutorService mThreadExecutor;
    private DataCallback<UserInfoValue> mCallback;

    /**
     * Constructor class.
     */
    public AddLoader() {
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
     * @param context  context app.
     * @param dataSend data send, new user.
     */
    public void load(final Context context, final String dataSend) {
        mThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                loadData(context, dataSend);
            }
        });
    }

    /**
     * Load the webService.
     *
     * @param context  context app.
     * @param dataSend data send, new user.
     */
    private void loadData(final Context context, String dataSend) {
        Response response = WebService.sendHttpsPost(dataSend, context, Constants.HOST_CREATE_USER);

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

                JSONObject jsonObject = new JSONObject(resultString);
                if (jsonObject != null) {

                    Type listType = new TypeToken<UserInfoValue>() {
                    }.getType();
                    UserInfoValue responseList = new GsonBuilder().create().fromJson(jsonObject.toString(), listType);
                    Log.d("result", jsonObject.toString());
                    response(responseList);

                } else {
                    failure(RESPONSE_UNKNOWN_ERROR);
                    Log.d("Response ", String.valueOf(response.code()));

                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
                failure(RESPONSE_UNKNOWN_ERROR);
            }
        } else if (response != null) {
            failure(response.code());
            Log.d("Response ", String.valueOf(response.code()));

        } else {
            failure(RESPONSE_UNKNOWN_ERROR);
        }
    }

    /**
     * User request response.
     *
     * @param resultList response data
     */
    private void response(UserInfoValue resultList) {
        if (mCallback != null) {
            mCallback.onResponse(resultList);
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
