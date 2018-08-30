package com.ara.sunflowerorder.models.view;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class SalesOrderReport {
    private int salesOrderId;
    @SerializedName("so_entry_no")
    private String salesOrderNo;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("so_entry_date")
    private String date;

    @SerializedName("product_uom_name")
    private String uom;

    @SerializedName("so_entry_product_details_rate")
    private double price;

    @SerializedName("so_entry_product_details_qty")
    private int quantity;

    @SerializedName("so_entry_product_details_amount")
    private double total;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(int salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public static List<SalesOrderReport> fromJsonArray(String messsage) {
        Gson gson = getGson();
        SalesOrderReport[] salesOrderReports = gson.fromJson(messsage, SalesOrderReport[].class);
        return Arrays.asList(salesOrderReports);

    }
}
