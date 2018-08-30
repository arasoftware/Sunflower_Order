package com.ara.sunflowerorder.utils.http;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;

/**
 * Created by sathishbabur on 1/31/2018.
 */

public class HttpRequest {
    public static final int GET = 1;

    public static final int POST = 2;

    private RequestBody requestBody;


    private String url;

    private int methodtype;

    private HashMap<String, String> params;

    public HttpRequest(String url) {
        this(url, POST);
    }

    public HttpRequest(String url, int methodtype) {
        this.url = url;
        this.methodtype = methodtype;
    }


    public String getUrl() {

        return url;
    }


    public void setUrl(String url) {

        this.url = url;

    }


    public int getMethodType() {

        return methodtype;

    }


    public void setMethodtype(int methodtype) {

        this.methodtype = methodtype;

    }

    public String getMethodName() {
        switch (methodtype) {
            case GET:
                return "GET";
            case POST:
                return "POST";
            default:
                throw new UnsupportedOperationException("MethodType is not supported");
        }

    }

    public HashMap<String, String> getParams() {

        if (params == null) {
            params = new HashMap<>();
        }
        return params;

    }

    public void addParam(String name, String value) {
        HashMap<String, String> params = getParams();
        params.put(name, value);
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public RequestBody getRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody formBody = builder.build();
        return formBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }


    public String getURLWithParam() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(getUrl()).newBuilder();
        HashMap<String, String> params = getParams();
        Set<String> paramNames = params.keySet();
        for (String paramName : paramNames) {
            urlBuilder.addQueryParameter(paramName, params.get(paramName));
        }
        String url = urlBuilder.build().toString();
        return url;
    }
}

