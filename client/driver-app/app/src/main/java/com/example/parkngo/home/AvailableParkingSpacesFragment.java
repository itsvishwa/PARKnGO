package com.example.parkngo.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.home.helpers.AvailableParkingSpaceHelper;
import com.example.parkngo.home.helpers.AvailableParkingSpaceModel;

import java.util.ArrayList;

public class AvailableParkingSpacesFragment extends Fragment {

    View availableParkingSpaceView;
    View loadingView;
    View errorView;
    String vehicleType;
    Double latitude;
    Double longitude;
    MainActivity mainActivity;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr  = new ArrayList<>();
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        availableParkingSpaceView  = inflater.inflate(R.layout.fragment_available_parking_spaces, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        errorView = inflater.inflate(R.layout.fragment_error, container, false);


        // store passed data
        if (getArguments() != null) {
            vehicleType = getArguments().getString("vehicleType", "none");
            latitude = getArguments().getDouble("lat", 6.919875);
            longitude = getArguments().getDouble("long", 79.854209);

        }

        AvailableParkingSpaceHelper availableParkingSpaceHelper = new AvailableParkingSpaceHelper(getContext(), availableParkingSpaceView, loadingView, errorView, vehicleType, availableParkingSpaceModelsArr, latitude, longitude);
        availableParkingSpaceHelper.init();

        return loadingView;
    }
}
