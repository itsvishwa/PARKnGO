package com.example.parkngo.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.home.helpers.APSFetchData;
import com.example.parkngo.home.helpers.APSSearchFetchData;

public class AvailableParkingSpacesFragment extends Fragment {

    View view;
    View loadingView;
    View noAvailableParkingView;
    String vehicleType;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view  = inflater.inflate(R.layout.fragment_available_parking_spaces, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);
        noAvailableParkingView = inflater.inflate(R.layout.fragment_no_available_parking, container, false);
        mainActivity = (MainActivity)requireContext();


        // get the selected vehicle type from prev fragment
        if (getArguments() != null) {
            vehicleType = getArguments().getString("vehicleType", "none");
        }

        // fetch data via api and set to the views
        new APSFetchData(view, loadingView, noAvailableParkingView, getContext(), vehicleType);


        // onclick listeners
        SearchView searchView = view.findViewById(R.id.available_parking_spaces_frag_search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(view);
                    parent.removeView(view);
                    parent.addView(loadingView, index);
                }
                new APSSearchFetchData(view, loadingView, getContext(), vehicleType, query, mainActivity);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        return loadingView; // loading view until data get fetched
    }
}
