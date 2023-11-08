package com.example.officertestapp.status;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.officertestapp.R;

import java.util.ArrayList;

public class StatusMainFragment extends Fragment {


    ArrayList<ParkingStatusModel> parkingStatusModels = new ArrayList<>();

    public StatusMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_main, container, false);

        // call the setupParkingStatusModels function
        setupParkingStatusModels();

        // make a reference to the recycleView
        RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);

        PSRecycleViewAdapter adapter = new PSRecycleViewAdapter(parkingStatusModels, getContext());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    // setting up the parkingStatusModels Array
    public void setupParkingStatusModels(){
        String[] parkingIDs = {"PA001", "PA002", "PA003", "PA004", "PA005"};
        String[] parkingStatus = {"Filled", "Payment Due", "Free", "Free", "Free"};
        String[] vehicleNumbers = {"CAR 1565", "CAR 1565", "", "", ""};

        for(int i=0; i<parkingIDs.length; i++){
            parkingStatusModels.add(new ParkingStatusModel(parkingIDs[i], parkingStatus[i], vehicleNumbers[i]));
        }
    }
}