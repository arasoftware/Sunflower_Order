package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.RequestBody;

import static com.ara.sunflowerorder.utils.AppConstants.JSON_MEDIA_TYPE;
import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Collection {
    private int id;
    private User user;
    private Customer customer;
    private String paymentMode;
    private String date;
    private List<Invoice> invoiceList;
    private double totalAmount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public RequestBody toRequestBody() {

        RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, this.toJson());
        return requestBody;
    }
}
