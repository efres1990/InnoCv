package inno.innocv.network;

import android.content.Context;
import android.os.Bundle;

import inno.innocv.utils.Constants;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author eladiofreire on 21/8/17.
 */

public class WebService {

    public static String url = "";

    private static final long READ_TIMEOUT = 30;
    private static final long CONNECT_TIMEOUT = 30;

    /**
     * For get request.
     *
     * @param host host name.
     * @param id   put null if don't need use id.
     * @return response.
     */
    public static Response getRequest(String host, Bundle id) {
        try {

            /**  HTTP CLIENT CONSTRUCTION */
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            okHttpBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
            okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });


            final OkHttpClient okHttpClient = okHttpBuilder.build();
            if (id != null) {
                url = Constants.URL + host + "/" + id.getInt("id");
            } else {
                url = Constants.URL + host;
            }
            /** Url request builder */
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();


            String urlGetRequest = urlBuilder.build().toString();


            /** Request builder with constructed url (urlbuilder) and parameters */
            Request request = (new Request.Builder())
                    .url(urlGetRequest)
                    .header("Accept-Encoding", "x-zip")
                    .build();

            /** Request EXECUTION */
            Response response = okHttpClient.newCall(request).execute();
            System.out.println("Response code: " + response.code() + response + response.protocol());

            /** Response */
            return response;


        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
            return null;
        }
    }

    /**
     * For post request.
     *
     * @param jsonObjSend json with the values.
     * @param context     context.
     * @param host        host name.
     * @return Response.
     */
    public static Response sendHttpsPost(final String jsonObjSend, final Context context, final String host) {

        try {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(final String s, final SSLSession sslSession) {
                    return true;
                }
            });

            final OkHttpClient httpClient = builder.build();

            final MediaType type = MediaType.parse("application/json; charset=utf-8");
            final RequestBody body = RequestBody.create(type, jsonObjSend);

            final Request request = new Request.Builder()
                    .url(Constants.URL + host)
                    .method("POST", body)
                    .build();

            final Response response = httpClient.newCall(request).execute();


            return response;

        } catch (Exception e) {

            return null;
        }
    }


}
