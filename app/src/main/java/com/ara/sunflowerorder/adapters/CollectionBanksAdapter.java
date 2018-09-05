package com.ara.sunflowerorder.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.models.Bank;
import com.ara.sunflowerorder.models.User;

import java.util.ArrayList;

public class CollectionBanksAdapter extends ArrayAdapter<Bank>{
    private Activity context;
    ArrayList<Bank> data = null;


    public CollectionBanksAdapter(Activity context, int resource, ArrayList<Bank> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;

    }

    @Override
    public int getPosition(@Nullable Bank item) {
        return super.getPosition(item);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Bank getItem(int position) {
        return super.getItem(position);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.banknames, parent, false);
        }
        Bank item = data.get(position);
        if (item != null) { // Parse the data from each object and set it.
            TextView accname = (TextView) row.findViewById(R.id.banknamesid);
            if (accname != null) {
                accname.setText(item.getAccounts_name());
            }

        }
        return row;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.banknames, parent, false);
        }
        Bank item = data.get(position);

        if (item != null) { // Parse the data from each object and set it.
            TextView accname = (TextView) row.findViewById(R.id.banknamesid);
            if (accname != null) {
                accname.setText(item.getAccounts_name());
            }

        }
        return row;
    }
}
