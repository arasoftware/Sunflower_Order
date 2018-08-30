package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Approval {
    @SerializedName("so_entry_id")
    private int id;
    @SerializedName("so_entry_no")
    private String approveNo;
    @SerializedName("so_entry_date")
    private String date = "23-06-2018";

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public static List<Approval> fromJSONArray(String json) {
        Gson gson = getGson();
        Approval[] approvals = gson.fromJson(json, Approval[].class);
        if (approvals == null) {
            return new ArrayList<>(0);
        }
        return Arrays.asList(approvals);
    }

    @Override
    public String toString() {
        return "App No:" + approveNo + "\t\tApp Date:" + date;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Approval fromJSON(String result) {
        Gson gson = new Gson();
        Approval approval = gson.fromJson(result, Approval.class);
        return approval;
    }
}
