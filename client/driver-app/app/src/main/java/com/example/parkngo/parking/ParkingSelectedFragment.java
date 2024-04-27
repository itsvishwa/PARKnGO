package com.example.parkngo.parking;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.ParkingSelectedHelper;

public class ParkingSelectedFragment extends Fragment {

    private View parkingSelectedView;
    private View loadingView;
    private String parkingID;
    private static final int PERMISSION_REQUEST_CODE = 1003;
    ParkingSelectedHelper parkingSelectedHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parkingSelectedView =  inflater.inflate(R.layout.fragment_parking_selected, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // Retrieve data from arguments
        if (getArguments() != null) {
            parkingID = getArguments().getString("parkingID");
        }

        // Perform data loading in the background
        parkingSelectedHelper = new ParkingSelectedHelper(parkingSelectedView, loadingView, parkingID, getContext());
        parkingSelectedHelper.init();
        navigateBtnHandler();

        return loadingView;
    }


    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission denied, show message or handle accordingly
                Toast.makeText(getContext(), "Access to your location is required to proceed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void navigateBtnHandler(){
        Button navigateBtn  = parkingSelectedView.findViewById(R.id.parking_selected_fragment_navigate_btn);
        navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingSelectedHelper.getLocationAndContinue();
            }
        });
    }
}