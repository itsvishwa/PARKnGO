package com.example.parkngo.session;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.AddVehicleHelper;

public class AddVehicle extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addVehicleView = inflater.inflate(R.layout.fragment_add_vehicle, container, false);

        AddVehicleHelper addVehicleHelper = new AddVehicleHelper(addVehicleView, getContext(),requireActivity().getSupportFragmentManager());
        addVehicleHelper.initSpinners();

        // onclick listeners...............................
        addVehicleHelper.initVehicleProvinceSpinnerBtnListener();
        addVehicleHelper.initVehicleTypeSpinnerBtnListener();
        addVehicleHelper.initAddVehicleBtnListener();
        // onclick listeners...............................

        return addVehicleView;
    }
}