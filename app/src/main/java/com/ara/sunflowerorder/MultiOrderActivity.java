package com.ara.sunflowerorder;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ara.sunflowerorder.fragments.OrderFragment;
import com.ara.sunflowerorder.models.Brand;
import com.ara.sunflowerorder.models.OrderItem;
import com.ara.sunflowerorder.models.Product;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.ara.sunflowerorder.utils.AppConstants.BRAND_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.LIST_BRAND_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.getProductListURL;
import static com.ara.sunflowerorder.utils.AppConstants.showProgressBar;

public class MultiOrderActivity extends AppCompatActivity implements OrderFragment.OnFragmentInteractionListener {

    @BindView(R.id.tv_multi_order_brandName)
    TextView tvBrandName;

    @BindView(R.id.list_view_products)
    ListView lvProducts;
    private Brand brand;

    ArrayAdapter<Product> products;
    ProgressDialog progressDialog;


    List<OrderItem> orderItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_order);
        ButterKnife.bind(this);

        orderItemList = new ArrayList<>();
    }

    @OnClick(R.id.tv_multi_order_brandName)
    public void selectBrand(View view) {
        Intent chooseBrand = new Intent(this, ListHelperActivity.class);
        chooseBrand.putExtra(REQUEST_CODE, LIST_BRAND_REQUEST);
        startActivityForResult(chooseBrand, LIST_BRAND_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        String result = data.getStringExtra(EXTRA_SEARCH_RESULT);
        switch (requestCode) {
            case LIST_BRAND_REQUEST:
                this.brand = Brand.fromJSON(result);
                tvBrandName.setText(this.brand.getName());
                loadProducts();
                break;
        }
    }

    private void loadProducts() {
        HttpRequest httpRequest;
        httpRequest = new HttpRequest(getProductListURL(), HttpRequest.GET);
        httpRequest.addParam(BRAND_ID_PARAM, brand.getId() + "");
        progressDialog = showProgressBar(this, "Loading data..");
        new HttpCaller() {
            @Override
            public void onResponse(HttpResponse response) {

                if (response.getStatus() == HttpResponse.ERROR) {
                    Snackbar.make(lvProducts, response.getMesssage(), Snackbar.LENGTH_LONG).show();
                } else {
                    List<Product> productList = Product.fromJSONArray(response.getMesssage());
                    products = new ArrayAdapter<Product>(
                            lvProducts.getContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            productList
                    );
                    lvProducts.setAdapter(products);
                }
                progressDialog.dismiss();
            }
        }.execute(httpRequest);
    }

    @OnItemClick(R.id.list_view_products)
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Product product = (Product) lvProducts.getAdapter().getItem(position);
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(product.getPrice());
        product.setBrand(brand);
        orderItem.setProduct(product);

        FragmentManager fragmentManager = getSupportFragmentManager();
        OrderFragment orderFragment = OrderFragment.newInstance(orderItem);

        orderFragment.show(fragmentManager, "frag_frame_order");


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.EXTRA_ADD_ITEM, OrderItem.toJsonArray(orderItemList));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFragmentInteraction(OrderItem orderItem) {
        orderItemList.add(orderItem);
    }
}
