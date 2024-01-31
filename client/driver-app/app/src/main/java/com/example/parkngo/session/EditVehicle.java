package com.example.parkngo.session;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.session.helpers.EditVehicleHelper;

public class EditVehicle extends Fragment {

    int selected;
    String vehicleName;
    String vehicleNumber;
    String vehicleType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editVehicleView = inflater.inflate(R.layout.fragment_edit_vehicle, container, false);

        // Retrieve data from arguments
        if (getArguments() != null) {
            selected = getArguments().getInt("selectedVehicle", -1);
            vehicleName = getArguments().getString("vehicleName", "test");
            vehicleNumber = getArguments().getString("vehicleNumber", "test");
            vehicleType = getArguments().getString("vehicleType", "test");
            // TODO :: has to fix default values
        }

        EditVehicleHelper editVehicleHelper = new EditVehicleHelper(selected, vehicleName, vehicleNumber, vehicleType, editVehicleView, getContext());
        editVehicleHelper.initLayout();
        return editVehicleView;
    }
}