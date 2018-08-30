package com.ara.sunflowerorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ara.sunflowerorder.adapters.DeliveryItemAdapter;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.Approval;
import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.Delivery;
import com.ara.sunflowerorder.models.DeliveryItem;
import com.ara.sunflowerorder.models.Warehouse;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.CurrentUser;
import static com.ara.sunflowerorder.utils.AppConstants.DELIVERY_ITEM_EDIT_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.ENTRY_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_CUSTOMER;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_DELIVERY_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_ITEM_INDEX;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_APPROVE_ID_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_WAREHOUSE_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.fetchWarehouse;
import static com.ara.sunflowerorder.utils.AppConstants.getApprovedProducts;
import static com.ara.sunflowerorder.utils.AppConstants.getDeliverySubmitURL;
import static com.ara.sunflowerorder.utils.AppConstants.showProgressBar;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class DeliveryActivity extends AppCompatActivity implements ListViewClickListener {

    Delivery deliveryModel;
    Customer customer;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.tv_delivery_customer)
    TextView tv_customer;

    @BindView(R.id.delivery_order_date)
    TextView tv_deliveryDate;

    @BindView(R.id.delivery_delivery_date)
    TextView tv_deliveryTodayDate;


    @BindView(R.id.tv_delivery_approve_id)
    TextView tv_approveId;

    @BindView(R.id.delivery_item_list_view)
    RecyclerView recyclerView;

    @BindView(R.id.delivery_total_qty)
    TextView tv_totalQty;

    @BindView(R.id.delivery_accepted_quantity)
    TextView tv_acceptedQty;

    @BindView(R.id.tv_delivery_warehouse)
    TextView tv_deliveryWarehouse;

    DeliveryActivity thisActivity;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);
        fetchWarehouse();
        getSupportActionBar().hide();
        tv_deliveryTodayDate.setText(AppConstants.dateToString(Calendar.getInstance()));
        recyclerView = findViewById(R.id.delivery_item_list_view);
        deliveryModel = new Delivery();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //If list is empty then recycler view will throw exception. So need to hide if empty.
        recyclerView.setVisibility(View.GONE);

        //Used in the inner classes like HttpCaller
        thisActivity = this;
    }

    @OnClick(R.id.tv_delivery_warehouse)
    public void chooseWarehouse(View view) {
        Intent intent = new Intent(this, ListHelperActivity.class);
        intent.putExtra(REQUEST_CODE, LIST_WAREHOUSE_REQUEST);
        startActivityForResult(intent, LIST_WAREHOUSE_REQUEST);
    }

    @OnClick(R.id.tv_delivery_customer)
    public void chooseCustomer(View view) {
        Intent customerList = new Intent(this, SearchHelper.class);
        customerList.putExtra(REQUEST_CODE, SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST);
        startActivityForResult(customerList, SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST);
    }

    @OnClick(R.id.tv_delivery_approve_id)
    public void chooseApproveNo(View view) {
        if (customer == null) {
            showSnackbar(tv_customer, "Please choose Customer first");
            return;
        }
        Intent intent = new Intent(this, ListHelperActivity.class);
        intent.putExtra(EXTRA_SELECTED_CUSTOMER, customer.getId());
        intent.putExtra(REQUEST_CODE, LIST_APPROVE_ID_REQUEST);
        startActivityForResult(intent, LIST_APPROVE_ID_REQUEST);
    }

    @OnClick(R.id.submit_delivery)
    public void onDeliverySubmit() {
        if (deliveryModel.getDeliveryItems().size() == 0) {
            showSnackbar(tv_customer, "No Items found to deliver");
            return;
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        String json = data.getStringExtra(EXTRA_SEARCH_RESULT);
        switch (requestCode) {
            case SEARCH_CUSTOMER_FOR_DELIVERY_REQUEST:

                customer = Customer.fromJSON(json);
                tv_customer.setText(customer.getName());

                break;
            case LIST_APPROVE_ID_REQUEST:
                Approval approval = Approval.fromJSON(json);
                deliveryModel.setCustomer(customer);
                deliveryModel.setApproval(approval);
                tv_approveId.setText(deliveryModel.getApproval().getApproveNo() + "");
                tv_deliveryDate.setText(deliveryModel.getApproval().getDate());
                progressDialog = showProgressBar(this, "Loading Products");
                HttpRequest httpRequest = new HttpRequest(getApprovedProducts(), HttpRequest.GET);
                httpRequest.addParam(ENTRY_ID_PARAM, approval.getId() + "");
                new HttpCaller() {
                    @Override
                    public void onResponse(HttpResponse response) {

                        if (response.getStatus() == HttpResponse.ERROR) {
                            showSnackbar(tv_approveId, response.getMesssage());
                        } else {
                            List<DeliveryItem> deliveryItemList = DeliveryItem.fromJSONArray(response.getMesssage());
                            deliveryModel.setDeliveryItems(deliveryItemList);
                            updateOM();
                            mAdapter = new DeliveryItemAdapter(deliveryModel.getDeliveryItems(), thisActivity);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                            updateTotal();
                            updateAccepted();
                        }
                        progressDialog.dismiss();
                    }
                }.execute(httpRequest);


                break;
            case DELIVERY_ITEM_EDIT_REQUEST:
                int position = data.getIntExtra(EXTRA_SELECTED_ITEM_INDEX, -1);
                DeliveryItem deliveryItem = DeliveryItem.fromJSON(json);
                updateRecyclerView(deliveryItem, position);
                updateAccepted();
                break;
            case LIST_WAREHOUSE_REQUEST:
                json = data.getStringExtra(EXTRA_SEARCH_RESULT);
                Warehouse warehouse = Warehouse.fromJson(json);
                deliveryModel.setWarehouse(warehouse);
                tv_deliveryWarehouse.setText(warehouse.getName());
                break;
        }
    }

    @Override
    public void onItemClick(Object selectedObject, int position) {
        Intent intent = new Intent(this, DeliveryItemActivity.class);
        DeliveryItem deliveryItem = (DeliveryItem) selectedObject;
        String json = deliveryItem.toJson();
        intent.putExtra(EXTRA_SELECTED_DELIVERY_ITEM, json);
        intent.putExtra(EXTRA_SELECTED_ITEM_INDEX, position);
        startActivityForResult(intent, DELIVERY_ITEM_EDIT_REQUEST);
    }

    private void updateRecyclerView(DeliveryItem deliverItem, int position) {
        List<DeliveryItem> items = deliveryModel.getDeliveryItems();
        DeliveryItem actualItem = items.get(position);
        actualItem.setAccept(deliverItem.getAccept());
        actualItem.setReject(deliverItem.getReject());
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.notifyItemChanged(position);
    }

    private void updateOM() {
        List<DeliveryItem> items = deliveryModel.getDeliveryItems();
        for (int i = 0; i < items.size(); i++) {
            DeliveryItem item = items.get(i);
            item.setAccept(item.getQuantity());
            item.setReject(0);

        }
    }

    private void updateTotal() {
        int total = 0;
        for (DeliveryItem item : deliveryModel.getDeliveryItems()) {
            total += item.getQuantity();
        }
        tv_totalQty.setText(total + "");
    }

    private void updateAccepted() {
        int total = 0;
        for (DeliveryItem item : deliveryModel.getDeliveryItems()) {
            total += item.getAccept();
        }
        tv_acceptedQty.setText(total + "");
    }

    @OnClick(R.id.submit_delivery)
    public void submitDelivery(View view) {
        if (!validate()) {

            return;
        }

        final HttpRequest httpRequest = new HttpRequest(getDeliverySubmitURL(), HttpRequest.POST);
        deliveryModel.setUser(CurrentUser);
        deliveryModel.setDeliveryDate(tv_deliveryTodayDate.getText().toString());

        httpRequest.addParam("data", deliveryModel.toJson());
        Log.i("Delivery Submit", deliveryModel.toJson());
        progressDialog = showProgressBar(this, "Submitting..");
        new HttpCaller() {
            @Override
            public void onResponse(HttpResponse response) {
                progressDialog.dismiss();
                if (response.getStatus() == HttpResponse.ERROR)
                    showSnackbar(tv_approveId, response.getMesssage());
                else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }.execute(httpRequest);


    }

    private boolean validate() {
        boolean isValid = true;
        if (deliveryModel.getDeliveryItems().size() == 0) {
            showSnackbar(tv_approveId, "Add one or more item.");
            return false;
        }
        if (deliveryModel.getCustomer() == null) {
            showSnackbar(tv_approveId, "Select a Customer Name.");

            return false;
        }
        return true;
    }
}
