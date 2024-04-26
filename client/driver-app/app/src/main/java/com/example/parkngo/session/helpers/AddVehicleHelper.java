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

public class AddVehicleHelper {
    View addVehicleView;
    Context context;
    String vehicleProvince;
    String optionalDigit;
    String vehicleType;
    FragmentManager fragmentManager;

    public AddVehicleHelper(View addVehicleView, Context context, FragmentManager fragmentManager){
        this.addVehicleView = addVehicleView;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void init(){
        initSpinners();
        initVehicleProvinceSpinnerBtnListener();
        initVehicleTypeSpinnerBtnListener();
        initAddVehicleBtnListener();
        initOptionalSpinnerBtnListener();
    }


    private void initSpinners(){
        setVehicleProvinceSpinner();
        setVehicleTypeSpinner();
        setVehicleOptionalSpinner();
    }

    private void setVehicleProvinceSpinner(){
        Spinner spinner = addVehicleView.findViewById(R.id.add_vehicle_frag_province_spinner);
        ArrayList<String> provinceList = new ArrayList<>(Arrays.asList("CP", "EP", "NC", "NE", "NW", "SB", "SP", "UP", "WP"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinceList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }

    private void setVehicleTypeSpinner(){
        Spinner spinner = addVehicleView.findViewById(R.id.add_vehicle_frag_type_spinner);
        ArrayList<String> vehicleTypeList = new ArrayList<>(Arrays.asList("Car", "TukTuk", "Bicycle", "Mini Van", "Van", "Lorry", "Mini Bus", "Long Vehicles"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, vehicleTypeList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }

    private void setVehicleOptionalSpinner(){
        Spinner spinner = addVehicleView.findViewById(R.id.add_vehicle_frag_optional_spinner);
        ArrayList<String> optionalList = new ArrayList<>(Arrays.asList("-", "N/A", "ශ්\u200Dරී"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, optionalList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinner.setAdapter(adapter);
    }

    public void initVehicleProvinceSpinnerBtnListener(){
        Spinner spinner = addVehicleView.findViewById(R.id.add_vehicle_frag_province_spinner); // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vehicleProvince = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initOptionalSpinnerBtnListener(){
        Spinner spinner = addVehicleView.findViewById(R.id.add_vehicle_frag_optional_spinner); // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = adapterView.getItemAtPosition(i).toString();
                if(temp.equals("ශ්\u200Dරී")){
                    optionalDigit = "SRI";
                } else if (temp.equals("-")) {
                    optionalDigit = "DH";
                } else{
                    optionalDigit = "NA";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initVehicleTypeSpinnerBtnListener(){
        Spinner spinner = addVehicleView.findViewById(R.id.add_vehicle_frag_type_spinner); // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vehicleType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void initAddVehicleBtnListener(){

        Button button = addVehicleView.findViewById(R.id.add_vehicle_frag_add_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUserInputs()){
                    EditText vehicleNameView = addVehicleView.findViewById(R.id.add_vehicle_frag_vehicle_name);
                    EditText vehicleNumberLettersView = addVehicleView.findViewById(R.id.add_vehicle_frag_vehicle_number_letters);
                    EditText vehicleNumberDigitsView = addVehicleView.findViewById(R.id.add_vehicle_frag_vehicle_number_digits);

                    // add other views as well
                    String vehicleName = vehicleNameView.getText().toString();
                    String vehicleNumber = vehicleNumberLettersView.getText().toString() +  "#" + optionalDigit + "#" +  vehicleNumberDigitsView.getText().toString() + "#" + vehicleProvince;

                    // sending request
                    sendVehicleAddRequest(vehicleName, vehicleNumber, vehicleType);
                }
            }
        });


    }

    private void sendVehicleAddRequest(String vName, String vNumber, String vType){

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/driver/add_vehicle";

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
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(){
        Toast.makeText(context, "Vehicle Added Successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to the previous fragment
        fragmentManager.popBackStack();
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);

                Bundle data = new Bundle();

                    data.putString("MainText1", "Operation Failed");
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

    private boolean checkUserInputs(){
        EditText vehicleNameView = addVehicleView.findViewById(R.id.add_vehicle_frag_vehicle_name);
        EditText vehicleNumberLettersView = addVehicleView.findViewById(R.id.add_vehicle_frag_vehicle_number_letters);
        EditText vehicleNumberDigitsView = addVehicleView.findViewById(R.id.add_vehicle_frag_vehicle_number_digits);

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
}
