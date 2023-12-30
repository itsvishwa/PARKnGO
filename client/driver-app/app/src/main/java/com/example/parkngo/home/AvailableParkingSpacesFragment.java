package com.example.parkngo.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.parkngo.home.helpers.AvailableParkingSpaceModel;
import com.example.parkngo.home.helpers.SortParkingSpaces;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class AvailableParkingSpacesFragment extends Fragment {

    View view;
    View loadingView;
    View noAvailableParkingView;
    String vehicleType;
    MainActivity mainActivity;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr  = new ArrayList<>();;

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
        new APSFetchData(view, loadingView, noAvailableParkingView, getContext(), vehicleType, availableParkingSpaceModelsArr);


        // onclick listeners.......................................................................
        // search bar
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
                new APSSearchFetchData(view, loadingView, getContext(), vehicleType, query, mainActivity, availableParkingSpaceModelsArr);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        // sorting and filtering buttons
        ChipGroup chipGroup = view.findViewById(R.id.available_parking_spaces_frag_chip_group);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                boolean min_distance = false;
                boolean max_free_slots = false;
                boolean is_public = false;
                boolean min_rate = false;

                for(int i=0; i<checkedIds.size(); i++)
                {
                    Chip chipView = view.findViewById(checkedIds.get(i));
                    if(chipView.getText().toString().equals("Minimum Distance")){
                        min_distance = true;
                    }
                    else if(chipView.getText().toString().equals("Maximum Free Slots")){
                        max_free_slots = true;
                    }
                    else if(chipView.getText().toString().equals("Public")){
                        is_public = true;
                    }
                    else if(chipView.getText().toString().equals("Minimum Rate")){
                        min_rate = true;
                    }
                }

                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(view);
                    parent.removeView(view);
                    parent.addView(loadingView, index);
                }
                new SortParkingSpaces(view, loadingView, availableParkingSpaceModelsArr, min_distance, max_free_slots, is_public, min_rate);
            }
        });


        return loadingView; // loading view until data get fetched
    }
}
