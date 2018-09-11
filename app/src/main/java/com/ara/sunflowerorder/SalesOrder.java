package com.ara.sunflowerorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.ara.sunflowerorder.adapters.OrderItemAdapter;
import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.OrderItem;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.ADD_ITEM_REQUEST;

import static com.ara.sunflowerorder.utils.AppConstants.CurrentUser;
import static com.ara.sunflowerorder.utils.AppConstants.DELIVERY_DATE_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_ADD_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_DATE_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.ORDER_DATE_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SALES_ORDER;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.SalesOrderList;
import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;
import static com.ara.sunflowerorder.utils.AppConstants.getSalesOrderSubmitURL;
import static com.ara.sunflowerorder.utils.AppConstants.showProgressBar;

public class SalesOrder extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    com.ara.sunflowerorder.models.SalesOrder salesOrderModel;

    @BindView(R.id.tv_customer)
    TextView customer_tv;
    @BindView(R.id.order_date_edit)
    TextView order_date_tv;
    @BindView(R.id.delivery_date_edit)
    TextView delivery_date_tv;
    @BindView(R.id.sales_order_total_amount)
    TextView total_amount_tv;
    @BindView(R.id.sp_order_payment_mode)
    Spinner spinnerPaymentMode;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);

        getSupportActionBar().hide();
        mRecyclerView = findViewById(R.id.order_item_list_view);
        salesOrderModel = new com.ara.sunflowerorder.models.SalesOrder();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        mRecyclerView.setHasFixedSize(true);
        salesOrderModel.setItems(new ArrayList<OrderItem>());

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setVisibility(View.GONE);

        mAdapter = new OrderItemAdapter(salesOrderModel.getItems());
        mRecyclerView.setAdapter(mAdapter);
        ButterKnife.bind(this);


        Calendar today = Calendar.getInstance();
        order_date_tv.setText(AppConstants.dateToString(today));
        today.add(Calendar.DATE, 2);
        delivery_date_tv.setText(AppConstants.dateToString(today));
    }

    public void select_customer_onClick(View view) {
        Intent customerList = new Intent(this, SearchHelper.class);
        customerList.putExtra(REQUEST_CODE, SEARCH_CUSTOMER_REQUEST);
        startActivityForResult(customerList, SEARCH_CUSTOMER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String json = null;
        switch (requestCode) {
            case SEARCH_CUSTOMER_REQUEST:
                if (resultCode == RESULT_OK) {
                    json = data.getStringExtra(EXTRA_SEARCH_RESULT);
                    Customer customer = Customer.fromJSON(json);
                    customer_tv.setText(customer.getName());
                    salesOrderModel.setCustomer(customer);
                }
                break;
            case ADD_ITEM_REQUEST:
                if (resultCode == RESULT_OK) {
                    json = data.getStringExtra(EXTRA_ADD_ITEM);
                    int total = 0;
                    List<OrderItem> orderItems = OrderItem.fromJsonArray(json);
                    int index = salesOrderModel.getItems().size() - 1;
                    salesOrderModel.getItems().addAll(orderItems);

                    for (int j=0;j<salesOrderModel.getItems().size();j++){
                       total = total + salesOrderModel.getItems().get(j).getTotalAmount();

                    }
                    total_amount_tv.setText(formatPrice(total));
                    mAdapter.notifyItemRangeChanged(index, orderItems.size());
                    if (salesOrderModel.getItems().size() >= 1)
                        mRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
            case DELIVERY_DATE_REQUEST:
                if (resultCode == RESULT_OK) {
                    json = data.getStringExtra(EXTRA_DATE_RESULT);
                    salesOrderModel.setDeliveryDate(json);
                    delivery_date_tv.setText(json);
                }
                break;
            case ORDER_DATE_REQUEST:
                if (resultCode == RESULT_OK) {
                    json = data.getStringExtra(EXTRA_DATE_RESULT);
                    salesOrderModel.setOrderDate(json);
                    order_date_tv.setText(json);
                }
                break;

        }

    }

    @OnClick({R.id.order_date_edit, R.id.delivery_date_edit})
    public void dateClicked(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        if (view.getId() == R.id.order_date_edit) {
            startActivityForResult(intent, ORDER_DATE_REQUEST);
        } else {
            startActivityForResult(intent, DELIVERY_DATE_REQUEST);
        }
    }

    @OnClick(R.id.btn_submit_order)
    public void onSubmit(View view) {
        if (!validate())
            return;
        salesOrderModel.setPaymentMode(spinnerPaymentMode.getSelectedItem().toString());
        salesOrderModel.setOrderDate(order_date_tv.getText().toString());
        salesOrderModel.setDeliveryDate(delivery_date_tv.getText().toString());
        salesOrderModel.setTotal(Double.parseDouble(total_amount_tv.getText().toString()));
        final HttpRequest httpRequest = new HttpRequest(getSalesOrderSubmitURL(), HttpRequest.POST);
        salesOrderModel.setUserId(CurrentUser);
        httpRequest.addParam("data", salesOrderModel.toJson());
        Log.e("Sales Order Submit","------------------------------------"+ salesOrderModel.toJson());
        progressDialog = showProgressBar(this, "Submitting");
        new HttpCaller() {
            @Override
            public void onResponse(HttpResponse response) {
                progressDialog.dismiss();
                Log.e("TAG","reposens ----------12---"+response.getMesssage());
                if (response.getStatus() == HttpResponse.ERROR)
                    showSnackbar(response.getMesssage());
                else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }.execute(httpRequest);

    }

    private void showSnackbar(String messsage) {
        Snackbar.make(customer_tv, messsage, Snackbar.LENGTH_LONG).show();
    }

    public boolean validate() {
        if (salesOrderModel.getItems().size() == 0) {
            showSnackbar("Add atleast one item.");
            return false;
        }
        if (salesOrderModel.getCustomer() == null) {
            showSnackbar("Choose a Customer.");
            return false;
        }

        return true;
    }


    @OnClick(R.id.btn_order_add_item)
    public void onAddItemClick(View view) {
        Intent addItemIntent = new Intent(this, com.ara.sunflowerorder.MultiOrderActivity.class);
        addItemIntent.putExtra(SALES_ORDER, salesOrderModel.toJson());
        startActivityForResult(addItemIntent, ADD_ITEM_REQUEST);
    }

}
