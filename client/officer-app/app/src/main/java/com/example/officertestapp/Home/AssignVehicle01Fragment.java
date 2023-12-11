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
        provinceTypes.add("WP");
        provinceTypes.add("SP");
        provinceTypes.add("CP");
        provinceTypes.add("EP");
        provinceTypes.add("NC");
        provinceTypes.add("NP");
        provinceTypes.add("NW");
        provinceTypes.add("SG");
        provinceTypes.add("UP");

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, provinceTypes);
        provinceAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerProvinces.setAdapter(provinceAdapter);



        Spinner spinnerSlots = view.findViewById(R.id.spinner_slots);
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

        ArrayList<String> parkingSlots = new ArrayList<>();
        parkingSlots.add("P00A1");
        parkingSlots.add("P00A2");
        parkingSlots.add("P00A3");
        parkingSlots.add("P00B1");
        parkingSlots.add("P00B2");
        parkingSlots.add("P00B3");
        parkingSlots.add("P00C1");
        parkingSlots.add("P00C2");
        parkingSlots.add("P00C3");

        ArrayAdapter<String> slotsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, parkingSlots);
        slotsAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerSlots.setAdapter(slotsAdapter);

        return view;
    }
}

