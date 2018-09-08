package com.ara.sunflowerorder.models;

import com.ara.sunflowerorder.utils.AppConstants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderItem {

    private int id;
    private Product product;
    private int quantity;
    private double price;
    private int totalAmount;

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static OrderItem fromJson(String json) {
        if (json == null || json.isEmpty()) {
            return new OrderItem();
        }
        Gson gson = new Gson();
        OrderItem orderItem = gson.fromJson(json, OrderItem.class);
        return orderItem;
    }

    public String toJson() {
        Gson gson = AppConstants.getGson();
        return gson.toJson(this);
    }
    public static String toJsonArray(List<OrderItem> orderItems){
        Gson gson = AppConstants.getGson();
        return gson.toJson(orderItems);
    }
    public static List<OrderItem> fromJsonArray(String json){
        Gson gson = AppConstants.getGson();
        OrderItem[] orderItems= gson.fromJson(json,OrderItem[].class);
        return Arrays.asList(orderItems);
    }
}
