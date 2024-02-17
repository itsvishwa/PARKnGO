package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.PaymentFetchData;
import com.example.officertestapp.R;
import com.example.officertestapp.Status.Helpers.StatusFetchData;

public class ReleaseASlot03Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot03, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        // fetching data
        PaymentFetchData paymentFetchData = new PaymentFetchData(getContext(),view);

        return view;
    }
}