package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Delivery {
    private int id;
    private User user;
    private Customer customer;
    private String deliveryDate;
    private Approval approval;
    private List<DeliveryItem> deliveryItems;
    private String remarks;
    private Warehouse warehouse;

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Delivery() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public List<DeliveryItem> getDeliveryItems() {
        if (deliveryItems == null)
            deliveryItems = new ArrayList<>();
        return deliveryItems;
    }

    public void setDeliveryItems(List<DeliveryItem> deliveryItems) {
        this.deliveryItems = deliveryItems;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static List<Delivery> fromJSONArray(String json) {
        Gson gson = getGson();
        Delivery[] deliveries = gson.fromJson(json, Delivery[].class);
        return Arrays.asList(deliveries);
    }

    @Override
    public String toString() {
        return id + "";
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Delivery fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Delivery();
        }
        Gson gson = new Gson();
        Delivery delivery = gson.fromJson(result, Delivery.class);
        return delivery;
    }
}
