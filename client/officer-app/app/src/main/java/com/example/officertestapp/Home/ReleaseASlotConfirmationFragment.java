package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Helpers.VehicleNumberHelper;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.ReleaseASlotConfirmationHelper;
import com.example.officertestapp.R;


public class ReleaseASlotConfirmationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_release_a_slot_confirmation, container, false);

        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        TextView vehicleNumberTextView = view.findViewById(R.id.frag_release_slot_confirm_vehicle_number_txt_view);
        TextView vehicleTypeTextView = view.findViewById(R.id.frag_release_slot_confirm_vehicle_type_txt_view);

        // Retrieve values from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            String vehicleNumber = args.getString("vehicleNumber");
            String vehicleType = args.getString("vehicleType");

            vehicleNumberTextView.setText(VehicleNumberHelper.splitVehicleNumber(vehicleNumber));

            vehicleTypeTextView.setText(VehicleNumberHelper.formatVehicleType(vehicleType));
        } else {
            Log.e("ReleaseASlot02Fragment", "Arguments (Bundle) is null");
        }

        // Handle back button click
        Button backButton = view.findViewById(R.id.release_slot_confirmation_back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                requireActivity().onBackPressed();
            }
        });

        // Handle release a slot confirmation Yes button click
        Button reserveSlotConfirmationYesBtn = view.findViewById(R.id.release_slot_confirmation_yes_btn);
        reserveSlotConfirmationYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReleaseASlotConfirmationFragment", "Yes button clicked");

                // Retrieve values from the Bundle
                Bundle args = getArguments();
                if (args != null) {
                    String sessionId = args.getString("sessionId");
                    String timestamp = args.getString("endTimeStamp");

                    // Invoke the ReleaseASlotHelper helper to release the slot
                    ReleaseASlotConfirmationHelper releaseASlotConfirmationHelper = new ReleaseASlotConfirmationHelper(view, requireContext(), getFragmentManager());
                    releaseASlotConfirmationHelper.releaseSlot(sessionId, timestamp);

                } else {
                    Log.e("ReleaseASlotConfirmationFragment", "Arguments (Bundle) is null");
                }

            }
        });

        return view;
    }

}