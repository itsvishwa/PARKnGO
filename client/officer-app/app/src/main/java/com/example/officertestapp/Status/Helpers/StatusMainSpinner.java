package com.example.officertestapp.Status.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.officertestapp.R;

import java.util.ArrayList;

public class StatusMainSpinner {
    public StatusMainSpinner(View view, Context context){
        this.init(view, context);
    }

    public void init(View view, Context context){

        // Vehicle type Spinner

        Spinner spinnerVehicleType = view.findViewById(R.id.vehicleTypeSpinner);
        spinnerVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(context, item + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayList<String> vehicleTypes = new ArrayList<>();
        String[] vTypes = {"Vehicle Type", "Car", "Bike", "Van", "Lorry", "Bus"};

        for (int i=0; i<vTypes.length; i++){
            vehicleTypes.add(vTypes[i]);
        }

        ArrayAdapter<String> vTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, vehicleTypes);
        vTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerVehicleType.setAdapter(vTypeAdapter);



        // Status Spinner
        Spinner spinnerStatus = view.findViewById(R.id.statusTypeSpinner);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(context, item + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayList<String> statusTypes = new ArrayList<>();
        String[] sTypes = {"Status", "Payment Due", "In Progress"};

        for (int i=0; i<sTypes.length; i++){
            statusTypes.add(sTypes[i]);
        }

        ArrayAdapter<String> sTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusTypes);
        sTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerStatus.setAdapter(sTypeAdapter);
    }
}
