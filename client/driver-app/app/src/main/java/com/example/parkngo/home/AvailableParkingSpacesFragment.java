package com.example.parkngo.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.home.helpers.APSFetchData;

public class AvailableParkingSpacesFragment extends Fragment {

    View view;
    View loadingView;
    View noAvailableParkingView;
    String vehicleType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view  = inflater.inflate(R.layout.fragment_available_parking_spaces, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        noAvailableParkingView = inflater.inflate(R.layout.fragment_no_available_parking, container, false);

        // get the selected vehicle type from prev fragment
        if (getArguments() != null) {
            vehicleType = getArguments().getString("vehicleType", "none");
        }

        // fetch data via api and set to the views
        new APSFetchData(view, loadingView, noAvailableParkingView, getContext(), vehicleType);

        return loadingView; // loading view until data get fetched
    }
}
