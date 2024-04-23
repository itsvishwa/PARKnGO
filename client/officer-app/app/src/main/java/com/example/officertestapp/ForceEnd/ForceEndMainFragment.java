package com.example.officertestapp.ForceEnd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.officertestapp.ForceEnd.Helpers.ForceEndMainSearchHelper;
import com.example.officertestapp.ForceEnd.Helpers.ForceEndedFetchData;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

public class ForceEndMainFragment extends Fragment {
    View loadingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View forceEndsessionsView = inflater.inflate(R.layout.fragment_force_end_main, container, false);

        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        //Create an instance of Parkngo Storage using the Fragment's context
        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());

        // Get data from storage
        String parkingName = parkngoStorage.getData("parkingName");

        // Find the TextView by its ID
        TextView parkingNameView = forceEndsessionsView.findViewById(R.id.force_end_frag_parking_name);

        // Set the officer's name in the TextView
        parkingNameView.setText(parkingName);



        // fetching data
        new ForceEndedFetchData(forceEndsessionsView, loadingView, getContext());

        // Helper Class
        ForceEndMainSearchHelper forceEndMainSearchHelper = new ForceEndMainSearchHelper(forceEndsessionsView, getContext(),requireActivity().getSupportFragmentManager(), loadingView);

        // Search bar
        //forceEndMainSearchHelper.initSearchBarListener();


        return loadingView;
    }


}