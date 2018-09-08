package com.ara.sunflowerorder.models.view;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class DeliveryReport {
    @SerializedName("dc_entry_id")
    private int entryId;
    @SerializedName("dc_entry_uniq_id")
    private String entryUniqueId;
    @SerializedName("dc_entry_no")
    private String entryNo;
    @SerializedName("dc_entry_date")
    private String entryDate;
    @SerializedName("customer_name")
    private String customerName;
    @SerializedName("customer_code")
    private String customerCode;
    @SerializedName("product_code")
    private String productCode;
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("dc_entry_product_details_qty")
    private int quantity;
    @SerializedName("accept_qty")
    private int accept;
    @SerializedName("rejected_qty")
    private int reject;

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getReject() {
        return reject;
    }

    public void setReject(int reject) {
        this.reject = reject;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getEntryUniqueId() {
        return entryUniqueId;
    }

    public void setEntryUniqueId(String entryUniqueId) {
        this.entryUniqueId = entryUniqueId;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static List<DeliveryReport> fromJsonArray(String json) {
        Gson gson = getGson();
        DeliveryReport[] deliveryReports = gson.fromJson(json, DeliveryReport[].class);
        return Arrays.asList(deliveryReports);
    }
}
