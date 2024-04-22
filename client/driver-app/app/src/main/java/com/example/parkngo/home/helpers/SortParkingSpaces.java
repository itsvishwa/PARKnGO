package com.example.parkngo.home.helpers;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkngo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortParkingSpaces {
    boolean min_distance = false;
    boolean max_free_slots = false;
    boolean is_public = false;
    boolean min_rate = false;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr;
    View view;
    View loadingView;

    public SortParkingSpaces(View view, View loadingView, ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr, boolean min_distance, boolean max_free_slots, boolean is_public, boolean min_rate){
        this.availableParkingSpaceModelsArr = availableParkingSpaceModelsArr;
        this.view = view;
        this.loadingView = loadingView;
        this.min_distance = min_distance;
        this.max_free_slots = max_free_slots;
        this.is_public = is_public;
        this.min_rate = min_rate;
        showData();
    }

    public void showData(){
        ArrayList<AvailableParkingSpaceModel> newAvailableParkingSpacesModelArr = new ArrayList<>(availableParkingSpaceModelsArr);
        if(min_distance){
            Collections.sort(newAvailableParkingSpacesModelArr, Comparator.comparingDouble(AvailableParkingSpaceModel::getDistance));
        }
        if(max_free_slots){
            Collections.sort(newAvailableParkingSpacesModelArr, Comparator.comparingInt(AvailableParkingSpaceModel::getFreeSlots).reversed());
        }
        if (is_public) {
            newAvailableParkingSpacesModelArr.removeIf(model -> !"Public".equals(model.getParkingType()));
        }
        if(min_rate){
            Collections.sort(newAvailableParkingSpacesModelArr, Comparator.comparingDouble(AvailableParkingSpaceModel::getRate));
        }

        // setting up the available parking spaces recycle view
        RecyclerView recyclerView = view.findViewById(R.id.ava_recycle_view);
        APSRecycleViewAdapter adapter = new APSRecycleViewAdapter(view.getContext(), newAvailableParkingSpacesModelArr);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

//         Replace the loading view with the parking view
        ViewGroup parent = (ViewGroup) loadingView.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(loadingView);
            parent.removeView(loadingView);
            parent.addView(view, index);
        }
    }
}