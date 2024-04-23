package com.example.parkngo.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.LocationHelper;
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
        }

        AvailableParkingSpaceHelper availableParkingSpaceHelper = new AvailableParkingSpaceHelper(getContext(), availableParkingSpaceView, loadingView, errorView, vehicleType, availableParkingSpaceModelsArr);
        availableParkingSpaceHelper.init();

        return loadingView;
    }
}
