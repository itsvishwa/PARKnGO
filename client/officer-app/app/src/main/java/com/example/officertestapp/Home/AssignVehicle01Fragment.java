package com.example.officertestapp.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.officertestapp.R;

import java.util.ArrayList;

public class AssignVehicle01Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assign_vehicle01, container, false);

        Spinner spinnerProvinces = view.findViewById(R.id.spinner_provinces);
        spinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), item + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayList<String> provinceTypes = new ArrayList<>();
        String[] pTypes = {"WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP"};

        for (int i=0; i<pTypes.length; i++){
            provinceTypes.add(pTypes[i]);
        }

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);



        Spinner spinnerSlots = view.findViewById(R.id.spinner_vehicle_types);
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
}

