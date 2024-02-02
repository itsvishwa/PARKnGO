package com.example.parkngo.session;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.PaymentOnGoingModel;
import com.example.parkngo.session.helpers.PaymentOngoingHelper;
import com.example.parkngo.session.helpers.SessionOnGoingModel;

public class SessionPaymentFragment extends Fragment {
    PaymentOnGoingModel paymentOnGoingModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View paymentOnGoingView =  inflater.inflate(R.layout.fragment_session_payment, container, false);
        // getting open-parking-data from the session main fragment
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("paymentOnGoingModelObj")) {
            paymentOnGoingModel = (PaymentOnGoingModel) bundle.getSerializable("paymentOnGoingModelObj");
        }

        PaymentOngoingHelper paymentOngoingHelper = new PaymentOngoingHelper(paymentOnGoingView, getContext(), paymentOnGoingModel);
        paymentOngoingHelper.initLayout();

        return paymentOnGoingView;
    }
}