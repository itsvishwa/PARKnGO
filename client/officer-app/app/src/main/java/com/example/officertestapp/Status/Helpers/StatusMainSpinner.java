package com.example.officertestapp.Status.Helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.officertestapp.R;

import java.util.ArrayList;

public class StatusMainSpinner {

    String vehicleType;
    String statusType;
    Context context;
    View loadingView;
    View statusMainView;
    StatusFetchData statusFetchData;
    public StatusMainSpinner(View statusMainView, View loadingView, Context context, StatusFetchData statusFetchData){
        this.statusMainView = statusMainView;
        this.context = context;
        this.vehicleType = "all";
        this.statusType = "all";
        this.statusFetchData = statusFetchData;
        this.loadingView = loadingView;
        this.init(statusMainView, context);
    }

    public void init(View view, Context context){

        // Vehicle type Spinner

        Spinner spinnerVehicleType = view.findViewById(R.id.vehicleTypeSpinner);
        spinnerVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString().toLowerCase();
                if(!item.equals(vehicleType)){
                    vehicleType = item;
                    spinnerBtnHandler();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        ArrayList<String> vehicleTypes = new ArrayList<>();
        String[] vTypes = {"All", "Car", "Bike", "Van", "Lorry", "Bus"};

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
                String item = adapterView.getItemAtPosition(i).toString().toLowerCase();
                String newItem;
                if(item.equals("payment due")){
                    newItem = "pd";
                } else if(item.equals("in progress")) {
                    newItem = "ip";
                }else{
                    newItem = item;
                }
                if(!newItem.equals(statusType)){
                    statusType = newItem;
                    spinnerBtnHandler();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        ArrayList<String> statusTypes = new ArrayList<>();
        String[] sTypes = {"All", "Payment Due", "In Progress"};

        for (int i=0; i<sTypes.length; i++){
            statusTypes.add(sTypes[i]);
        }

        ArrayAdapter<String> sTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusTypes);
        sTypeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerStatus.setAdapter(sTypeAdapter);
    }

    private void spinnerBtnHandler(){
        ViewGroup parent = (ViewGroup) statusMainView.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(statusMainView);
            parent.removeView(statusMainView);
            parent.addView(loadingView, index);
        }
        statusFetchData.fetchData(vehicleType, statusType);
    }
}
