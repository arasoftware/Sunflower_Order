package com.ara.sunflowerorder.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.models.SalesOrder;
import com.ara.sunflowerorder.models.User;
import com.ara.sunflowerorder.models.Warehouse;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;

public class AppConstants {
    public static final String REST_API = "http://sunflower.sunflowergroups.com/app/sun_android_app.php?action=";
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final ArrayList<SalesOrder> SalesOrderList = new ArrayList<>();


    public static final int SEARCH_CUSTOMER_REQUEST = 101;
    public static final int LIST_BRAND_REQUEST = 102;
    public static final int List_PRODUCT_REQUEST = 103;
    public static final int ADD_SALES_ORDER_REQUEST = 104;

    public static final int ADD_ITEM_REQUEST = 105;
    public static final int SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST = 106;
    public static final int LIST_APPROVE_ID_REQUEST = 107;
    public static final int ADD_DELIVERY_REQUEST = 108;
    public static final int DELIVERY_ITEM_EDIT_REQUEST = 109;
    public static final int ADD_COLLECTION_REQUEST = 110;
    public static final int INVOICE_ITEM_EDIT_REQUEST = 111;
    public static final int LOGIN_REQUEST = 112;
    public static final int ORDER_DATE_REQUEST = 113;
    public static final int DELIVERY_DATE_REQUEST = 114;
    public static final int FROM_DATE_REQUEST = 115;
    public static final int TO_DATE_REQUEST = 116;
    public static final int LIST_WAREHOUSE_REQUEST = 117;


    public static final String EXTRA_SEARCH_RESULT = "SearchResult";
    public static final String EXTRA_SELECTED_CUSTOMER = "selectedCustomer";
    public static final String EXTRA_ADD_ITEM = "OrderItem";
    public static final String REQUEST_CODE = "RequestCode";
    public static final String EXTRA_SELECTED_DELIVERY_ITEM = "selecteDeliveryItem";
    public static final String EXTRA_SELECTED_ITEM_INDEX = "SelectedIndex";
    public static final String EXTRA_SELECTED_INVOICE_ITEM = "Invoice Item";
    public static final String EXTRA_DATE_RESULT = "dateResult";
    public static final String PREFERENCE_NAME = "Sunflower_order";
    public static final String USER_INFO_STORAGE = "UserInfo";

    public static final String BANK_LIST = REST_API+"banklist";
    public static final String USER_ID_PREF = "user_id";
    public static final String BRANCH_ID_PREF = "user_branch_ids";

    public static final String CUSTOMER_NAME_PARAM = "customer_name";
    public static final String CUSTOMER_ID_PARAM = "customer_id";
    public static final String BRAND_ID_PARAM = "brand_id";
    public static final String FROM_DATE_PARAM = "from_date";
    public static final String TO_DATE_PARAM = "to_date";
    public static final String USER_ID_PARAM = "user_id";
    public static final String PASSWORD_PARAM = "password";
    public static final String ENTRY_ID_PARAM = "so_entry_id";
    public static final String SALES_ORDER = "sales_order";
    public static User CurrentUser = null;
    public static List<Warehouse> WarehouseList;

    public static String getUserLoginURL() {
        return REST_API + "login";
    }

    public static String getCustomerListURL() {
        return REST_API + "customer";
    }

    public static String getBrandListURL() {
        return REST_API + "brand";
    }

    public static String getApproveListURL() {
        return REST_API + "so_approvel_list";
    }

    public static String getApprovedProducts() {
        return REST_API + "so_approvel_product";
    }

    public static String getWarehouseList() {
        return REST_API + "godown";
    }

    public static String getProductListURL() {
        return REST_API + "product";
    }

    public static String getInvoiceListURL() {
        return REST_API + "invoice";
    }

    public static String getCollectionSubmitURL() {
        return REST_API + "collection";
    }

    public static String getSalesOrderSubmitURL() {
        return REST_API + "sales_order";
    }

    public static String getDeliverySubmitURL() {
        return REST_API + "Delivery";
    }

    public static String getDeliveryReportURL() {
        return REST_API + "delivery_report";
    }

    public static String getSalesOrderReportURL() {
        return REST_API + "sales_report";
    }

    public static String getCollectionReportURL() {
        return REST_API + "collection_report";
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static Gson getGson() {
        return new Gson();
    }

    public static String dateToString(Calendar calendar) {
        return calendar.get(Calendar.DATE) + "-"
                + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.YEAR);
    }

    public static int getUOMSpinnerIndex(Spinner spinner, String itemName) {
        SpinnerAdapter arrayAdapter = spinner.getAdapter();
        for (int i = 0; i < arrayAdapter.getCount(); i++) {
            if (arrayAdapter.getItem(i) == itemName)
                return i;
        }
        return -1;
    }

    public static String formatPrice(double price) {
        return String.format("%8.2f", price).trim();
    }

    public static String formatQuantity(int qty) {
        return String.format("%d", qty).trim();
    }

    public static ProgressDialog showProgressBar(Context context, String progressMessage) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(progressMessage);

        return progressDialog;
    }

    public static int parseInt(String intValue) {
        if (intValue.isEmpty()) {
            return 0;
        }
        try {
            int value = Integer.parseInt(intValue);
            return value;
        } catch (NumberFormatException numberFormatException) {
            return 0;
        }
    }

    public static double parseDouble(String intValue) {
        if (intValue.isEmpty()) {
            return 0;
        }
        try {
            double value = Double.parseDouble(intValue);
            return value;
        } catch (NumberFormatException numberFormatException) {
            return 0;
        }
    }

    public static List<Warehouse> fetchWarehouse() {
        if (WarehouseList != null && WarehouseList.size() > 0) {
            return WarehouseList;
        }

        HttpRequest httpRequest = new HttpRequest(getWarehouseList(), HttpRequest.GET);

        new HttpCaller() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.getStatus() == HttpResponse.ERROR) {
                    WarehouseList = new ArrayList<>();
                } else {
                    WarehouseList = Warehouse.fromJsonArray(response.getMesssage());
                }
            }
        }.execute(httpRequest);
        return WarehouseList;
    }
}
