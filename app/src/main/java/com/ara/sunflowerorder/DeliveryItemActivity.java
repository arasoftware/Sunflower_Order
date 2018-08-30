package com.ara.sunflowerorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ara.sunflowerorder.models.DeliveryItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SEARCH_RESULT;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_DELIVERY_ITEM;
import static com.ara.sunflowerorder.utils.AppConstants.EXTRA_SELECTED_ITEM_INDEX;
import static com.ara.sunflowerorder.utils.AppConstants.showSnackbar;

public class DeliveryItemActivity extends AppCompatActivity {

    @BindView(R.id.tv_delivery_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_delivery_product_code)
    TextView tvProductCode;
    @BindView(R.id.tv_ordered_qty)
    TextView tvOrderedQty;
    @BindView(R.id.edit_accepted_qty)
    EditText editTextAcceptedQty;
    @BindView(R.id.edit_rejected_qty)
    EditText editTextRejectedQty;

    DeliveryItem deliveryItem;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_item);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String json = intent.getStringExtra(EXTRA_SELECTED_DELIVERY_ITEM);
        position = intent.getIntExtra(EXTRA_SELECTED_ITEM_INDEX, -1);
        deliveryItem = DeliveryItem.fromJSON(json);

        tvProductName.setText(deliveryItem.getProductName());
        tvProductCode.setText(deliveryItem.getProductCode());
        tvOrderedQty.setText(deliveryItem.getQuantity() + "");

    }

    @OnClick({R.id.edit_accepted_qty, R.id.edit_rejected_qty})
    public void onEditTextClicked(View view) {
        EditText editText = (EditText) view;
        editText.selectAll();
    }

    private int getQuantity(EditText editText) {
        String rejectQty = editText.getText().toString();
        if (rejectQty.isEmpty())
            return 0;
        return Integer.parseInt(rejectQty);
    }


    @OnClick(R.id.btn_deliver_an_item)
    public void deliveryAnItem() {
        int rejecteQty = getQuantity(editTextRejectedQty);
        int acceptedQty = getQuantity(editTextAcceptedQty);
        if (deliveryItem.getQuantity() != (rejecteQty + acceptedQty)) {
            showSnackbar(tvProductName, "Sum of Accepted & Rejected must match Ordered Quantity");
            return;
        }

        if (acceptedQty == deliveryItem.getAccept()) {
            setResult(RESULT_CANCELED);
            finish();
        } else {

            deliveryItem.setAccept(acceptedQty);
            deliveryItem.setReject(rejecteQty);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED_ITEM_INDEX, position);
            intent.putExtra(EXTRA_SEARCH_RESULT, deliveryItem.toJson());
            setResult(RESULT_OK, intent);
            finish();
        }
    }


}
