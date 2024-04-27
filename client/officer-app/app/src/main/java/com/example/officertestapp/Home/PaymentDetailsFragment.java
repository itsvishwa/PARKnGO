package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.PaymentDetailsHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

public class PaymentDetailsFragment extends Fragment {

    String paymentID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_details, container, false);


        if (getArguments() != null) {
            paymentID = getArguments().getString("_id", "-1");
        }


        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());


        // CAll the paymentDetailsHelper here
        PaymentDetailsHelper paymentDetailsHelper = new PaymentDetailsHelper(view, getContext());
        paymentDetailsHelper.initLayout(paymentID);


        // Handle the Receive Cash Payment button
        Button receiveCashPaymentBtn = view.findViewById(R.id.release_slot_03_receive_cash_payment_btn);
        receiveCashPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Bundle values
                Bundle paybundle = new Bundle();
                paybundle.putString("PaymentID", paymentID);
                paybundle.putString("Amount", paymentDetailsHelper.getAmount());

                // Log the values for debugging
                Log.d("Bundle Values", "Payment ID: " + paymentID);
                Log.d("Bundle Values", "Vehicle Number: " + paymentDetailsHelper.getAmount());

                // Navigate to AssignVehicle02Fragment with the Bundle
                ConfirmCashPaymentFragment releaseASlot04Fragment = new ConfirmCashPaymentFragment();

                ((MainActivity) requireActivity()).replaceFragment(releaseASlot04Fragment, paybundle, getView());
            }
        });

        return view;
    }
}