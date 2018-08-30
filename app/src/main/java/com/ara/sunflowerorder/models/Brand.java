package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Brand {
    @SerializedName("brand_id")
    private int id;
    @SerializedName("brand_name")
    private String name;

    public Brand() {
    }

    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static List<Brand> fromJSONArray(String json) {
        Gson gson = getGson();
        Brand[] brands = gson.fromJson(json, Brand[].class);
        List<Brand> brandList = Arrays.asList(brands);
        return brandList;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Brand fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Brand(-1, "");
        }
        Gson gson = new Gson();
        Brand brand = gson.fromJson(result, Brand.class);
        return brand;
    }
}
