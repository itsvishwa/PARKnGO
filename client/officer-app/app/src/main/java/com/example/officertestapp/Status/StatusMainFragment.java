package com.example.officertestapp.Status;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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



        Spinner spinnerSlots = view.findViewById(R.id.vehicleTypeSpinner);
        spinnerSlots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayList<String> vehicleTypes = new ArrayList<>();
        String[] vTypes = {"Car", "Bike", "Van", "Lorry", "Bus"};

        for (int i=0; i<vTypes.length; i++){
            vehicleTypes.add(vTypes[i]);
        }

        ArrayAdapter<String> vTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, vehicleTypes);
        vTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerSlots.setAdapter(vTypeAdapter);



        return view;
    }

    // setting up the parkingStatusModels Array
    public void setupParkingStatusModels(){
       String[] vehicleNumbers = {"CAF 6565 WP", "CAF 6565 WP", "CAF 6565 WP", "CAF 6565 WP", "CAF 6565 WP"};
       String[] vehicleTypes = {"CAR", "CAR", "CAR", "CAR", "CAR"};
        String[] dateAndTime = {"07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM"};
       String[] parkingStatus = {"Payment Due", "In Progress", "Payment Due", "Payment Due", "Payment Due"};


       for(int i=0; i<vehicleNumbers.length; i++){
           parkingStatusModels.add(new ParkingStatusModel(vehicleNumbers[i], vehicleTypes[i], dateAndTime[i], parkingStatus[i]));
       }
    }
}