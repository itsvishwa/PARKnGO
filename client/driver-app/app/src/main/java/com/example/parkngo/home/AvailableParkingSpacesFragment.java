package com.example.parkngo.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.home.helpers.AvailableParkingSpaceHelper;
import com.example.parkngo.home.helpers.AvailableParkingSpaceModel;

import java.util.ArrayList;
import java.util.List;

public class AvailableParkingSpacesFragment extends Fragment {

    View availableParkingSpaceView;
    View loadingView;
    View errorView;
    String vehicleType;
    MainActivity mainActivity;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr  = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        availableParkingSpaceView  = inflater.inflate(R.layout.fragment_available_parking_spaces, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        errorView = inflater.inflate(R.layout.fragment_error, container, false);

        // get the selected vehicle type from prev fragment
        if (getArguments() != null) {
            vehicleType = getArguments().getString("vehicleType", "none");
        }

        AvailableParkingSpaceHelper availableParkingSpaceHelper = new AvailableParkingSpaceHelper(getContext(), availableParkingSpaceView, loadingView, errorView, vehicleType, availableParkingSpaceModelsArr);
        availableParkingSpaceHelper.initLayout();
        availableParkingSpaceHelper.initAllBtnListeners();

        return loadingView;
    }
}
