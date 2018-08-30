package com.ara.sunflowerorder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.view.CollectionReport;

import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;

public class CollectionReportAdapter extends RecyclerView.Adapter<CollectionReportAdapter.ViewHolder> {

    private final List<CollectionReport> mValues;
    private final ListViewClickListener onItemClickListner;


    public CollectionReportAdapter(List<CollectionReport> items, ListViewClickListener onItemClickListener) {
        mValues = items;
        this.onItemClickListner = onItemClickListener;
    }

    @Override
    public CollectionReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_report_item, parent, false);
        return new CollectionReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CollectionReportAdapter.ViewHolder holder, final int position) {

        final CollectionReport collectionReport = mValues.get(position);
        holder.mItem = collectionReport;
        holder.mCollectionNo.setText(collectionReport.getCollectionNo());
        holder.mCustomerName.setText(collectionReport.getCustomerName());

        holder.mdate.setText(collectionReport.getDate());


        holder.mCollectionAmt.setText(formatPrice(collectionReport.getCollectionAmt()));
        holder.mInvoiceNo.setText(collectionReport.getInvoiceNo());


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
        public final TextView mCollectionNo;
        public final TextView mCustomerName;
        public final TextView mdate;

        public final TextView mCollectionAmt;
        public final TextView mInvoiceNo;

        public CollectionReport mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCollectionNo = view.findViewById(R.id.coll_rpt_no);
            mCustomerName = view.findViewById(R.id.coll_rpt_customer);
            mdate = view.findViewById(R.id.coll_rpt_date);

            mInvoiceNo = view.findViewById(R.id.coll_rpt_inv_no);
            mCollectionAmt = view.findViewById(R.id.coll_rpt_coll_amt);

        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}