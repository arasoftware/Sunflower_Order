package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class SampleData {

    @SerializedName("paymentMode")
    String paymetMode;
    @SerializedName("accoutId")
    String accountId;

    public String getPaymetMode() {
        return paymetMode;
    }

    public void setPaymetMode(String paymetMode) {
        this.paymetMode = paymetMode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public SampleData(String paymetMode, String accountId) {

        this.paymetMode = paymetMode;
        this.accountId = accountId;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }
}
