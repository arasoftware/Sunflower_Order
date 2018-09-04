package com.ara.sunflowerorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ara.sunflowerorder.adapters.CollectionBanksAdapter;
import com.ara.sunflowerorder.adapters.InvoiceAdapter;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.Bank;
import com.ara.sunflowerorder.models.Collection;
import com.ara.sunflowerorder.models.Customer;
import com.ara.sunflowerorder.models.Invoice;
import com.ara.sunflowerorder.models.SampleData;
import com.ara.sunflowerorder.models.User;
import com.ara.sunflowerorder.utils.AppConstants;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.ara.sunflowerorder.utils.AppConstants.BANK_LIST;
import static com.ara.sunflowerorder.utils.AppConstants.BRANCH_ID_PREF;
import static com.ara.sunflowerorder.utils.AppConstants.CurrentUser;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_INVOICE_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_ITEM_INDEX;
import static com.ara.sunflowerorder.utils.AppConstants.INVOICE_ITEM_EDIT_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.PREFERENCE_NAME;
import static com.ara.sunflowerorder.utils.AppConstants.REQUEST_CODE;
import static com.ara.sunflowerorder.utils.AppConstants.SEARCH_CUSTOMER_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;
import static com.ara.sunflowerorder.utils.AppConstants.getCollectionSubmitURL;
import static com.ara.sunflowerorder.utils.AppConstants.showProgressBar;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class CollectionActivity extends AppCompatActivity implements ListViewClickListener {

    String collectionMode[] = {"cash","Bank"};
    String paymentmode[ ] = {"Please select payment mode"};
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Customer customer;
    Collection collection;
    CollectionActivity collectionActivity;
    CollectionBanksAdapter adapter;
    ArrayList<Bank> arrayList = new ArrayList<>();

    @BindView(R.id.tv_coll_customer)
    TextView tvCustomer;
    @BindView(R.id.tv_coll_date)
    TextView tvTodayDate;
    @BindView(R.id.tv_coll_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.spinner_coll_payment_mode)
    Spinner spinnerPaymentMode;
    @BindView(R.id.coll_item_list_view)
    RecyclerView recyclerView;
    @BindView(R.id.spinnerBankList)
    Spinner banklist_spinner;
    ProgressDialog progressDialog;
    String branchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        branchID = sharedPreferences.getString(BRANCH_ID_PREF, "");
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,collectionMode);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMode.setAdapter(aa);
        spinnerPaymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String data = collectionMode[i];
                if (data.equalsIgnoreCase("Bank")){
                    collection.setPaymentMode("Bank");
                    getBankList();
                } else if (data.equalsIgnoreCase("cash")){
                    collection.setPaymentMode("cash");
                    collection.setAccountId(0);
                    arrayList.clear();

                    //nothing to do
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        collection = new Collection();
        collectionActivity = this;
        //recyclerView = (RecyclerView) findViewById(R.id.coll_item_list_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //If list is empty then recycler view will throw exception. So need to hide if empty.
        recyclerView.setVisibility(View.GONE);

    }


    @OnClick(R.id.tv_coll_customer)
    public void selectCustomer(View view) {
        Intent customerList = new Intent(this, SearchHelper.class);
        customerList.putExtra(REQUEST_CODE, SEARCH_CUSTOMER_REQUEST);
        startActivityForResult(customerList, SEARCH_CUSTOMER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case SEARCH_CUSTOMER_REQUEST:
                if (resultCode == RESULT_OK) {
                    String json = data.getStringExtra(EXTRA_SEARCH_RESULT);
                    customer = Customer.fromJSON(json);

                    HttpRequest httpRequest = new HttpRequest(AppConstants.getInvoiceListURL(), HttpRequest.GET);
                    httpRequest.addParam(AppConstants.CUSTOMER_ID_PARAM, customer.getId() + "");
                    progressDialog = showProgressBar(this, "Loading Invoices..");
                    new HttpCaller() {
                        @Override
                        public void onResponse(HttpResponse response) {
                            if (response.getStatus() == HttpResponse.ERROR) {
                                showSnackbar(tvCustomer, "Something went wrong, contact support");
                            } else {
                                String json = response.getMesssage();
                                List<Invoice> invoices = Invoice.fromJSONArray(json);
                                collection.setInvoiceList(invoices);
                                mAdapter = new InvoiceAdapter(collection.getInvoiceList(), collectionActivity);
                                recyclerView.setAdapter(mAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            progressDialog.dismiss();
                        }
                    }.execute(httpRequest);

                    tvCustomer.setText(customer.getName());
                    collection.setCustomer(customer);
                    collection.setDate(AppConstants.dateToString(Calendar.getInstance()));
                    tvTodayDate.setText(collection.getDate());
                }
                break;
            case INVOICE_ITEM_EDIT_REQUEST:
                String json = data.getStringExtra(EXTRA_SEARCH_RESULT);

                Invoice tempInvoice = Invoice.fromJSON(json);
                int position = data.getIntExtra(EXTRA_SELECTED_ITEM_INDEX, -1);
                Invoice invoice = collection.getInvoiceList().get(position);
                invoice.setCollectedAmount(tempInvoice.getCollectedAmount());
                invoice.setPendingAmount(invoice.getBalanceAmount() - invoice.getCollectedAmount());
                mAdapter.notifyItemChanged(position);
                double total = 0;
                for (Invoice item : collection.getInvoiceList()) {
                    total += item.getCollectedAmount();
                }
                tvTotalAmount.setText(formatPrice(total));
                break;
        }
    }


    @Override
    public void onItemClick(Object selectedObject, int position) {
        Invoice invoice = (Invoice) selectedObject;
        Intent intent = new Intent(this, InvoceItemActivity.class);
        String json = invoice.toJson();
        intent.putExtra(EXTRA_SELECTED_INVOICE_ITEM, json);
        intent.putExtra(EXTRA_SELECTED_ITEM_INDEX, position);
        startActivityForResult(intent, INVOICE_ITEM_EDIT_REQUEST);
    }


    public void getBankList() {

        final HttpRequest httpRequest = new HttpRequest(BANK_LIST, HttpRequest.POST);
        httpRequest.addParam("branch_id", branchID);
        progressDialog = showProgressBar(this, "Please Wait..");
        new HttpCaller() {
            @Override
            public void onResponse(HttpResponse response) {
                progressDialog.dismiss();
                if (response.getStatus() == HttpResponse.ERROR)
                    showSnackbar(tvCustomer, response.getMesssage());
                else {
                    {
                        Log.e("LOG","Bank List : "+ response.getMesssage());
                        try {
                            JSONArray jsonArray = new JSONArray(response.getMesssage());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject= jsonArray.getJSONObject(i);
                                String accounts_id = jsonObject.getString("accounts_id");
                                String accounts_name = jsonObject.getString("accounts_name");
                                String accounts_group_id = jsonObject.getString("accounts_group_id");
                                String accounts_type = jsonObject.getString("accounts_type");
                                Bank bank = new Bank();
                                bank.setAccounts_id(accounts_id);
                                bank.setAccounts_name(accounts_name);
                                bank.setAccounts_group_id(accounts_group_id);
                                bank.setAccounts_type(accounts_type);
                                arrayList.add(bank);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG","error : "+ e);
                        }
                        adapter = new CollectionBanksAdapter(CollectionActivity.this, android.R.layout.simple_spinner_item, arrayList);
                        banklist_spinner.setAdapter(adapter); // Set the custom adapter to the spinner
                        // You can create an anonymous listener to handle the event when is selected an spinner item
                        banklist_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {
                                // Here you get the current item (a User object) that is selected by its position
                                Bank bank = adapter.getItem(position);

                                // Here you can do the action you want to...
                                collection.setAccountId(Integer.parseInt(bank.getAccounts_id()));
                                Log.e("TAG","message : "+Integer.parseInt(bank.getAccounts_id()));

                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapter) {  }
                        });

                    }
                }

            }
        }.execute(httpRequest);
    }



    @OnClick(R.id.btn_submit_coll)
    public void onSubmit() {
        if (!validate())
            return;

        Log.e("LOG_TAG", "json response : " + collection.toJson());
        final HttpRequest httpRequest = new HttpRequest(getCollectionSubmitURL(), HttpRequest.POST);
        collection.setUser(CurrentUser);

        collection.setDate(tvTodayDate.getText().toString());
        httpRequest.addParam("data", collection.toJson());
        Log.i("Collection Submit", collection.toJson());
        progressDialog = showProgressBar(this, "Submitting..");
        new HttpCaller() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.getStatus() == HttpResponse.ERROR)
                    showSnackbar(tvCustomer, response.getMesssage());
                else {
                    {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
                progressDialog.dismiss();
            }
        }.execute(httpRequest);
    }

    public boolean validate() {
        boolean isValid = true;
        List<Invoice> invoiceList = collection.getInvoiceList();
        if (invoiceList.size() == 0) {
            showSnackbar(tvCustomer, "No Invoice Item found to submit.");
            return false;
        }
        int i = 0;
        for (i = 0; i < invoiceList.size(); i++)
            if (invoiceList.get(i).getCollectedAmount() > 0)
                break;
        if (i == invoiceList.size()) {
            showSnackbar(tvCustomer, "Update Collection amount in any of the invoice entry");
            return false;
        }


        if (collection.getCustomer() == null) {
            showSnackbar(tvCustomer, "Please select a customer");
        }


        return isValid;
    }

}
