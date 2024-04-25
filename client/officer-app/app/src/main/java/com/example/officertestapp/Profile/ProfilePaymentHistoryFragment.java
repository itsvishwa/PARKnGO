package com.example.officertestapp.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.MainActivity;
import com.example.officertestapp.Profile.Helpers.PPHFetchData;

import com.example.officertestapp.Profile.Helpers.PaymentProfileModel;
import com.example.officertestapp.R;

import java.util.ArrayList;


public class ProfilePaymentHistoryFragment extends Fragment {
    ArrayList<PaymentProfileModel> paymentHistoryModels = new ArrayList<>();
    View loadingView;
    View paymentHistoryView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        paymentHistoryView  = inflater.inflate(R.layout.fragment_profile_payment_history, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // fetching data
        new PPHFetchData(paymentHistoryView, loadingView, getContext(), (MainActivity)requireContext());

        return loadingView;
    }

}