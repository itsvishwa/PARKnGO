package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.PaymentDetailsHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ReleaseASlot03Fragment extends Fragment {

    String paymentID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot03, container, false);


        if (getArguments() != null) {
            paymentID = getArguments().getString("_id", "-1");
        }


        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());



        // CAll the paymentDetailsHelper here
        PaymentDetailsHelper paymentDetailsHelper = new PaymentDetailsHelper(view, getContext());
        paymentDetailsHelper.initLayout(paymentID);


        // Find the TextViews
//        TextView vehicleNumberTextView = view.findViewById(R.id.vehicle_num_txt_view);
//        TextView vehicleTypeTextView = view.findViewById(R.id.vehicle_type_txt_view);
//        TextView sessionStartedTimeTextView = view.findViewById(R.id.session_started_time_txt_view);
//        TextView sessionEndedTimeTextView = view.findViewById(R.id.session_ended_time_txt_view);
//        TextView timeDurationTextView = view.findViewById(R.id.time_duration_txt_view);
//        TextView amountTextView = view.findViewById(R.id.amount_txt_view);

        // Retrieve values from the Bundle

//            String vehicleNumber = args.getString("VehicleNumber");
//            String VehicleType = args.getString("VehicleType");
//            String StartTime = args.getString("StartTime");
//            String EndTime = args.getString("EndTime");
//            String TimeWent = args.getString("TimeWent");
//            String Amount = args.getString("Amount");



//
//
//            // Update TextViews with the retrieved values


            // Handle the Receive Cash Payment button
            Button receiveCashPaymentBtn = view.findViewById(R.id.release_slot_03_receive_cash_payment_btn);
            receiveCashPaymentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // replace the fragment to 04
                    // amount text view should update
                    // contain payment_id

                    // Bundle values
                    Bundle paybundle = new Bundle();
                    paybundle.putString("PaymentID", paymentID);
                    paybundle.putString("Amount", paymentDetailsHelper.getAmount());

                    // Log the values for debugging
                    Log.d("Bundle Values", "Payment ID: " + paymentID);
                    Log.d("Bundle Values", "Vehicle Number: " + paymentDetailsHelper.getAmount());

                    // Navigate to AssignVehicle02Fragment with the Bundle
                    ReleaseASlot04Fragment releaseASlot04Fragment = new ReleaseASlot04Fragment();

                    ((MainActivity) requireActivity()).replaceFragment(releaseASlot04Fragment, paybundle, getView());
                }
            });

        return view;
    }
}