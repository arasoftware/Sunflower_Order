package com.ara.sunflowerorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ara.sunflowerorder.models.Approval;
import com.ara.sunflowerorder.models.Brand;
import com.ara.sunflowerorder.models.Product;
import com.ara.sunflowerorder.models.Warehouse;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static com.ara.sunflowerorder.utils.AppConstants.BRAND_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.CUSTOMER_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_CUSTOMER;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_APPROVE_ID_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_BRAND_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_WAREHOUSE_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.List_PRODUCT_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.WarehouseList;
import static com.ara.sunflowerorder.utils.AppConstants.getApproveListURL;
import static com.ara.sunflowerorder.utils.AppConstants.getBrandListURL;
import static com.ara.sunflowerorder.utils.AppConstants.getProductListURL;
import static com.ara.sunflowerorder.utils.AppConstants.showProgressBar;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class ListHelperActivity extends AppCompatActivity {

    private int requestCode;
    int searchId;
    int selectedCustomerId;
    @BindView(R.id.list_helper)
    ListView listView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_helper);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        requestCode = intent.getIntExtra(REQUEST_CODE, -1);
        selectedCustomerId = intent.getIntExtra(EXTRA_SELECTED_CUSTOMER, -1);
        searchId = intent.getIntExtra(BRAND_ID_PARAM, -1);
        fetchData();
    }

    private HttpRequest getHttpRequest() {
        HttpRequest httpRequest = null;
        switch (requestCode) {
            case LIST_APPROVE_ID_REQUEST:

                httpRequest = new HttpRequest((getApproveListURL()), HttpRequest.GET);

                httpRequest.addParam(CUSTOMER_ID_PARAM, selectedCustomerId + "");
                break;
            case LIST_BRAND_REQUEST:
                httpRequest = new HttpRequest(getBrandListURL(), HttpRequest.GET);
                break;
            case List_PRODUCT_REQUEST:
                httpRequest = new HttpRequest(getProductListURL(), HttpRequest.GET);
                httpRequest.addParam(BRAND_ID_PARAM, searchId + "");
                break;

        }
        return httpRequest;
    }

    public void fetchData() {
        if (requestCode == LIST_WAREHOUSE_REQUEST) {
            setListView(null);
            return;
        }
        HttpRequest httpRequest = getHttpRequest();
        if (httpRequest == null) {
            Snackbar.make(listView, "Mr Developer: Please set the Request Code:",
                    Snackbar.LENGTH_INDEFINITE).show();
            return;
        }
        progressDialog = showProgressBar(this, "Loading data..");
        new HttpCaller() {
            @Override
            public void onResponse(HttpResponse response) {

                if (response.getStatus() == HttpResponse.ERROR) {
                    Snackbar.make(listView, response.getMesssage(), Snackbar.LENGTH_LONG).show();
                } else {
                    setListView(response.getMesssage());
                }
                progressDialog.dismiss();
            }
        }.execute(httpRequest);
    }

    private void setListView(String json) {
        switch (requestCode) {
            case LIST_APPROVE_ID_REQUEST:
                List<Approval> approvalList = Approval.fromJSONArray(json);
                if (approvalList.size() == 0) {
                    showSnackbar(listView, "No Approval ids..");
                    return;
                }
                ArrayAdapter<Approval> approvalArrayAdapter = new ArrayAdapter<Approval>(
                        listView.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        approvalList
                );
                listView.setAdapter(approvalArrayAdapter);
                break;
            case LIST_BRAND_REQUEST:
                List<Brand> brandList = Brand.fromJSONArray(json);
                ArrayAdapter<Brand> brands = new ArrayAdapter<Brand>(
                        listView.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        brandList
                );
                listView.setAdapter(brands);
                break;

            case List_PRODUCT_REQUEST:
                List<Product> productList = Product.fromJSONArray(json);
                ArrayAdapter<Product> products = new ArrayAdapter<Product>(
                        listView.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        productList
                );
                listView.setAdapter(products);
                break;
            case LIST_WAREHOUSE_REQUEST:
                ArrayAdapter<Warehouse> warehouseArrayAdapter = new ArrayAdapter<Warehouse>(
                        listView.getContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        WarehouseList
                );
                listView.setAdapter(warehouseArrayAdapter);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @OnItemClick(R.id.list_helper)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String result = null;
        switch (requestCode) {
            case LIST_APPROVE_ID_REQUEST:
                Approval approval = (Approval) listView.getAdapter().getItem(position);
                result = approval.toJson();
                break;
            case LIST_BRAND_REQUEST:
                Brand brand = (Brand) listView.getAdapter().getItem(position);
                result = brand.toJson();
                break;
            case List_PRODUCT_REQUEST:
                Product product = (Product) listView.getAdapter().getItem(position);
                result = product.toJson();
                break;
            case LIST_WAREHOUSE_REQUEST:
                Warehouse warehouse = (Warehouse) listView.getAdapter().getItem(position);
                result = warehouse.toJson();
                break;

        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SEARCH_RESULT, result);
        setResult((result != null) ? RESULT_OK : RESULT_CANCELED, intent);
        finish();
    }

}
