package com.example.officertestapp.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.officertestapp.Profile.Helpers.ProfileFragmentHelper;
import com.example.officertestapp.R;
import com.example.officertestapp.Helpers.ParkngoStorage;

public class ProfileMainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_main, container, false);

        TextView officerIDView = view.findViewById(R.id.profile_main_frag_officer_id);
        TextView nameView = view.findViewById(R.id.profile_main_frag_name);
        TextView phoneNumberView = view.findViewById(R.id.profile_main_frag_mobile_number);
        TextView nicView = view.findViewById(R.id.profile_main_frag_nic);
        TextView parkingNameView = view.findViewById(R.id.profile_main_frag_parking_name);

        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());
        String firstName = parkngoStorage.getData("firstName");
        String lastName = parkngoStorage.getData("lastName");
        String mobileNumber = parkngoStorage.getData("mobileNumber");
        String nic = parkngoStorage.getData("nic");
        String parkingName = parkngoStorage.getData("parkingName");
        String officerID  = parkngoStorage.getData("officerID");

        officerIDView.setText(officerID);
        nameView.setText(firstName + " " + lastName);
        phoneNumberView.setText("(+94) " + mobileNumber);
        nicView.setText(nic);
        parkingNameView.setText(parkingName);

        // top app bar
        TextView parkingNameAppBarView = view.findViewById(R.id.profile_frag_app_bar_parking_name);
        parkingNameAppBarView.setText(parkingName);

        // Use the helper class to set app bar details
        ProfileFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        return view;
    }
}