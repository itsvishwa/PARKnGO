package com.example.parkngo.parking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parkngo.R;

import java.util.ArrayList;

public class ParkingFragment extends Fragment {

    ArrayList<ParkingModel> parkingModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parking, container, false);
        setupParkingModels();

        RecyclerView recyclerView = view.findViewById(R.id.parking_frag_recycle_view);
        PMRecycleViewAdapter pmRecycleViewAdapter = new PMRecycleViewAdapter(parkingModels, getContext());

        recyclerView.setAdapter(pmRecycleViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    public void setupParkingModels(){
        String[] parkingNames = {"CMC CAR PARK 01", "Lotus CAR PARK", "ABC CAR PARk 01", "CMC CAR PARK 02"};
        String[] parkingTypes = {"Public", "Customer Only", "Public", "Customer Only"};
        String[] parkingRates = {"Rs.40/ 1H", "Rs.40/ 1H", "Rs.40/ 1H", "Rs.40/ 1H"};
        int[] noOfStars = {3, 4, 5, 3};
        int[] noOfReviews = {86, 90, 15, 78};
        int[] images = {R.drawable.parking_space_sample, R.drawable.parking_space_sample, R.drawable.parking_space_sample, R.drawable.parking_space_sample};

        for(int i=0; i<parkingNames.length; i++){
            parkingModels.add(new ParkingModel(parkingNames[i], parkingTypes[i], parkingRates[i], noOfStars[i], noOfReviews[i], images[i]));
        }
    }


}
