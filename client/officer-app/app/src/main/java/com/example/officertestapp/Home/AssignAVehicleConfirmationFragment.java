package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.Home.Helpers.AssignAVehicleConfirmationHelper;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.ReleaseASlotHelper;
import com.example.officertestapp.R;


public class AssignAVehicleConfirmationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View assignAVehicleConfirmationView = inflater.inflate(R.layout.fragment_assigna_a_vehicle_confirmation, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(assignAVehicleConfirmationView, requireContext());


        // Retrieve values from the Bundle in AssignAVehicleAddDetailsHelper
        Bundle args = getArguments();
        if (args != null) {
            String vehicleNumber = args.getString("vehicle_number");
            String vehicleNumberProcessed = args.getString("vehicle_number_processed");
            String vehicleType = args.getString("vehicle_type");
            String startTimeStamp = args.getString("start_time");
            String driverId = args.getString("driver_id");

            // Helper Class
            AssignAVehicleConfirmationHelper assignAVehicleConfirmationHelper = new AssignAVehicleConfirmationHelper(assignAVehicleConfirmationView, getContext(),requireActivity().getSupportFragmentManager(), vehicleNumber, vehicleNumberProcessed, vehicleType, startTimeStamp, driverId);

            // Text Views
            assignAVehicleConfirmationHelper.initTextViews();

            // Handle Yes button click
            assignAVehicleConfirmationHelper.initYesBtnListener();

            // Handle Back button click
            //assignAVehicleConfirmationHelper.initBackBtnListener();

        } else {
            Log.e("AssignAVehicleConfirmationFragment", "AssignAVehicleAddDetailsHelper Arguments (Bundle) is null");
        }




        return assignAVehicleConfirmationView;
    }
}