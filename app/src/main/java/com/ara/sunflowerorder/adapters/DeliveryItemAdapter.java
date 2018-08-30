package com.ara.sunflowerorder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.DeliveryItem;

import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.formatQuantity;

public class DeliveryItemAdapter extends RecyclerView.Adapter<DeliveryItemAdapter.ViewHolder> {

    private final List<DeliveryItem> mValues;
    private final ListViewClickListener onItemClickListner;


    public DeliveryItemAdapter(List<DeliveryItem> items, ListViewClickListener onItemClickListener) {
        mValues = items;
        this.onItemClickListner = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final DeliveryItem deliveryItem = mValues.get(position);
        holder.mItem = deliveryItem;
        holder.mProductNameView.setText(deliveryItem.getProductName());
        holder.mProductCode.setText(deliveryItem.getProductCode());
        holder.mOrderedQty.setText(formatQuantity(deliveryItem.getQuantity()));
        holder.mAcceptedQty.setText(formatQuantity(deliveryItem.getAccept()));
        holder.mRejectedQty.setText(formatQuantity(deliveryItem.getReject()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClick(mValues.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mProductNameView;
        public final TextView mProductCode;
        public final TextView mOrderedQty;
        public final TextView mAcceptedQty;
        public final TextView mRejectedQty;
        public DeliveryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductNameView = view.findViewById(R.id.dli_tv_product_name);
            mProductCode = view.findViewById(R.id.dli_tv_product_code);
            mOrderedQty = view.findViewById(R.id.dli_tv_ordered_qty);
            mAcceptedQty = view.findViewById(R.id.dli_tv_accepted_qty);
            mRejectedQty = view.findViewById(R.id.dli_tv_rejected_qty);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mProductCode.getText() + "'";
        }
    }
}
