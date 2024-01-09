package com.example.officertestapp.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.Home.Helpers.HomeFragmentHelper;
import com.example.officertestapp.R;

public class HomeMainFragment extends Fragment {

    public HomeMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);

        //Create an instance of ParkngoStorage using the Fragment's context
        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());

        // Get data from storage
        String firstName = parkngoStorage.getData("firstName");
        //String parkingName = parkngoStorage.getData("parkingName");

        // Find the TextView by its ID
        TextView officerNameTextView = view.findViewById(R.id.home_frag_officer_name);
        //TextView parkingNameView = view.findViewById(R.id.home_frag_parking_name);

        // Set the officer's name in the TextView
        officerNameTextView.setText(firstName);
        //parkingNameView.setText(parkingName);


        // Use the helper class to set app bar details
        HomeFragmentHelper.setTopAppBarDetailsInFragment(view, requireContext());

        return view;
    }
}