package com.ara.sunflowerorder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.view.SalesOrderReport;

import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;
import static com.ara.sunflowerorder.utils.AppConstants.formatQuantity;

public class SalesOrderReportAdapter extends RecyclerView.Adapter<SalesOrderReportAdapter.ViewHolder> {

    private final List<SalesOrderReport> mValues;
    private final ListViewClickListener onItemClickListner;


    public SalesOrderReportAdapter(List<SalesOrderReport> items, ListViewClickListener onItemClickListener) {
        mValues = items;
        this.onItemClickListner = onItemClickListener;
    }

    @Override
    public SalesOrderReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_order_report_item, parent, false);
        return new SalesOrderReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SalesOrderReportAdapter.ViewHolder holder, final int position) {

        final SalesOrderReport salesOrderReport = mValues.get(position);
        holder.mItem = salesOrderReport;
        holder.mProductNameView.setText(salesOrderReport.getProductName());
        holder.mCustomerName.setText(salesOrderReport.getCustomerName());
        holder.mOrderedQty.setText(formatQuantity(salesOrderReport.getQuantity()));
        holder.mdate.setText(salesOrderReport.getDate());
        holder.mUOM.setText(salesOrderReport.getUom());
        holder.mPrice.setText(formatPrice(salesOrderReport.getPrice()));
        holder.mTotal.setText(formatPrice(salesOrderReport.getTotal()));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner != null)
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
        public final TextView mCustomerName;
        public final TextView mdate;
        public final TextView mUOM;
        ;
        public final TextView mOrderedQty;
        public final TextView mPrice;
        public final TextView mTotal;

        public SalesOrderReport mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductNameView = view.findViewById(R.id.so_rpt_product);
            mCustomerName = view.findViewById(R.id.so_rpt_customer);
            mOrderedQty = view.findViewById(R.id.so_rpt_qty);
            mdate = view.findViewById(R.id.so_rpt_date);
            mUOM = view.findViewById(R.id.so_rpt_uom);
            mPrice = view.findViewById(R.id.so_rpt_price);
            mTotal = view.findViewById(R.id.so_rpt_total);
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}