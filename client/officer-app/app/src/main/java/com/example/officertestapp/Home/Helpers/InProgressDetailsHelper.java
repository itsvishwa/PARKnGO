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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.Home.ReleaseASlotConfirmationFragment;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class InProgressDetailsHelper {
    Context context;
    View view;
    ParkngoStorage parkngoStorage;
    Button continueBtn;
    Spinner spinnerProvinces;
    Spinner spinnerSymbols;
    ArrayList<String> provinceTypes;
    ArrayList<String> symbols;
    public InProgressDetailsHelper (View view, Context context, Button continueBtn, Spinner spinnerProvinces, Spinner spinnerSymbols, ArrayList<String> provinceTypes, ArrayList<String> symbols) {
        this.view = view;
        this.context = context;
        this.parkngoStorage = new ParkngoStorage(context);
        this.spinnerProvinces = spinnerProvinces;
        this.spinnerSymbols = spinnerSymbols;
        this.provinceTypes = provinceTypes;
        this.symbols = symbols;
        this.continueBtn = continueBtn;
    }
    public void initLayout(String sessionId) {
        String parkingId = parkngoStorage.getData("parkingID");

        // get the token
        String token = parkngoStorage.getData("token");

        // volley request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/get_in_progress_session_details/" + sessionId;
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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                headers.put("encoded-parking-id", parkingId);

                // Logging header values
                Log.d("Header Values", "Token: " + token);
                Log.d("Header Values", "Encoded Parking ID: " + parkingId);

                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(String response){
        Log.d("Raw Response", response);

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject responseData = jsonResponse.getJSONObject("response");

            // Extract values from the JSON response
            String responseCode = responseData.getString("response_code");
            String message = responseData.getString("message");

            // Log the parsed response data
            Log.d("responseCode", responseCode);
            Log.d("message", message);


            // Check if the response code is "800"
            if ("800".equals(responseCode)) {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                // Extract values from the JSON response
                String sessionId = responseData.getString("session_id");
                String endTimeStamp = responseData.getString("end_Time_Stamp");
                String startTimeStamp = responseData.getString("start_Time_Stamp");
                String formattedSTime = responseData.getString("formatted_STime");
                String formattedSDate = responseData.getString("formatted_SDate");
                String duration = responseData.getString("duration");
                String amount = responseData.getString("amount");
                String vehicleNumber = responseData.getString("vehicle_Number");
                String vehicleType = responseData.getString("vehicle_Type");

                String formattedSDateTime = formattedSTime + "\n" + formattedSDate;


                // Split the vehicle number into letters, symbol, numbers, province using "#"
                String[] vehicleNumberParts = vehicleNumber.split("#");
                String vNumLetters = vehicleNumberParts[0];
                String vNumSymbol = vehicleNumberParts[1];
                String vNumNumbers = vehicleNumberParts[2];
                String vNumProvince = vehicleNumberParts[3];

                // Find the EditText fields in the fragment
                EditText vehicleNumLettersEditText = view.findViewById(R.id.vehicle_num_letters);
                EditText vehicleNumDigitsEditText = view.findViewById(R.id.vehicle_num_digits);

                // Set the text of the EditText fields
                vehicleNumLettersEditText.setText(vNumLetters);
                vehicleNumDigitsEditText.setText(vNumNumbers);


                // Handle special characters and set the selection in spinnerSymbols
                if ("SRI".equals(vNumSymbol)) {
                    vNumSymbol = "ශ්\u200Dරී";
                } else if ("DH".equals(vNumSymbol)) {
                    vNumSymbol = "-";
                } else if ("NA".equals(vNumSymbol)) {
                    vNumSymbol = "NONE";
                }


                // Find the index of province in the provinceTypes array
                int provinceIndex = provinceTypes.indexOf(vNumProvince);
                Log.d("Province", "Index: " + provinceIndex + ", Province: " + vNumProvince);

                // Set the selection of the Spinner to the provinceIndex
                if (provinceIndex != -1) {
                    spinnerProvinces.setSelection(provinceIndex);
                }


                // Find the index of symbol in the symbolTypes array
                int symbolIndex = symbols.indexOf(vNumSymbol);
                Log.d("Symbol", "Index: " + symbolIndex + ", Symbol: " + vNumSymbol);

                // Set the selection of the Spinner to the provinceIndex
                if (symbolIndex != -1) {
                    spinnerSymbols.setSelection(symbolIndex);
                }

                TextView parkedDateTimeTextView = view.findViewById(R.id.parked_Date_Time_txt);
                TextView durationTextView = view.findViewById(R.id.duration_txt);
                TextView amountTextView = view.findViewById(R.id.amount_txt);

                parkedDateTimeTextView.setText(formattedSDateTime);
                durationTextView.setText(duration);
                amountTextView.setText(amount);

                // Bundle response values
                Bundle inProBundle = new Bundle();
                inProBundle.putString("vehicleNumber", vehicleNumber);
                inProBundle.putString("vehicleType", vehicleType);
                inProBundle.putString("sessionId", sessionId);
                inProBundle.putString("endTimeStamp", endTimeStamp);

                // Enable the continueBtn
                continueBtn.setEnabled(true);
                continueBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor));

                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!inProBundle.isEmpty()) {

                            // Navigate o  Bundle to AssignAVehicleConfirmationFragment
                            MainActivity mainActivity = (MainActivity) context;
                            mainActivity.replaceFragment(new ReleaseASlotConfirmationFragment(), inProBundle, view);
                        }
                    }
                });


            } else {
                // Show a toast message with the response message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e){
            Log.e("JSON Parsing Error", "Error parsing response: " + e.getMessage());
            Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                JSONObject responseData = jsonResponse.getJSONObject("response");

                // Extract values from the JSON response
                String responseCode = responseData.getString("response_code");
                String message = responseData.getString("message");

                if(responseCode.equals("101") || responseCode.equals("204")) // the officer's parking space has been changed
                {
                    parkngoStorage.clearData();
                    Intent i = new Intent(context, HeroActivity.class);
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.logout_immediately();
                }else{
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
