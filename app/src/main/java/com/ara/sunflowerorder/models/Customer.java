package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Customer {
    @SerializedName("customer_id")
    private int id;
    @SerializedName("customer_name")
    private String name;
    @SerializedName("customer_code")
    private String code;


    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    @Override
    public String toString() {
        return getName();
    }

    public static Customer fromJSON(String json) {
        if (json == null || json.isEmpty()) {
            return new Customer(-1, "");
        }
        Gson gson = getGson();
        Customer customer = gson.fromJson(json, Customer.class);
        return customer;
    }

    public static List<Customer> fromJSONArray(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>(0);
        }
        Gson gson = getGson();
        Customer[] customers = gson.fromJson(json, Customer[].class);
        return Arrays.asList(customers);
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }
}
