package com.example.officertestapp.ForceEnd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.ForceEnd.Helpers.ForceEndedPaymentDetailsHelper;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.PaymentDetailsHelper;
import com.example.officertestapp.R;


public class ForceEndDetailsFragment extends Fragment {
    String sessionID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_details, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        if (getArguments() != null) {
            sessionID = getArguments().getString("_id", "-1");
        }

        //Helper ForceEndedPaymentDetailsHelper
        ForceEndedPaymentDetailsHelper paymentDetailsHelper = new ForceEndedPaymentDetailsHelper(view, getContext());
        paymentDetailsHelper.initLayout(sessionID);

        return view;
    }
}