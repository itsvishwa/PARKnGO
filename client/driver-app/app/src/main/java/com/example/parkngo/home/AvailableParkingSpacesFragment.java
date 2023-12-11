package com.example.parkngo.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;

import java.util.ArrayList;

public class AvailableParkingSpacesFragment extends Fragment {


    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModels = new ArrayList<>(); // will hold all the models

    public AvailableParkingSpacesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_available_parking_spaces, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.ava_recycle_view);
        setupAvailableParkingSpaceModels();
        APSRecycleViewAdapter adapter = new APSRecycleViewAdapter(getContext(), availableParkingSpaceModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    // set the availableParkingSpaceModels => add data to it
    private void setupAvailableParkingSpaceModels(){
        String[] parkingNames = {"CMC CAR PARK 01", "CMC CAR PARK 02", "CMC CAR PARK 03", "CMC CAR PARK 04", "CMC CAR PARK 05", "CMC CAR PARK 06"};
        int[] freeSlots = {10, 20, 30, 40, 50, 60};
        int[] totalSlots = {100, 200, 300, 400, 500, 600};
        int[] rates = {150, 250, 350, 450, 550, 650};
        String[] parkingTypes = {"Public", "Customer Only", "Public", "Customer Only", "Public", "Customer Only"};
        int[] noOfStars = {1, 2, 3, 4, 5, 5};
        int[] noOfReviews = {11, 22, 33, 44, 55, 66};
        int[] distance = {111, 222, 333, 444, 555, 666};

        for(int i=0; i<parkingNames.length; i++){
            availableParkingSpaceModels.add(new AvailableParkingSpaceModel(parkingNames[i], freeSlots[i], totalSlots[i], rates[i], parkingTypes[i], noOfStars[i], noOfReviews[i], distance[i]));
        }
    }
}