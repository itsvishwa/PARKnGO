package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ReleaseASlot03Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot03, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());



        // CAll the paymentDetailsHelper here




        // Find the TextViews
        TextView vehicleNumberTextView = view.findViewById(R.id.vehicle_num_txt_view);
        TextView vehicleTypeTextView = view.findViewById(R.id.vehicle_type_txt_view);
        TextView sessionStartedTimeTextView = view.findViewById(R.id.session_started_time_txt_view);
        TextView sessionEndedTimeTextView = view.findViewById(R.id.session_ended_time_txt_view);
        TextView timeDurationTextView = view.findViewById(R.id.time_duration_txt_view);
        TextView amountTextView = view.findViewById(R.id.amount_txt_view);

        // Retrieve values from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            String PaymentID = args.getString("PaymentID");
            String vehicleNumber = args.getString("VehicleNumber");
            String VehicleType = args.getString("VehicleType");
            String StartTime = args.getString("StartTime");
            String EndTime = args.getString("EndTime");
            String TimeWent = args.getString("TimeWent");
            String Amount = args.getString("Amount");


            // Insert space between letters and numbers in the vehicle number
            String formattedVehicleNumber = vehicleNumber.replaceAll("(\\D)(\\d+)", "$1 $2 ");


            // format the timestamp to date time according to the devices time zone
            // Convert the timestamp string to a long value
            long timestampStart = Long.parseLong(StartTime);
            // Create a Date object from the timestamp
            Date startDate = new Date(timestampStart * 1000);
            // Create a SimpleDateFormat object with your desired format
            SimpleDateFormat sdf = new SimpleDateFormat("hh.mm a", Locale.ENGLISH);
            // Set the timezone to the device's local timezone
            sdf.setTimeZone(TimeZone.getDefault());
            // Format the date object to a string
            String formattedStartTime = sdf.format(startDate);


            long timestampEnd = Long.parseLong(EndTime);
            Date EndDate = new Date(timestampEnd * 1000);
            SimpleDateFormat edf = new SimpleDateFormat("hh.mm a", Locale.ENGLISH);
            edf.setTimeZone(TimeZone.getDefault());
            String formattedEndTime = sdf.format(EndDate);


            // Update TextViews with the retrieved values
            vehicleNumberTextView.setText(formattedVehicleNumber);
            vehicleTypeTextView.setText(VehicleType);
            sessionStartedTimeTextView.setText(formattedStartTime);
            sessionEndedTimeTextView.setText(formattedEndTime);
            timeDurationTextView.setText(TimeWent);
            amountTextView.setText(Amount);

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
                    paybundle.putString("PaymentID", PaymentID);
                    paybundle.putString("Amount", Amount);

                    // Log the values for debugging
                    Log.d("Bundle Values", "Payment ID: " + PaymentID);
                    Log.d("Bundle Values", "Vehicle Number: " + Amount);

                    // Navigate to AssignVehicle02Fragment with the Bundle
                    ReleaseASlot04Fragment releaseASlot04Fragment = new ReleaseASlot04Fragment();

                    ((MainActivity) requireActivity()).replaceFragment(releaseASlot04Fragment, paybundle, getView());
                }
            });

        } else {
            Log.e("AssignVehicle02Fragment", "Arguments (Bundle) is null");
        }

        return view;
    }
}