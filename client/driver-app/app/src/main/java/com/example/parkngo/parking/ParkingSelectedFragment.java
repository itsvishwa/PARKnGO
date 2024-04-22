package com.example.parkngo.parking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.ParkingSelectedHelper;

public class ParkingSelectedFragment extends Fragment {

    private View parkingSelectedView;
    private View loadingView;
    private String parkingID;
    private String userReviewId;
    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get main activity context
        mainActivity = (MainActivity)requireContext();

        // Inflate the layout for this fragment
        parkingSelectedView =  inflater.inflate(R.layout.fragment_parking_selected, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // Retrieve data from arguments
        if (getArguments() != null) {
            parkingID = getArguments().getString("parkingID");
        }

        // Perform data loading in the background
        ParkingSelectedHelper parkingSelectedHelper = new ParkingSelectedHelper(parkingSelectedView, loadingView, parkingID, getContext());
        parkingSelectedHelper.init();

        return loadingView;
    }
}