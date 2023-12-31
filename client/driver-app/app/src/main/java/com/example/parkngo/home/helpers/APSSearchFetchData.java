package com.example.parkngo.home.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ErrorFragmentHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APSSearchFetchData {

    View view;
    View loadingView;
    View errorView;
    Context context;
    String vehicleType;
    String keyword;
    MainActivity mainActivity;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr;

    public APSSearchFetchData(View view, View loadingView, View errorView, Context context, String vehicleType, String keyword, MainActivity mainActivity, ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr){
        this.view = view;
        this.loadingView = loadingView;
        this.errorView = errorView;
        this.context = context;
        this.vehicleType = vehicleType;
        this.keyword = keyword;
        this.mainActivity = mainActivity;
        this.availableParkingSpaceModelsArr = availableParkingSpaceModelsArr;
        fetchData();
    }


    // call the API and fetch data
    private void fetchData(){

        RequestQueue queue = Volley.newRequestQueue(context);

        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/search_available/" + vehicleType + "/" + keyword;

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
        );

        queue.add(stringRequest);
    }


    // response-success handler
    private void successResponseHandler(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultDataArr = jsonObject.getJSONArray("response");

            availableParkingSpaceModelsArr.clear();

            for (int i=0; i<resultDataArr.length(); i++){
                JSONObject dataObj = resultDataArr.getJSONObject(i);
                String _id = dataObj.getString("_id");
                String name = dataObj.getString("name");
                String address = dataObj.getString("address");
                String latitude = dataObj.getString("latitude");
                String longitude = dataObj.getString("longitude");
                String publicOrPrivate = dataObj.getString("is_public").equals("1") ? "Public" : "Customer Only";
                int free_slots = Integer.parseInt(dataObj.getString("free_slots"));
                String total_slots = dataObj.getString("total_slots");
                int rate = Integer.parseInt(dataObj.getString("rate"));
                int avg_star_count = Integer.parseInt(dataObj.getString("avg_star_count"));
                String total_review_count = "( " + dataObj.getString("total_review_count") + " )";
                availableParkingSpaceModelsArr.add(new AvailableParkingSpaceModel(name, free_slots, total_slots, rate, publicOrPrivate, avg_star_count, total_review_count, 450.5, latitude, longitude));
            }

            // setting up the available parking spaces recycle view
            RecyclerView recyclerView = view.findViewById(R.id.ava_recycle_view);
            APSRecycleViewAdapter adapter = new APSRecycleViewAdapter(context, availableParkingSpaceModelsArr);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Replace the loading view with the parking view
            ViewGroup parent = (ViewGroup) loadingView.getParent();
            if (parent != null) {
                int index = parent.indexOfChild(loadingView);
                parent.removeView(loadingView);
                parent.addView(view, index);
            }
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    // response-error handler
    private void errorResponseHandler(VolleyError error){
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                if(response.equals("0")) // 0 => means no parking available
                {
                    String appBarMainText = "No Available Parking Spaces";
                    String appBarSubText = "Please try again later";
                    int bodyImg = R.drawable.not_available;
                    String bodyMainText = "Parking spaces not available for selected vehicle type!";
                    String bodySubText = "Sorry, no parking slots are currently available. Please try again later or consider alternative parking options";

                    ErrorFragmentHandler errorFragmentHandler = new ErrorFragmentHandler(appBarMainText, appBarSubText, bodyImg, bodyMainText, bodySubText, errorView);
                    View newErrorView = errorFragmentHandler.setupView();

                    ViewGroup parent = (ViewGroup) loadingView.getParent();
                    if (parent != null) {
                        int index = parent.indexOfChild(loadingView);
                        parent.removeView(loadingView);
                        parent.addView(newErrorView, index);
                    }
                }else{
                    Toast.makeText(context, jsonResponse.getString("response"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
