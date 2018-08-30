package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Invoice {
    @SerializedName("invoice_entry_id")
    private int id;
    @SerializedName("invoice_entry_no")
    private String no;
    @SerializedName("invoice_entry_date")
    private String date;
    private Customer customer;
    @SerializedName("invoice_entry_total_amount")
    private double invoiceAmount;
    @SerializedName("col_amt")
    private double paidAmount;
    @SerializedName("balance_amount")
    private double balanceAmount;
    private double collectedAmount;
    private double pendingAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String id) {
        this.no = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public double getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public static List<Invoice> fromJSONArray(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>(0);
        }
        Gson gson = getGson();
        Invoice[] invoices = gson.fromJson(json, Invoice[].class);
        return Arrays.asList(invoices);
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Invoice fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Invoice();
        }
        Gson gson = new Gson();
        Invoice invoice = gson.fromJson(result, Invoice.class);
        return invoice;
    }
}
