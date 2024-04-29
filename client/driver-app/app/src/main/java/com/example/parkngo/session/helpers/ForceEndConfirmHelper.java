package com.example.parkngo.session.helpers;



import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.example.parkngo.session.ForceEndSucessfullFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForceEndConfirmHelper {
    View forceEndConfirmView;
    double latitude;
    double longitude;

    private LocationManager locationManager;
    Context context;


    public ForceEndConfirmHelper(Context context, View forceEndConfirmView){
        this.context = context;
        this.forceEndConfirmView = forceEndConfirmView;
    }


    public void getLocationAndSendReq() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Get latitude and longitude from location object
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                System.out.println(longitude  + "       " + latitude);
                locationManager.removeUpdates(this);

                sendForceEndReq();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    private void sendForceEndReq(){
        RequestQueue queue = Volley.newRequestQueue(context);
        // String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/force_end/" + latitude + "/" +longitude; // uncomment for true GPS
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/force_end/" + "6.900662" + "/" + "79.8617228";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successForceEndReq(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        errorForceEndReq(error);
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
        queue.add(stringRequest);
    }


    private void successForceEndReq(String response){
//        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        MainActivity mainActivity  = (MainActivity) context;
        mainActivity.replaceFragment(new ForceEndSucessfullFragment());
    }


    private void errorForceEndReq(VolleyError error){
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                Bundle data = new Bundle();

                if (response.equals("SE_SIDPA")){
                    data.putString("MainText1", "Operation Failed");
                    data.putString("subText1", "you should be outside of the 200m range parking space area");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "Operation Failed");
                    data.putString("subText2", "You didn't have leave the designated parking space area");
                }else{
                    data.putString("MainText1", "Unknown Error");
                    data.putString("subText1", "Please try again later");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "Unknown error occurred! ");
                    data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");
                }
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.onBackPressed();
                mainActivity.replaceFragment(new ErrorFragment(), data);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}