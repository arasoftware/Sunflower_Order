package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class DeliveryItem {
    @SerializedName("so_entry_product_details_id")
    private int id;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_code")
    private String productCode;

    @SerializedName("so_entry_product_details_qty")
    private int quantity;
    private int accept;
    private int reject;

    public DeliveryItem() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static DeliveryItem fromJSON(String result) {

        Gson gson = getGson();
        DeliveryItem deliveryItem = gson.fromJson(result, DeliveryItem.class);
        return deliveryItem;
    }

    public static List<DeliveryItem> fromJSONArray(String messsage) {
        Gson gson = getGson();
        DeliveryItem[] deliveryItems = gson.fromJson(messsage, DeliveryItem[].class);
        return Arrays.asList(deliveryItems);
    }
}
