package com.example.parkngo.session.helpers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ErrorFragment;
import com.example.parkngo.helpers.ParkngoStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EditVehicleHelper {
    int selected;
    String vehicleName;
    String vehicleNumber;
    String vehicleType;
    View editVehicleView;
    Context context;
    String spinnerVehicleProvince;
    String spinnerVehicleType;
    String spinnerVehicleOptionalDigit;
    FragmentManager fragmentManager;

    public EditVehicleHelper(int selected, String vehicleName, String vehicleNumber, String vehicleType, View editVehicleView, Context context, FragmentManager fragmentManager){
        this.selected = selected;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.editVehicleView = editVehicleView;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    public void init(){
        setVehicleProvinceSpinner();
        setVehicleTypeSpinner();
        setVehicleOptionalSpinner();
        initLayout();
        initVehicleProvinceSpinnerBtnListener();
        initVehicleOptionalSpinnerBtnListener();
        initVehicleTypeSpinnerBtnListener();
        initEditBtnHandler();
        initDiscardBtnHandler();
        initDeleteBtnHandler();
    }


    private void setVehicleProvinceSpinner(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_province_spinner);
        ArrayList<String> provinceList = new ArrayList<>(Arrays.asList("CP", "EP", "NC", "NE", "NW", "SB", "SP", "UP", "WP"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinceList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }


    private void setVehicleOptionalSpinner(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_optional_spinner);
        ArrayList<String> optionalList = new ArrayList<>(Arrays.asList("-", "N/A", "ශ්\u200Dරී"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, optionalList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }

    private void setVehicleTypeSpinner(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_type_spinner);
        ArrayList<String> vehicleTypeList = new ArrayList<>(Arrays.asList("Car", "TukTuk", "Bicycle", "Mini Van", "Van", "Lorry", "Mini Bus", "Long Vehicles"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, vehicleTypeList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }

    public void initVehicleProvinceSpinnerBtnListener(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_province_spinner); // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerVehicleProvince = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initVehicleOptionalSpinnerBtnListener(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_optional_spinner); // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerVehicleOptionalDigit = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initVehicleTypeSpinnerBtnListener(){
        Spinner spinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_type_spinner); // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerVehicleType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initLayout(){
        EditText vehicleNameView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_name);
        EditText vehicleNumberLettersView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_letters);
        EditText vehicleNumberDigitsView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_digits);
        Spinner vehicleProvinceSpinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_province_spinner);
        Spinner vehicleTypeSpinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_type_spinner);
        Spinner vehicleNumberOptionalSpinner = editVehicleView.findViewById(R.id.edit_vehicle_frag_optional_spinner);

        ArrayList<String> vehicleTypeList = new ArrayList<>(Arrays.asList("Car", "TukTuk", "Bicycle", "Mini Van", "Van", "Lorry", "Mini Bus", "Long Vehicles"));
        ArrayList<String> vehicleOptionalList = new ArrayList<>(Arrays.asList("DH", "NA", "SRI"));
        ArrayList<String> provinceList = new ArrayList<>(Arrays.asList("CP", "EP", "NC", "NE", "NW", "SB", "SP", "UP", "WP"));

        String[] vehicleNumberArr = vehicleNumber.split("#");

        vehicleNameView.setText(vehicleName);

        vehicleNumberLettersView.setText(vehicleNumberArr[0]);

        int voIndex = vehicleOptionalList.indexOf(vehicleNumberArr[1]);
        vehicleNumberOptionalSpinner.setSelection(voIndex);

        vehicleNumberDigitsView.setText(vehicleNumberArr[2]);

        int vpIndex = provinceList.indexOf(vehicleNumberArr[3]);
        vehicleProvinceSpinner.setSelection(vpIndex);

        int vtIndex = vehicleTypeList.indexOf(vehicleType);
        vehicleTypeSpinner.setSelection(vtIndex);
    }

    public void initEditBtnHandler(){
        Button editButton = editVehicleView.findViewById(R.id.edit_vehicle_frag_edit_btn);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUserInputs()){
                    EditText vehicleNameView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_name);
                    EditText vehicleNumberLettersView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_letters);
                    EditText vehicleNumberDigitView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_digits);

                    String newVehicleName = vehicleNameView.getText().toString();

                    String temp;
                    if(spinnerVehicleOptionalDigit.equals("ශ්\u200Dරී")){
                        temp = "SRI";
                    } else if (spinnerVehicleOptionalDigit.equals("-")) {
                        temp = "DH";
                    }else{
                        temp = "NA";
                    }
                    String newVehicleNumber = vehicleNumberLettersView.getText().toString()  + "#" + temp + "#" + vehicleNumberDigitView.getText().toString() + "#" +  spinnerVehicleProvince;

                    // sending request
                    sendVehicleEditRequest(newVehicleName, newVehicleNumber, spinnerVehicleType, selected);
                }
            }
        });

    }

    private boolean checkUserInputs(){
        EditText vehicleNameView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_name);
        EditText vehicleNumberLettersView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_letters);
        EditText vehicleNumberDigitsView = editVehicleView.findViewById(R.id.edit_vehicle_frag_vehicle_number_digits);

        if(vehicleNameView.getText().toString().equals("")){
            Toast.makeText(context, "Vehicle name should be non empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (vehicleNumberLettersView.getText().toString().equals("")) {
            Toast.makeText(context, "Vehicle number should be valid", Toast.LENGTH_LONG).show();
            return false;
        } else if (vehicleNumberDigitsView.getText().toString().equals("")) {
            Toast.makeText(context, "Vehicle number should be valid", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void sendVehicleEditRequest(String vName, String vNumber, String vType, int selected){

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/driver/edit_vehicle";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandler();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorResponseHandler(error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("vehicle_name", vName);
                params.put("vehicle_number", vNumber);
                params.put("vehicle_type", vType);
                params.put("selected", selected + "");
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(){
        Toast.makeText(context, "Vehicle information are updated Successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to the previous fragment
        fragmentManager.popBackStack();
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");

                Bundle data = new Bundle();
                data.putString("MainText1", "Unknown Error");
                data.putString("subText1", "Please try again later");
                data.putInt("img", R.drawable.not_available);
                data.putString("MainText2", "Unknown error occurred! ");
                data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new ErrorFragment(), data);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initDiscardBtnHandler(){
        Button button = editVehicleView.findViewById(R.id.edit_vehicle_frag_discard_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLayout();
            }
        });
    }

    public void initDeleteBtnHandler(){
        TextView deleteView = editVehicleView.findViewById(R.id.edit_vehicle_frag_delete_btn);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVehicleDeleteRequest();
            }
        });
    }

    private void sendVehicleDeleteRequest(){

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/driver/delete_vehicle/" + selected;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Vehicle information are deleted!", Toast.LENGTH_SHORT).show();
                        fragmentManager.popBackStack();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorResponseHandler(error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

