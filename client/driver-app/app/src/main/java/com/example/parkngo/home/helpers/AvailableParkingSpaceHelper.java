package com.example.parkngo.home.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.parkngo.home.ViewMapFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailableParkingSpaceHelper {
    Context context;
    View availableParkingSpaceView;
    View loadingView;
    View errorView;
    String vehicleType;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr;
    ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArrOriginal = new ArrayList<>();
    int pageNumber = 1;
    boolean is_prev_searched = false;
    String prevKeyword;

    public AvailableParkingSpaceHelper(Context context, View availableParkingSpaceView, View loadingView, View errorView, String vehicleType, ArrayList<AvailableParkingSpaceModel> availableParkingSpaceModelsArr){
        this.context = context;
        this.availableParkingSpaceView = availableParkingSpaceView;
        this.loadingView = loadingView;
        this.errorView = errorView;
        this.vehicleType = vehicleType;
        this.availableParkingSpaceModelsArr = availableParkingSpaceModelsArr;
    }


    // initializing
    public void init(){
        layoutFetchData();
        initSearchBarListener();
        initChipGroupBtnListener();
        initRefreshBtnListener();
        initViewMapBtnListener();
        initPrevBtnListener();
        initNextBtnListener();
    }


    private void initPrevBtnListener(){
        Button button = availableParkingSpaceView.findViewById(R.id.aps_frag_prev_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber -= 1;
                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) availableParkingSpaceView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(availableParkingSpaceView);
                    parent.removeView(availableParkingSpaceView);
                    parent.addView(loadingView, index);
                }
                if (is_prev_searched){
                    availableParkingSpaceModelsArr.clear();
                    searchResultFetchData(prevKeyword);
                }else {
                    availableParkingSpaceModelsArr.clear();
                    layoutFetchData();
                }
            }
        });
    }

    private void initNextBtnListener(){
        Button button = availableParkingSpaceView.findViewById(R.id.aps_frag_next_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber += 1;
                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) availableParkingSpaceView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(availableParkingSpaceView);
                    parent.removeView(availableParkingSpaceView);
                    parent.addView(loadingView, index);
                }

                if (is_prev_searched){
                    availableParkingSpaceModelsArr.clear();
                    searchResultFetchData(prevKeyword);
                }else {
                    availableParkingSpaceModelsArr.clear();
                    layoutFetchData();
                }

            }
        });
    }


    // initializing view-map btn listener
    private void initViewMapBtnListener() {
        Button button = availableParkingSpaceView.findViewById(R.id.available_parking_space_frag_view_map_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // switching to loading view
                ViewGroup parent = (ViewGroup) availableParkingSpaceView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(availableParkingSpaceView);
                    parent.removeView(availableParkingSpaceView);
                    parent.addView(loadingView, index);
                }
                viewMapDataFetch();
            }
        });
    }

    private void viewMapDataFetch(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/get_map_data/" + vehicleType;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandlerVMD(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorResponseHandlerLFD(error);
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

    // response-success handler - VMD (View Map data)
    private void successResponseHandlerVMD(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultDataArr = jsonObject.getJSONArray("response");


            ArrayList<AvailableParkingSpaceModel> newAvailableParkingSpaceModelsArr = new ArrayList<>();
            for (int i=0; i<resultDataArr.length(); i++){
                JSONObject dataObj = resultDataArr.getJSONObject(i);
                String parkingID = dataObj.getString("_id");
                String name = dataObj.getString("name");
                String address = dataObj.getString("address");
                String latitude = dataObj.getString("latitude");
                String longitude = dataObj.getString("longitude");
                int free_slots = Integer.parseInt(dataObj.getString("free_slots"));
                String total_slots = dataObj.getString("total_slots");
                int rate = Integer.parseInt(dataObj.getString("rate"));
                newAvailableParkingSpaceModelsArr.add(new AvailableParkingSpaceModel(parkingID, name, address, free_slots, total_slots, rate, "0", 0, "0", 0.0, latitude, longitude));
            }

            MainActivity mainActivity = (MainActivity) context;
            Bundle data = new Bundle();
            data.putSerializable("availableParkingSpaceModelsArr", newAvailableParkingSpaceModelsArr);
            data.putString("vehicleType", vehicleType);
            mainActivity.replaceFragment(new ViewMapFragment(), data);

        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    // fetching layout data to render the view
    private void layoutFetchData(){

        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/view_available/" + vehicleType + "/6.919875/79.854209/" + pageNumber;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandlerLFD(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorResponseHandlerLFD(error);
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


    // response-success handler - LFD (layout fetch data)
    private void successResponseHandlerLFD(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject resultDataObj = jsonObject.getJSONObject("response");
            JSONArray resultDataArr = resultDataObj.getJSONArray("data");

            for (int i=0; i<resultDataArr.length(); i++){
                JSONObject dataObj = resultDataArr.getJSONObject(i);
                String parkingID = dataObj.getString("_id");
                String name = dataObj.getString("name");
                String address = dataObj.getString("address");
                String latitude = dataObj.getString("latitude");
                String longitude = dataObj.getString("longitude");
                String publicOrPrivate = dataObj.getString("is_public").equals("1") ? "Public" : "Customer Only";
                double distance = dataObj.getDouble("distance");
                int free_slots = Integer.parseInt(dataObj.getString("free_slots"));
                String total_slots = dataObj.getString("total_slots");
                int rate = Integer.parseInt(dataObj.getString("rate"));
                int avg_star_count = Integer.parseInt(dataObj.getString("avg_star_count"));
                String total_review_count = "( " + dataObj.getString("total_review_count") + " )";
                availableParkingSpaceModelsArr.add(new AvailableParkingSpaceModel(parkingID, name, address, free_slots, total_slots, rate, publicOrPrivate, avg_star_count, total_review_count, distance, latitude, longitude));
            }

            availableParkingSpaceModelsArrOriginal.clear();
            availableParkingSpaceModelsArrOriginal.addAll(availableParkingSpaceModelsArr);


            // updating page number view
            TextView pageNumberView = availableParkingSpaceView.findViewById(R.id.aps_frag_page_number);
            pageNumberView.setText("Page " + pageNumber);

            //updating next and prev buttons
            String is_next_available = resultDataObj.getString("is_next_available");
            Button nextBtn = availableParkingSpaceView.findViewById(R.id.aps_frag_next_btn);
            Button prevBtn = availableParkingSpaceView.findViewById(R.id.aps_frag_prev_btn);

            Drawable selectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_primary);
            Drawable unselectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_solid);

            if(is_next_available.equals("0")){
                nextBtn.setEnabled(false);
                nextBtn.setBackground(unselectedDrawable);
            }else{
                nextBtn.setEnabled(true);
                nextBtn.setBackground(selectedDrawable);
            }
            if(pageNumber == 1){
                prevBtn.setEnabled(false);
                prevBtn.setBackground(unselectedDrawable);
            }else{
                prevBtn.setEnabled(true);
                prevBtn.setBackground(selectedDrawable);
            }

            // setting up the available parking spaces recycle view
            RecyclerView recyclerView = availableParkingSpaceView.findViewById(R.id.ava_recycle_view);
            APSRecycleViewAdapter adapter = new APSRecycleViewAdapter(context, availableParkingSpaceModelsArr);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Replace the loading view with the parking view
            ViewGroup parent = (ViewGroup) loadingView.getParent();
            if (parent != null) {
                int index = parent.indexOfChild(loadingView);
                parent.removeView(loadingView);
                parent.addView(availableParkingSpaceView, index);
            }
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    // response-error handler - LFD (layout fetch data)
    private void errorResponseHandlerLFD(VolleyError error){
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");

                Bundle data = new Bundle();

                if(response.equals("PS_NOPS")) // PS_NOPS => means no parking available
                {
                    data.putString("MainText1", "No Available Parking Spaces");
                    data.putString("subText1", "Please try again later");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "Parking spaces not available for selected vehicle type!");
                    data.putString("subText2", "Sorry, no parking slots are currently available. Please try again later or consider alternative parking options");
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


    // initializing search bar listener
    private void initSearchBarListener(){
        SearchView searchView = availableParkingSpaceView.findViewById(R.id.available_parking_spaces_frag_search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) availableParkingSpaceView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(availableParkingSpaceView);
                    parent.removeView(availableParkingSpaceView);
                    parent.addView(loadingView, index);
                }
                pageNumber = 1;
                prevKeyword = query;

                searchResultFetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }


    // fetching search result data
    private void searchResultFetchData(String keyword){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/search_available/" + vehicleType + "/" + keyword + "/6.919875/79.854209/" + pageNumber;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandlerSRD(response);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorResponseHandlerSRD(error);
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


    // response-error handler - SRD (Search Result Fetch Data)
    private void errorResponseHandlerSRD(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");

                Bundle data = new Bundle();

                if(response.equals("PS_NOPS")) // PS_NOPS => means no parking available
                {
                    data.putString("MainText1", "No Available Parking Spaces");
                    data.putString("subText1", "Please try again later");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "Parking spaces not available for selected vehicle type!");
                    data.putString("subText2", "Sorry, no parking slots are currently available. Please try again later or consider alternative parking options");
                }else{
                    data.putString("MainText1", "Unknown Error");
                    data.putString("subText1", "Please try again later");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "Unknown error occurred! ");
                    data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");
                }
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new ErrorFragment(), data);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // response-success handler - SRD (Search Result Fetch Data)
    private void successResponseHandlerSRD( String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject resultDataObj = jsonObject.getJSONObject("response");
            JSONArray resultDataArr = resultDataObj.getJSONArray("data");

            availableParkingSpaceModelsArr.clear();

            for (int i=0; i<resultDataArr.length(); i++){
                JSONObject dataObj = resultDataArr.getJSONObject(i);
                String parkingID = dataObj.getString("_id");
                String name = dataObj.getString("name");
                String address = dataObj.getString("address");
                String latitude = dataObj.getString("latitude");
                String longitude = dataObj.getString("longitude");
                String publicOrPrivate = dataObj.getString("is_public").equals("1") ? "Public" : "Customer Only";
                int free_slots = Integer.parseInt(dataObj.getString("free_slots"));
                String total_slots = dataObj.getString("total_slots");
                int rate = Integer.parseInt(dataObj.getString("rate"));
                int avg_star_count = Integer.parseInt(dataObj.getString("avg_star_count"));
                double distance = dataObj.getDouble("distance");
                String total_review_count = "( " + dataObj.getString("total_review_count") + " )";
                availableParkingSpaceModelsArr.add(new AvailableParkingSpaceModel(parkingID, name, address, free_slots, total_slots, rate, publicOrPrivate, avg_star_count, total_review_count, distance, latitude, longitude));
            }

            is_prev_searched = true;
            // updating page number view
            TextView pageNumberView = availableParkingSpaceView.findViewById(R.id.aps_frag_page_number);
            pageNumberView.setText("Page " + pageNumber);

            //updating next and prev buttons
            String is_next_available = resultDataObj.getString("is_next_available");
            Button nextBtn = availableParkingSpaceView.findViewById(R.id.aps_frag_next_btn);
            Button prevBtn = availableParkingSpaceView.findViewById(R.id.aps_frag_prev_btn);

            Drawable selectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_primary);
            Drawable unselectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_solid);

            if(is_next_available.equals("0")){
                nextBtn.setEnabled(false);
                nextBtn.setBackground(unselectedDrawable);
            }else{
                nextBtn.setEnabled(true);
                nextBtn.setBackground(selectedDrawable);
            }
            if(pageNumber == 1){
                prevBtn.setEnabled(false);
                prevBtn.setBackground(unselectedDrawable);
            }else{
                prevBtn.setEnabled(true);
                prevBtn.setBackground(selectedDrawable);
            }

            // setting up the available parking spaces recycle view
            RecyclerView recyclerView = availableParkingSpaceView.findViewById(R.id.ava_recycle_view);
            APSRecycleViewAdapter adapter = new APSRecycleViewAdapter(context, availableParkingSpaceModelsArr);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Replace the loading view with the parking view
            ViewGroup parent = (ViewGroup) loadingView.getParent();
            if (parent != null) {
                int index = parent.indexOfChild(loadingView);
                parent.removeView(loadingView);
                parent.addView(availableParkingSpaceView, index);
            }
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    // initializing chip group listener
    private void initChipGroupBtnListener(){
        ChipGroup chipGroup = availableParkingSpaceView.findViewById(R.id.available_parking_spaces_frag_chip_group);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                boolean maxFreeSlots = false;
                boolean isPublic = false;
                boolean minRate = false;

                for(int i=0; i<checkedIds.size(); i++)
                {
                    Chip chipView = availableParkingSpaceView.findViewById(checkedIds.get(i));
                    if(chipView.getText().toString().equals("Free Slots: High to Low")){
                        maxFreeSlots = true;
                    }
                    else if(chipView.getText().toString().equals("Public Parking Spaces")){
                        isPublic = true;
                    }
                    else if(chipView.getText().toString().equals("Rate: Low to High")){
                        minRate = true;
                    }
                }

                sortParkingSpaceData(maxFreeSlots, isPublic, minRate);
            }
        });
    }


    // sorting model arr
    private void sortParkingSpaceData(Boolean maxFreeSlots, Boolean isPublic, Boolean minRate){
            ArrayList<AvailableParkingSpaceModel> newAvailableParkingSpacesModelArr = new ArrayList<>(availableParkingSpaceModelsArr);
            if(maxFreeSlots){
                Collections.sort(newAvailableParkingSpacesModelArr, Comparator.comparingInt(AvailableParkingSpaceModel::getFreeSlots).reversed());
            }
            if (isPublic) {
                newAvailableParkingSpacesModelArr.removeIf(model -> !"Public".equals(model.getParkingType()));
            }
            if(minRate){
                Collections.sort(newAvailableParkingSpacesModelArr, Comparator.comparingDouble(AvailableParkingSpaceModel::getRate));
            }

            // setting up the available parking spaces recycle view
            RecyclerView recyclerView = availableParkingSpaceView.findViewById(R.id.ava_recycle_view);
            APSRecycleViewAdapter adapter = new APSRecycleViewAdapter(availableParkingSpaceView.getContext(), newAvailableParkingSpacesModelArr);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(availableParkingSpaceView.getContext()));
    }


    // initializing refresh btn listener
    private void initRefreshBtnListener(){
        Button button = availableParkingSpaceView.findViewById(R.id.available_parking_space_frag_refresh_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to loading view until the data is get fetched
                ViewGroup parent = (ViewGroup) availableParkingSpaceView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(availableParkingSpaceView);
                    parent.removeView(availableParkingSpaceView);
                    parent.addView(loadingView, index);
                }
                pageNumber = 1;
                is_prev_searched = false;
                availableParkingSpaceModelsArr.clear();
                layoutFetchData();
            }
        });
    }
}
