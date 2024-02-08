package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Home.Helpers.AddVehicleDetails;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.R;


public class ReleaseASlot02Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot02, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        TextView vehicleNumberTextView = view.findViewById(R.id.frag_release_slot_02_vehicle_number_txt_view);
        TextView vehicleTypeTextView = view.findViewById(R.id.frag_release_slot_02_vehicle_type_txt_view);

        // Retrieve values from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            String vehicleNumber = args.getString("vehicleNumberWithoutProvince");
            String vehicleType = args.getString("vehicleTypeCaps");

            // Set the retrieved values to TextViews
            vehicleNumberTextView.setText(vehicleNumber);
            vehicleTypeTextView.setText(vehicleType);
        } else {
            Log.e("ReleaseASlot02Fragment", "Arguments (Bundle) is null");
        }

        // Handle back button click
        Button backButton = view.findViewById(R.id.release_slot_02_back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                requireActivity().onBackPressed();
            }
        });

        return view;
    }
}