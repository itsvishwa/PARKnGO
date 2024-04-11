package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.Home.ReleaseASlot01Fragment;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QRHelper {
    private View view;
    private Context context;
    private FragmentManager fragmentManager;
    ParkngoStorage parkngoStorage;

    public QRHelper(View view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.parkngoStorage = new ParkngoStorage(context);

    }

    public void processQRCode(String qrContent) {
        // get the token
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);
        String token = parkngoStorage.getData("token");

        String encodedParkingId = parkngoStorage.getData("parkingID");

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);

        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/qr/read_qr/"+ qrContent;
        Log.d("Request URL", apiURL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandler(response);
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
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                headers.put("encoded-parking-id", encodedParkingId);

                // Logging header values
                Log.d("Header Values", "Token: " + token);
                Log.d("Header Values", "Encoded Parking ID: " + encodedParkingId);

                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(String response){
        Log.d("Raw Response", response);
        Toast.makeText(context, "QR read Successful", Toast.LENGTH_SHORT).show();

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject responseData = jsonResponse.getJSONObject("response");

            // Extract values from the response
            String vehicleNumber = responseData.getString("vehicle_number");
            String vehicleType = responseData.getString("vehicle_type");
            String driverId = responseData.getString("driver_id");

            String vehicleTypeFCap = vehicleType.substring(0, 1).toUpperCase() + vehicleType.substring(1);

            // Split the vehicle number into letters, symbol, numbers, province using "#"
            String[] parts = vehicleNumber.split("#");

            // Extract individual parts of vehicle number
            String vNumLetters = parts[0];
            String vNUmSymbol = parts[1];
            String vNumNumbers = parts[2];
            String vNumProvince = parts[3];

            // Find the EditText fields in the fragment
            EditText vehicleNumLettersEditText = view.findViewById(R.id.vehicle_num_letters);
            EditText vehicleNumDigitsEditText = view.findViewById(R.id.vehicle_num_digits);
            EditText driverIdEditText = view.findViewById(R.id.driver_id_etxt);

            // Set the text of the EditText fields
            vehicleNumLettersEditText.setText(vNumLetters);
            vehicleNumDigitsEditText.setText(vNumNumbers);
            driverIdEditText.setText(driverId);

            // Find the Spinner for provinces
            Spinner spinnerProvinces = view.findViewById(R.id.spinner_provinces);

            // Get the array of province types
            ArrayList<String> provinceTypes = new ArrayList<>(Arrays.asList("WP", "SP", "CP", "EP", "NC", "NP", "NW", "SG", "UP", "NONE"));

            // Find the index of vNumProvince in the provinceTypes array
            int provinceIndex = provinceTypes.indexOf(vNumProvince);

            // Set the selection of the Spinner to the provinceIndex
            if (provinceIndex != -1) {
                spinnerProvinces.setSelection(provinceIndex);
            }


            Spinner spinnerSymbols = view.findViewById(R.id.spinner_symbols);
            ArrayList<String> symbols = new ArrayList<>(Arrays.asList("ශ්\u200Dරී", "-", "NONE"));



            // Find the Spinner for vehicle types
            Spinner spinnerVehicleTypes = view.findViewById(R.id.spinner_vehicle_types);

            // Get the array of vehicle types
            ArrayList<String> vehicleTypes = new ArrayList<>(Arrays.asList("Car", "Bike", "Van", "Lorry", "Bus"));

            // Find the index of vehicleType in the vehicleTypes array
            int vehicleTypeIndex = vehicleTypes.indexOf(vehicleTypeFCap);
            Log.d("Vehicle Type", "Index: " + vehicleTypeIndex + ", Type: " + vehicleType);

            // Set the selection of the Spinner to the vehicleTypeIndex
            if (vehicleTypeIndex != -1) {
                spinnerVehicleTypes.setSelection(vehicleTypeIndex);
            }


        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                if(response.equals("101") || response.equals("204")) // the officer's parking space has been changed
                {
                    parkngoStorage.clearData();
                    Intent i = new Intent(context, HeroActivity.class);
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.logout_immediately();
                }else{
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
