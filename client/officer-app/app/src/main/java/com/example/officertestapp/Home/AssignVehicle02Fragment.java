package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Home.Helpers.AddVehicleDetails;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.R;


public class AssignVehicle02Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assign_vehicle02, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        TextView vehicleNumberTextView = view.findViewById(R.id.frag_assign_vehicle_01_vehicle_number_txt_view);
        TextView vehicleTypeTextView = view.findViewById(R.id.frag_assign_vehicle_01_vehicle_type_txt_view);


        // Retrieve values from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            String vehicleNumberWithoutProvince = args.getString("vehicle_number_without_province");
            String selectedVehicleTypeCaps = args.getString("vehicle_type_caps");

            // Update TextViews with the retrieved values
            vehicleNumberTextView.setText(vehicleNumberWithoutProvince);
            vehicleTypeTextView.setText(selectedVehicleTypeCaps);
        } else {
            Log.e("AssignVehicle02Fragment", "Arguments (Bundle) is null");
        }

        // Handle back button click
        Button backButton = view.findViewById(R.id.assign_vehicle_02_back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                requireActivity().onBackPressed();
            }
        });


        // Handle reserve slot confirmation Yes button click
        Button reserveSlotConfirmationYesBtn = view.findViewById(R.id.assign_vehicle_02_yes_btn);
        reserveSlotConfirmationYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AssignVehicle02Fragment", "Yes button clicked");

                // Retrieve values from the Bundle
                Bundle args = getArguments();
                if (args != null) {
                    String token = args.getString("token");
                    String vehicleNumber = args.getString("vehicle_number");
                    String selectedVehicleType = args.getString("vehicle_type");
                    String startTimeStamp = args.getString("start_time");
                    String parkingId = args.getString("parking_id");
                    String driverId = args.getString("driver_id");

                    // Invoke the AddVehicleDetails helper to add vehicle details
                    AddVehicleDetails addVehicleDetailsHelper = new AddVehicleDetails(view, requireContext(), getFragmentManager());
                    addVehicleDetailsHelper.addDetails(token, vehicleNumber, selectedVehicleType, startTimeStamp, parkingId, driverId);
                } else {
                    Log.e("AssignVehicle02Fragment", "Arguments (Bundle) is null");
                }
            }
        });

        return view;
    }
}