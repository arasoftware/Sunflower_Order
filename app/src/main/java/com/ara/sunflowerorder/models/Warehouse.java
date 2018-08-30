package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Warehouse {

    @SerializedName("godown_id")
    private int id;
    @SerializedName("godown_name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Warehouse> fromJsonArray(String json) {
        Gson gson = getGson();
        Warehouse[] warehouses = gson.fromJson(json, Warehouse[].class);
        return Arrays.asList(warehouses);
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Warehouse fromJson(String message) {
        Gson gson = getGson();
        return gson.fromJson(message, Warehouse.class);
    }

    public String toString() {
        return this.name;
    }
}
