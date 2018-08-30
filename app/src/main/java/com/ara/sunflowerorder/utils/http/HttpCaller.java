
package com.ara.sunflowerorder.utils.http;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.ara.sunflowerorder.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.app.AlertDialog.THEME_HOLO_DARK;
import static com.ara.sunflowerorder.utils.http.HttpRequest.POST;


/**
 * Created by sathishbabur on 1/31/2018.
 */

public class HttpCaller extends AsyncTask<HttpRequest, String, HttpResponse> {


    private static final String UTF_8 = "UTF-8";

    public HttpCaller() {
        super();
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected HttpResponse doInBackground(HttpRequest... httpRequests) {
        HttpResponse httpResponse = new HttpResponse();

        HttpRequest httpRequest = httpRequests[0];

        OkHttpClient client = new OkHttpClient();
        try {

            Request request;
            if (httpRequest.getMethodType() == POST) {
                request = new Request.Builder()
                        .url(httpRequest.getUrl())
                        .post(httpRequest.getRequestBody())
                        .build();
            } else {

                request = new Request.Builder()
                        .url(httpRequest.getURLWithParam())
                        .build();
            }
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                httpResponse.setErrorMessage("Response Body is null,Contact Support");
                return httpResponse;
            }
            String message = responseBody.string();
            if (response.isSuccessful()) {
                httpResponse.setSuccessMessage(message);
            } else {
                httpResponse.setErrorMessage(message);
            }
            responseBody.close();
            return httpResponse;

        } catch (Exception e) {

            Log.e("Http URL", e.toString());
            httpResponse.setErrorMessage(e.getMessage());
        }
        return httpResponse;
    }


    @Override
    protected void onPostExecute(HttpResponse response) {



        onResponse(response);
    }

    public void onResponse(HttpResponse response) {
    }
}