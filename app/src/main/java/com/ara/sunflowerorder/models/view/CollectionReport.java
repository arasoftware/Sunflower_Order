package com.ara.sunflowerorder.models.view;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class CollectionReport {
    private int collectionId;
    @SerializedName("collection_entry_no")
    private String collectionNo;
    @SerializedName("collection_entry_date")
    private String date;
    @SerializedName("customer_name")
    private String customerName;
    private String modeOfPayment;
    @SerializedName("invoice_entry_no")
    private String invoiceNo;
    private double invoiceAmt;
    @SerializedName("collection_entry_details_amount")
    private double collectionAmt;
    private double balanceAmt;


    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(String collectionNo) {
        this.collectionNo = collectionNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public double getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(double invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public double getCollectionAmt() {
        return collectionAmt;
    }

    public void setCollectionAmt(double collectionAmt) {
        this.collectionAmt = collectionAmt;
    }

    public double getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(double balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public static List<CollectionReport> fromJsonArray(String messsage) {
        Gson gson = getGson();
        CollectionReport[] collectionReports = gson.fromJson(messsage, CollectionReport[].class);
        if (collectionReports == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(collectionReports);

    }
}
