package com.example.officertestapp.ForceEnd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.officertestapp.ForceEnd.Helpers.ForceEndedPaymentDetailsHelper;
import com.example.officertestapp.Home.ConfirmCashPaymentFragment;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.PaymentDetailsHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;


public class ForceEndDetailsFragment extends Fragment {
    String sessionID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_force_end_details, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        if (getArguments() != null) {
            sessionID = getArguments().getString("_id", "-1");
        }

        //Helper ForceEndedPaymentDetailsHelper
        ForceEndedPaymentDetailsHelper forceEndedPaymentDetailsHelper = new ForceEndedPaymentDetailsHelper(view, getContext());
        forceEndedPaymentDetailsHelper.initLayout(sessionID);


        // Handle the Receive Cash Payment button
        Button receiveCashPaymentBtn = view.findViewById(R.id.force_ended_session_receive_cash_payment_btn);
        receiveCashPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bundle values
                Bundle paybundle = new Bundle();
                paybundle.putString("session_id", sessionID);
                paybundle.putString("amount_para", forceEndedPaymentDetailsHelper.getAmountPara());
                paybundle.putString("amount", forceEndedPaymentDetailsHelper.getAmount());

                // Log the values for debugging
                Log.d("Bundle Values", "sessionID : " + forceEndedPaymentDetailsHelper.getSessionId());
                Log.d("Bundle Values", "Amount: " + forceEndedPaymentDetailsHelper.getAmount());
                Log.d("Bundle Values", "Amount Para: " + forceEndedPaymentDetailsHelper.getAmountPara());

                // Navigate to AssignVehicle02Fragment with the Bundle
                MainActivity mainActivity = (MainActivity) requireContext();
                mainActivity.replaceFragment(new ConfirmForceEndedCashPaymentFragment(), paybundle, view);
            }
        });

        return view;
    }
}