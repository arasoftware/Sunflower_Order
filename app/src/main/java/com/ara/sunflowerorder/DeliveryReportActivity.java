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

import com.ara.sunflowerorder.adapters.DeliveryReportAdapter;
import com.ara.sunflowerorder.models.view.DeliveryReport;
import com.ara.sunflowerorder.utils.http.HttpCaller;
import com.ara.sunflowerorder.utils.http.HttpRequest;
import com.ara.sunflowerorder.utils.http.HttpResponse;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.CurrentUser;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_DATE_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.FROM_DATE_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.FROM_DATE_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.TO_DATE_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.TO_DATE_REQUEST;
import static com.ara.sunflowerorder.utils.AppConstants.USER_ID_PARAM;
import static com.ara.sunflowerorder.utils.AppConstants.getDeliveryReportURL;
import static com.ara.sunflowerorder.utils.AppConstants.showProgressBar;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class DeliveryReportActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.del_rpt_list_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_del_from_date)
    TextView fromDate;
    @BindView(R.id.tv_del_to_date)
    TextView toDate;

    ProgressDialog progressDialog;

    Calendar from;
    Calendar to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_report);
        ButterKnife.bind(this);

        // recyclerView = (RecyclerView) findViewById(R.id.del_rpt_list_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //If list is empty then recycler view will throw exception. So need to hide if empty.
        recyclerView.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_del_to_date, R.id.tv_del_from_date})
    public void chooseDate(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        int id = view.getId();
        switch (id) {
            case R.id.tv_del_from_date:
                startActivityForResult(intent, FROM_DATE_REQUEST);
                break;
            case R.id.tv_del_to_date:
                startActivityForResult(intent, TO_DATE_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        String date = data.getStringExtra(EXTRA_DATE_RESULT);
        switch (requestCode) {
            case FROM_DATE_REQUEST:

                fromDate.setText(date);
                break;
            case TO_DATE_REQUEST:

                toDate.setText(date);
                HttpRequest httpRequest = new HttpRequest(getDeliveryReportURL(), HttpRequest.POST);
                httpRequest.addParam(USER_ID_PARAM, CurrentUser.getId() + "");
                String strFromDate = fromDate.getText().toString();
                String strToDate = toDate.getText().toString();

                httpRequest.addParam(FROM_DATE_PARAM, strFromDate);
                httpRequest.addParam(TO_DATE_PARAM, strToDate);
                progressDialog = showProgressBar(this, "Delivery Report..");
                new HttpCaller() {
                    @Override
                    public void onResponse(HttpResponse response) {
                        progressDialog.dismiss();
                        if (response.getStatus() == HttpResponse.ERROR) {
                            Log.i("DeliveryReport", "Fetching error");
                        } else {
                            String strResponse = response.getMesssage();
                            if (strResponse.isEmpty() || strResponse.equalsIgnoreCase("[]")) {
                                recyclerView.setVisibility(View.GONE);
                                showSnackbar(recyclerView, "No data found.");
                            } else {
                                List<DeliveryReport> deliveryReportList = DeliveryReport.fromJsonArray(response.getMesssage());
                                mAdapter = new DeliveryReportAdapter(deliveryReportList, null);
                                recyclerView.setAdapter(mAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }.execute(httpRequest);
        }
    }

}
