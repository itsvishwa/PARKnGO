package com.example.officertestapp.Status;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.R;
import com.example.officertestapp.Status.Helpers.StatusFetchData;
import com.example.officertestapp.Status.Helpers.StatusMainSpinner;

import java.util.ArrayList;

public class StatusMainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_main, container, false);
        View loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // fetching data
        StatusFetchData statusFetchData = new StatusFetchData(getContext(),view, loadingView);

        // init spinners
        StatusMainSpinner statusMainSpinner = new StatusMainSpinner(view, loadingView, getContext(), statusFetchData);

        //Create an instance of Parkngo Storage using the Fragment's context
        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());

        // Get data from storage
        String firstName = parkngoStorage.getData("firstName");
        String parkingName = parkngoStorage.getData("parkingName");

        // Find the TextView by its ID
        TextView officerNameTextView = view.findViewById(R.id.status_frag_officer_name);
        TextView parkingNameView = view.findViewById(R.id.status_frag_parking_name);

        // Set the officer's name in the TextView
        officerNameTextView.setText(firstName);
        parkingNameView.setText(parkingName);

        return loadingView;
    }
}