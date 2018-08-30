package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class User {

    @SerializedName("user_id")
    private int id;

    private String userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_branch_ids")
    private String branchId;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static User fromJson(String message) {
        Gson gson = getGson();
        return gson.fromJson(message, User.class);
    }
}
