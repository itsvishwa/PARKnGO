package com.example.officertestapp.Status;

import android.content.Intent;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_status_main, container, false);

        setupParkingStatusModels();

        RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);

        PSRecycleViewAdapter adapter = new PSRecycleViewAdapter(parkingStatusModels, getContext());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void setupParkingStatusModels() {
        String[] parkingIDs = {"P00A1", "P00A2", "P00A3", "P00B1", "P00B2", "P00B3", "P00C1"};

        String[] parkingStatus = {"Filled", "Free", "Free", "Filled", "Payment Due", "Filled", "Filled"};

        String[] vehicleNumbers = {"CAR 1565", "", "", "CAR 6885", "CAR 1575", "CAF 1665", "CAA 1555"};

        for(int i = 0; i < parkingIDs.length; i++) {
            parkingStatusModels.add(new ParkingStatusModel(parkingIDs[i], parkingStatus[i], vehicleNumbers[i]));
        }

    }
}