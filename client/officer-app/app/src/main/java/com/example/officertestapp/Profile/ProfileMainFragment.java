package com.example.officertestapp.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Helpers.DateTimeHelper;
import com.example.officertestapp.Profile.Helpers.ProfileFragmentHelper;
import com.example.officertestapp.R;
import com.example.officertestapp.Helpers.ParkngoStorage;

public class ProfileMainFragment extends Fragment {
    private ParkngoStorage parkngoStorage;
    private DateTimeHelper dateTimeHelper;
    private String timestamp;
    private TextView currentTimeView;
    private TextView currentDateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileMainView =  inflater.inflate(R.layout.fragment_profile_main, container, false);

        // Use the helper class to set app bar details
        //HomeFragmentHelper.setTopAppBarDetailsInFragment(profileMainView, requireContext());

        // Helper Class
        ProfileFragmentHelper profileFragmentHelper = new ProfileFragmentHelper(profileMainView, getContext(), requireActivity().getSupportFragmentManager());


        TextView officerNameView = profileMainView.findViewById(R.id.profile_main_frag_name);
        TextView officerMobileNumView = profileMainView.findViewById(R.id.profile_main_frag_mobile_number);
        TextView officerIdView = profileMainView.findViewById(R.id.profile_main_frag_officer_id);
        TextView officerNICView = profileMainView.findViewById(R.id.profile_main_frag_nic);

        TextView officerParkingNameView = profileMainView.findViewById(R.id.profile_main_frag_parking_name);
        TextView officerCompanyNameView = profileMainView.findViewById(R.id.profile_frag_company_name);

        currentTimeView = profileMainView.findViewById(R.id.profile_frag_time);
        currentDateView = profileMainView.findViewById(R.id.profile_frag_date);


        //Retrieve data from shared preferences
        parkngoStorage = new ParkngoStorage(getContext());
        String firstNameStr = parkngoStorage.getData("firstName");
        String lastNameStr = parkngoStorage.getData("lastName");
        String mobileNumberStr = parkngoStorage.getData("mobileNumber");
        String nicStr = parkngoStorage.getData("nic");
        String parkingNameStr = parkngoStorage.getData("parkingName");
        String officerIdStr = parkngoStorage.getData("officerID");
        String companyNameStr = parkngoStorage.getData("companyName");
        String companyPhoneNumberStr = parkngoStorage.getData("companyPhoneNumber");

        TextView parkingNameView = profileMainView.findViewById(R.id.profile_frag_app_bar_parking_name);
        parkingNameView.setText(parkingNameStr);

        officerNameView.setText(firstNameStr + " " + lastNameStr);
        officerMobileNumView.setText(mobileNumberStr);
        officerIdView.setText(officerIdStr);
        officerNICView.setText(nicStr);

        officerParkingNameView.setText(parkingNameStr);
        officerCompanyNameView.setText(companyNameStr);


        // Initialize and start DateTimeHelper
        dateTimeHelper = new DateTimeHelper(currentTimeView, currentDateView);
        dateTimeHelper.startUpdatingDateTime();


        //Initialize Call button to take a phone call to the company number
        Button callBtn = profileMainView.findViewById(R.id.profile_frag_call_btn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + companyPhoneNumberStr));
                startActivity(intent);
            }
        });


        // Handle Duty Off Swipe Button
        profileFragmentHelper.initDutyOffSwipeButton(currentTimeView, currentDateView);

        return profileMainView;
    }
}