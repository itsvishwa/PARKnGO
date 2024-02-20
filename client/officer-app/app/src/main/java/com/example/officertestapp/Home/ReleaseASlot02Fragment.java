package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.Home.Helpers.ReleaseASlotHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;


public class ReleaseASlot02Fragment extends Fragment {
    private Bundle endSessionDataBundle;

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

        // Handle release a slot confirmation Yes button click
        Button reserveSlotConfirmationYesBtn = view.findViewById(R.id.release_slot_02_yes_btn);
        reserveSlotConfirmationYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReleaseASlot02Fragment", "Yes button clicked");

                // Retrieve values from the Bundle
                Bundle args = getArguments();
                if (args != null) {
                    String sessionID = args.getString("sessionID");
                    String timeStamp = args.getString("timeStamp");

                    // Invoke the ReleaseASlotHelper helper to release the slot
                    ReleaseASlotHelper releaseASlotHelper = new ReleaseASlotHelper(view, requireContext(), getFragmentManager());
                    releaseASlotHelper.releaseSlot(sessionID, timeStamp);

//                    // Check if the Bundle is not empty
//                    if (!endSessionDataBundle.isEmpty()) {
//                        // Navigate to ReleaseASlot03Fragment with the Bundle
//                        ReleaseASlot03Fragment releaseASlot03Fragment = new ReleaseASlot03Fragment();
//                        ((MainActivity) requireActivity()).replaceFragment(releaseASlot03Fragment, endSessionDataBundle, getView());
//                    }

                } else {
                    Log.e("ReleaseASlot02Fragment", "Arguments (Bundle) is null");
                }
            }
        });

        return view;
    }

    // Method to set the end  session data bundle
    public void setEndSessionDataBundle(Bundle bundle) {
        endSessionDataBundle = bundle;
    }
}