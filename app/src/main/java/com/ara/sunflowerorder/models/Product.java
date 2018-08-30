package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Product {
    @SerializedName("product_id")
    private int id;
    @SerializedName("product_name")
    private String name;
    private int availableQty;
    @SerializedName("product_selling_price")
    private double rate;
    private Brand brand;
    @SerializedName("product_uom_name")
    private String uom;
    @SerializedName("product_code")
    private String code;

    public Product() {
    }

    public Product(int id, String name, int availableQty, double price, Brand brand, String uom, String code) {
        this.id = id;
        this.name = name;
        this.availableQty = availableQty;
        this.rate = price;
        this.brand = brand;
        this.uom = uom;
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

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public double getPrice() {
        return rate;
    }

    public void setPrice(double price) {
        this.rate = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static List<Product> fromJSONArray(String json) {

        Gson gson = getGson();
        Product[] products = gson.fromJson(json, Product[].class);
        return Arrays.asList(products);

    }

    public String toString() {
        return this.name;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Product fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Product();
        }
        Gson gson = new Gson();
        Product product = gson.fromJson(result, Product.class);
        return product;
    }
}
