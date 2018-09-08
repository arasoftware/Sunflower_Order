package com.ara.sunflowerorder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ara.sunflowerorder.R;
import com.ara.sunflowerorder.models.OrderItem;

import static com.ara.sunflowerorder.utils.AppConstants.formatPrice;
import static com.ara.sunflowerorder.utils.AppConstants.formatQuantity;
import static com.ara.sunflowerorder.utils.AppConstants.parseDouble;
import static com.ara.sunflowerorder.utils.AppConstants.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ORDER_ITEM = "OrderItem";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OrderItem orderItem;

    private OnFragmentInteractionListener mListener;

    AppCompatButton submitButton;
    AppCompatButton cancelButton;
    TextInputEditText orderQuantity;
    TextInputEditText orderPrice;
    TextInputLayout orderQuanityLayout;
    TextInputLayout orderPriceLayout;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(OrderItem orderItem) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORDER_ITEM, orderItem.toJson());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderItem = OrderItem.fromJson(getArguments().getString(ARG_ORDER_ITEM));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cancelButton = (AppCompatButton) view.findViewById(R.id.btn_frag_cancel);
        submitButton = (AppCompatButton) view.findViewById(R.id.btn_frag_submit);
        orderQuantity = (TextInputEditText) view.findViewById(R.id.frag_order_qty);
        orderPrice = (TextInputEditText) view.findViewById(R.id.frag_order_price);
        orderPriceLayout = (TextInputLayout) view.findViewById(R.id.frag_order_price_layout);
        orderQuanityLayout = (TextInputLayout) view.findViewById(R.id.frag_order_qty_layout);

        orderPrice.setText(formatPrice(orderItem.getPrice()));
        orderQuantity.setText(formatQuantity(orderItem.getQuantity()));

        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_frag_cancel:
                this.dismiss();
                break;
            case R.id.btn_frag_submit:
                int quantity = parseInt(orderQuantity.getText().toString());
                if (quantity == 0) {
                    orderQuanityLayout.setError("cannot be zero or empty");
                    return;
                }
                double price = parseDouble(orderPrice.getText().toString());
                if (price == 0) {
                    orderPriceLayout.setError("cannot be zero or empty");
                    return;
                }
                int total = (int) (price * quantity);
                int temp = 0;
                temp = total+temp;
                Log.e("TAG", "total quantity  :  " + temp);
                Toast.makeText(getContext(), "value : " + temp, Toast.LENGTH_SHORT).show();
                orderItem.setTotalAmount(temp);
                orderItem.setPrice(price);
                orderItem.setQuantity(quantity);
                mListener.onFragmentInteraction(orderItem);
                dismiss();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(OrderItem orderItem);
    }
}
