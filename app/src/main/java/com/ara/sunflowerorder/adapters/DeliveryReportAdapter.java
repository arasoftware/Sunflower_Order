package com.ara.sunflowerorder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.view.DeliveryReport;

import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.formatQuantity;

public class DeliveryReportAdapter extends RecyclerView.Adapter<DeliveryReportAdapter.ViewHolder> {

    private final List<DeliveryReport> mValues;
    private final ListViewClickListener onItemClickListner;


    public DeliveryReportAdapter(List<DeliveryReport> items, ListViewClickListener onItemClickListener) {
        mValues = items;
        this.onItemClickListner = onItemClickListener;
    }

    @Override
    public DeliveryReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_report_item, parent, false);
        return new DeliveryReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeliveryReportAdapter.ViewHolder holder, final int position) {

        final DeliveryReport deliveryReport = mValues.get(position);
        holder.mItem = deliveryReport;
        holder.mProductNameView.setText(deliveryReport.getProduct_name());
        holder.mCustomerName.setText(deliveryReport.getCustomerName());
        holder.mOrderedQty.setText(formatQuantity(deliveryReport.getQuantity()));
        holder.mdate.setText(deliveryReport.getEntryDate());
        holder.mRejected.setText(formatQuantity(deliveryReport.getReject()));
        holder.mAccepted.setText(formatQuantity(deliveryReport.getAccept()));


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mProductNameView;
        public final TextView mCustomerName;
        public final TextView mdate;
        public final TextView mOrderedQty;
        public final TextView mAccepted;
        public final TextView mRejected;

        public DeliveryReport mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductNameView = view.findViewById(R.id.del_rpt_product);
            mCustomerName = view.findViewById(R.id.del_rpt_customer);
            mOrderedQty = view.findViewById(R.id.del_rpt_qty);
            mdate = view.findViewById(R.id.del_rpt_date);
            mAccepted = view.findViewById(R.id.del_rpt_accept_qty);
            mRejected = view.findViewById(R.id.del_rpt_reject_qty);
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}