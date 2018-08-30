package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class SalesOrder {
    private int id;
    private User userId;
    private Customer customer;
    private String orderDate;
    private String deliveryDate;
    private String paymentMode;
    private List<OrderItem> items;
    private String remarks;
    private double total;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public List<OrderItem> getItems() {
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addItem(OrderItem item) {
        getItems().add(item);
    }

    public void addItems(List<OrderItem> items){
        getItems().addAll(items);
    }

    public String toJson() {
        Gson gson=getGson();
        return  gson.toJson(this);
    }
    public static SalesOrder fromJson(String json){
        Gson gson=getGson();
        return  gson.fromJson(json,SalesOrder.class);
    }
}
