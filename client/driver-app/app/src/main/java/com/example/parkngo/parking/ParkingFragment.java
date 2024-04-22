package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.ParkingHelper;

public class ParkingFragment extends Fragment {
    private View loadingView;
    private View parkingView;
    private View errorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the loading fragment initially
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        parkingView = inflater.inflate(R.layout.fragment_parking, container, false);
        errorView = inflater.inflate(R.layout.fragment_error, container, false);

        // Data fetching and processing
        ParkingHelper parkingHelper = new ParkingHelper(loadingView, parkingView, errorView, getContext());
        parkingHelper.init();

        return loadingView;
    }
}
