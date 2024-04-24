package com.example.officertestapp.ForceEnd;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.officertestapp.ForceEnd.Helpers.ForceEndedFetchData;
import com.example.officertestapp.ForceEnd.Helpers.ForceEndedRecycleViewAdapter;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.R;

import java.util.ArrayList;

public class ForceEndMainFragment extends Fragment {
    private View loadingView;
    private View forceEndsessionsView;
    private ForceEndedRecycleViewAdapter adapter;
    private ArrayList<ForceEndedModel> forceEndedModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        forceEndsessionsView = inflater.inflate(R.layout.fragment_force_end_main, container, false);

        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // Initialize forceEndedModels ArrayList
        forceEndedModels = new ArrayList<>();

        //Create an instance of Parkngo Storage using the Fragment's context
        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());

        // Get data from storage
        String parkingName = parkngoStorage.getData("parkingName");

        // Find the TextView by its ID
        TextView parkingNameView = forceEndsessionsView.findViewById(R.id.force_end_frag_parking_name);

        // Set the officer's name in the TextView
        parkingNameView.setText(parkingName);

        // Initialize search bar listener
        initSearchBarListener();

        // Initialize ForceEndedFetchData and pass the empty ArrayList
        new ForceEndedFetchData(forceEndsessionsView, loadingView, getContext(), forceEndedModels);

        logForceEndedModels();

        adapter = new ForceEndedRecycleViewAdapter(forceEndedModels,getContext(), forceEndsessionsView);

        return loadingView;
    }


    public void initSearchBarListener(){
        SearchView searchView = forceEndsessionsView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Query text changed: " + newText);
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        ArrayList<ForceEndedModel> filteredList = new ArrayList<>();
        for (ForceEndedModel item : forceEndedModels) {
            if (item.getVehicleNumber().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.setFilteredList(filteredList);
        }
    }

    public void logForceEndedModels() {
        if (forceEndedModels != null) {
            for (ForceEndedModel item : forceEndedModels) {
                Log.e(TAG, "forceEndedModels is not null");
                Log.d(TAG, "Vehicle Number: " + item.getVehicleNumber());
                // Add more log statements as needed for other properties of ForceEndedModel
            }
        } else {
            Log.e(TAG, "forceEndedModels is null");
        }
    }

}