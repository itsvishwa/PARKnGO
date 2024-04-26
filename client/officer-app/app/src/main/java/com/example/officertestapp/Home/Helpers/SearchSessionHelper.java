package com.example.officertestapp.Home.Helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.officertestapp.Helpers.VehicleNumberHelper;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.Home.ReleaseASlotConfirmationFragment;
import com.example.officertestapp.Home.ReleaseASlotFragment;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class SearchSessionHelper {
    private View view;
    private Context context;
    private FragmentManager fragmentManager;
    private Button continueBtn;
    ParkngoStorage parkngoStorage;

    public SearchSessionHelper(View view, Context context, FragmentManager fragmentManager, Button continueBtn) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.continueBtn = continueBtn;
        this.parkngoStorage = new ParkngoStorage(context);
    }

    public void searchSession() {
        Log.d("SearchSession", "This is searchSession");

        // getting reference to the views
        EditText lettersEditTextView = view.findViewById(R.id.vehicle_num_letters);
        EditText digitsEditTextView = view.findViewById(R.id.vehicle_num_digits);

        Spinner symbolsSpinnerView = view.findViewById(R.id.spinner_symbols);
        Spinner provincesSpinnerView = view.findViewById(R.id.spinner_provinces);


        if (TextUtils.isEmpty(lettersEditTextView.getText())
                || TextUtils.isEmpty(digitsEditTextView.getText())
                || provincesSpinnerView.getSelectedItem() == null) {
            Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show();

        }else {
            // Extract user inputs
            String letters = lettersEditTextView.getText().toString().toUpperCase();
            String selectedSymbol = symbolsSpinnerView.getSelectedItem().toString();
            String digits = digitsEditTextView.getText().toString();
            String selectedProvince = provincesSpinnerView.getSelectedItem().toString();

            String urlSafeVehicleNumber = letters + "~" + selectedSymbol + "~" +digits + "~" + selectedProvince;

            // Preprocess vehicle number
            String processedUrlSafeVehicleNumber = VehicleNumberHelper.preprocessVehicleNumber(urlSafeVehicleNumber);

            // Concatenate the values to create the complete vehicle number
            Log.d("Final Vehicle Number", processedUrlSafeVehicleNumber);

            // get the token
            ParkngoStorage parkngoStorage = new ParkngoStorage(context);
            String token = parkngoStorage.getData("token");

            String encodedParkingId = parkngoStorage.getData("parkingID");

            // volley request
            RequestQueue queue = Volley.newRequestQueue(context);

            String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/search/"+ processedUrlSafeVehicleNumber;
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


                TextView parkedDateTimeView = view.findViewById(R.id.parked_Date_Time_txt);
                TextView durationView = view.findViewById(R.id.duration_txt);
                TextView amountView = view.findViewById(R.id.amount_txt);

                String formattedSDateTime = formattedSTime + "\n" + formattedSDate;

                // Set the values to TextViews
                parkedDateTimeView.setText(formattedSDateTime);
                durationView.setText(duration);
                amountView.setText(amount);

                // Bundle response values
                Bundle searchBundle = new Bundle();
                searchBundle.putString("vehicleNumber", vehicleNumber);
                searchBundle.putString("vehicleType", vehicleType);
                searchBundle.putString("sessionId", sessionId);

                // Enable the continueBtn
                continueBtn.setEnabled(true);
                continueBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor));

                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!searchBundle.isEmpty()) {

                            // Navigate o  Bundle to AssignAVehicleConfirmationFragment
                            MainActivity mainActivity = (MainActivity) context;
                            mainActivity.replaceFragment(new ReleaseASlotConfirmationFragment(), searchBundle, view);
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