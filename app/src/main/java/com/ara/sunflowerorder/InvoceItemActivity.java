package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ara.sunflowerorder.models.Invoice;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_INVOICE_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_ITEM_INDEX;
import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class InvoceItemActivity extends AppCompatActivity {

    Invoice invoice;
    int position;
    @BindView(R.id.tv_invoice_no)
    TextView tvInvoiceNo;
    @BindView(R.id.tv_invoice_date)
    TextView tvInvoiceDate;

    @BindView(R.id.tv_invoice_amt)
    TextView tvInvoiceAmount;
    @BindView(R.id.tv_invoice_paid_amt)
    TextView tvPaidAmount;
    @BindView(R.id.tv_invoice_balance_amt)
    TextView tvBalanceAmount;
    @BindView(R.id.ed_invoice_coll_amt)
    EditText edCollectionAmount;
    @BindView(R.id.tv_invoice_pending_amt)
    TextView tvPendingAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoce_item);
        ButterKnife.bind(this);
        Intent data = getIntent();
        String json = data.getStringExtra(EXTRA_SELECTED_INVOICE_ITEM);
        position=data.getIntExtra(EXTRA_SELECTED_ITEM_INDEX,-1);
        invoice = Invoice.fromJSON(json);

        tvInvoiceNo.setText(invoice.getNo() + "");
        tvInvoiceDate.setText(invoice.getDate());
        tvInvoiceAmount.setText(formatPrice(invoice.getInvoiceAmount()));
        tvPaidAmount.setText(formatPrice(invoice.getPaidAmount()));
        tvBalanceAmount.setText(formatPrice(invoice.getBalanceAmount()));
        tvPendingAmount.setText(formatPrice(invoice.getBalanceAmount()));
        edCollectionAmount.setText(formatPrice(invoice.getCollectedAmount()));
    }

    @OnClick(R.id.btn_invoice_update)
    public void updateItem(View view) {
        String collectedAmount = edCollectionAmount.getText().toString();
        double dblCollAmt = Double.parseDouble(collectedAmount);
        if (dblCollAmt < 0 || dblCollAmt > invoice.getBalanceAmount()) {
            showSnackbar(tvPaidAmount, "Collection Amount must be lesser than Balance Amount (Inv-paid)");
            return;
        }
        invoice.setCollectedAmount(dblCollAmt);
        Intent data = new Intent();
        data.putExtra(EXTRA_SEARCH_RESULT, invoice.toJson());
        data.putExtra(EXTRA_SELECTED_ITEM_INDEX, position);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
