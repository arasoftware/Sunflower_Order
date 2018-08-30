package com.ara.sunflowerorder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.listeners.ListViewClickListener;
import com.ara.sunflowerorder.models.Invoice;

import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private final List<Invoice> mValues;
    private final ListViewClickListener onItemClickListner;


    public InvoiceAdapter(List<Invoice> items, ListViewClickListener onItemClickListener) {
        mValues = items;
        this.onItemClickListner = onItemClickListener;
    }

    @Override
    public InvoiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_list_item, parent, false);
        return new InvoiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InvoiceAdapter.ViewHolder holder, final int position) {

        final Invoice invoice = mValues.get(position);
        holder.mItem = invoice;
        holder.invoiceNo.setText(invoice.getNo() + "");
        holder.date.setText(invoice.getDate());
        holder.invoiceAmt.setText(formatPrice(invoice.getInvoiceAmount()));
        holder.pendingAmt.setText(formatPrice(invoice.getPendingAmount()));
        holder.balanceAmt.setText(formatPrice(invoice.getBalanceAmount()));
        holder.collectionAmt.setText(formatPrice(invoice.getCollectedAmount()));
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
        public final TextView invoiceNo;
        public final TextView date;
        public final TextView invoiceAmt;
        public final TextView balanceAmt;
        public final TextView collectionAmt;
        public final TextView pendingAmt;

        public Invoice mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            invoiceNo = view.findViewById(R.id.coll_item_inv_no);
            date = view.findViewById(R.id.coll_item_date);
            invoiceAmt = view.findViewById(R.id.coll_item_inv_amt);
            balanceAmt = view.findViewById(R.id.coll_item_bal_amt);
            collectionAmt = view.findViewById(R.id.coll_item_coll_amt);
            pendingAmt = view.findViewById(R.id.coll_item_pending_amt);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
