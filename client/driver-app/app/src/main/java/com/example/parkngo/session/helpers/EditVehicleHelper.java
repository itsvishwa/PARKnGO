package com.example.parkngo.session.helpers;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.parkngo.R;

import java.util.ArrayList;
import java.util.Arrays;

public class EditVehicleHelper {
    int selected;
    String vehicleName;
    String vehicleNumber;
    String vehicleType;
    View editVehicleView;
    Context context;

    public EditVehicleHelper(int selected, String vehicleName, String vehicleNumber, String vehicleType, View editVehicleView, Context context){
        this.selected = selected;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.editVehicleView = editVehicleView;
        this.context = context;
        this.setVehicleProvinceSpinner();
        this.setVehicleTypeSpinner();
    }

    private void setVehicleProvinceSpinner(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_province_spinner);
        ArrayList<String> provinceList = new ArrayList<>(Arrays.asList("CP", "EP", "NC", "NE", "NW", "SB", "SP", "UP", "WP"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinceList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }

    private void setVehicleTypeSpinner(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_type_spinner);
        ArrayList<String> vehicleTypeList = new ArrayList<>(Arrays.asList("Car", "Bike", "3 Wheel", "Van", "Bus"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, vehicleTypeList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }

    public void initLayout(){
        EditText vehicleNameView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_name);
        EditText vehicleNumberLettersView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_letters);
        EditText vehicleNumberDigitsView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_digits);
        Spinner vehicleProvinceSpinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_province_spinner);
        Spinner vehicleTypeSpinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_type_spinner);

        ArrayList<String> vehicleTypeList = new ArrayList<>(Arrays.asList("Car", "Bike", "3 Wheel", "Van", "Bus"));
        ArrayList<String> provinceList = new ArrayList<>(Arrays.asList("CP", "EP", "NC", "NE", "NW", "SB", "SP", "UP", "WP"));

        vehicleNameView.setText(vehicleName);
        if(vehicleNumber.length() == 9){
            vehicleNumberLettersView.setText(vehicleNumber.substring(0,3));
            vehicleNumberDigitsView.setText(vehicleNumber.substring(3,7));
            int vpIndex = provinceList.indexOf(vehicleNumber.substring(7, 9));
            vehicleProvinceSpinner.setSelection(vpIndex);
        }else{
            vehicleNumberLettersView.setText(vehicleNumber.substring(0,2));
            vehicleNumberDigitsView.setText(vehicleNumber.substring(2,6));
            int vpIndex = provinceList.indexOf(vehicleNumber.substring(6, 8));
            vehicleProvinceSpinner.setSelection(vpIndex);
        }
        int vtIndex = vehicleTypeList.indexOf(vehicleType);
        vehicleTypeSpinner.setSelection(vtIndex);
    }
}
