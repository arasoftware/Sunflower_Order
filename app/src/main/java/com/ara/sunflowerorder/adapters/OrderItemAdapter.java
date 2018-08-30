package com.ara.sunflowerorder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.models.OrderItem;

import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;
import static com.ara.sunflowerorder.utils.AppConstants.formatQuantity;


public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private final List<OrderItem> mValues;


    public OrderItemAdapter(List<OrderItem> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        OrderItem orderItem = mValues.get(position);
        holder.mItem = orderItem;
        holder.mProductNameView.setText(orderItem.getProduct().getName());
        holder.mBrandNameView.setText(orderItem.getProduct().getBrand().getName());
        holder.mPriceView.setText(formatPrice(orderItem.getPrice()));
        holder.mQuantityView.setText(formatQuantity(orderItem.getQuantity()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        public final TextView mBrandNameView;
        public final TextView mPriceView;
        public final TextView mQuantityView;
        public OrderItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductNameView = view.findViewById(R.id.fg_tv_product_name);
            mBrandNameView = view.findViewById(R.id.fg_tv_brand_name);
            mPriceView = view.findViewById(R.id.fg_tv_price);
            mQuantityView = view.findViewById(R.id.fg_tv_qty);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBrandNameView.getText() + "'";
        }
    }
}
