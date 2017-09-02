package inno.innocv.data.loader;

import android.content.Context;
import android.util.Log;

import inno.innocv.data.model.UserInfoValue;
import inno.innocv.data.storage.DBManager;
import inno.innocv.network.WebService;
import inno.innocv.utils.Constants;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;

import static inno.innocv.utils.Constants.RESPONSE_CODE_200;
import static inno.innocv.utils.Constants.RESPONSE_UNKNOWN_ERROR;

/**
 * @author eladiofreire on 21/8/17.
 */

public class MainLoader {
    private ExecutorService mThreadExecutor;
    private DataCallback<ArrayList<UserInfoValue>> mCallback;
    private DBManager mDB;
    private ArrayList<UserInfoValue> mResultList;
    private boolean validData;

    public MainLoader() {
        mThreadExecutor = Executors.newSingleThreadExecutor();

    }

    /**
     * Set callback user info.
     *
     * @param callback callback.
     */
    public void setCallback(DataCallback<ArrayList<UserInfoValue>> callback) {
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
     */
    public void load(final Context context) {
        mDB = DBManager.getInstance(context);
//        mResultList = mDB.getUserList();
//        if (mResultList != null && mResultList.size() > 0) {
//            if (mResultList.size() != getSize(context)) {
//
//                validData = true;
//            }
//
//        }
//
//        if (validData) {
        // mCallback.onResponse(mResultList);
//        } else {

        mThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {

                loadData(context);
            }
        });
    }
//    }

    /**
     * Load the webService.
     *
     * @param context context app.
     */
    private void loadData(final Context context) {
        Response response = WebService.getRequest(Constants.HOST_GET_USERS, null);

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

                JSONArray jsonArray = new JSONArray(resultString);
                if (jsonArray != null) {

                    Type listType = new TypeToken<ArrayList<UserInfoValue>>() {
                    }.getType();
                    ArrayList<UserInfoValue> responseList = new GsonBuilder().create().fromJson(jsonArray.toString(), listType);
                    Log.d("result", jsonArray.toString());
                    //mDB.setUsersList(responseList);
                    response(responseList);

                } else {
                    failure(RESPONSE_UNKNOWN_ERROR);

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
     * @param resultList response data
     */
    private void response(ArrayList<UserInfoValue> resultList) {
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
