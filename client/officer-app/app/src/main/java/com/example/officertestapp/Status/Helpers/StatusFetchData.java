package com.example.officertestapp.Status.Helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.R;
import com.example.officertestapp.Status.PSRecycleViewAdapter;
import com.example.officertestapp.Status.ParkingStatusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatusFetchData {

    Context context;
    View view;
    View loadingView;
    public StatusFetchData(Context context, View view, View loadingView){
        this.context = context;
        this.view = view;
        this.loadingView = loadingView;
        this.fetchData();
    }

    public void fetchData(){


        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/view_status/7";

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
//                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
//                String token = parkngoStorage.getData("token");
                Map<String, String> headers = new HashMap<>();
                headers.put("token", "uO3gH9H98UiD5b29ILy7A1YzMDlXZlczZ29jOU5EV216K1B4L1hRYzZZN3pOcjJtdzUxbFk2ZC9LL0FtOTgvTk1sNXkwRjRlRFllMDVTc3d4SDJ3UkVHNTN5ejJXZTVuUHpVTzFBPT0=");
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // response-success handler
    private void successResponseHandler(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            jsonObject = jsonObject.getJSONObject("response");
            JSONObject sessionStatObj = jsonObject.getJSONObject("session_stat");

            TextView freeSlotsView = view.findViewById(R.id.status_main_farg_free_slots);
            TextView filledSlotsView = view.findViewById(R.id.status_main_farg_filled_slots);
            TextView paymentDueSlotsView = view.findViewById(R.id.status_main_farg_payment_due);

            freeSlotsView.setText(sessionStatObj.getString("free_slots"));
            filledSlotsView.setText(sessionStatObj.getString("in_progress"));
            paymentDueSlotsView.setText(sessionStatObj.getString("payment_due"));

            JSONObject paymentDueObj = jsonObject.getJSONObject("payment_due_sessions");
            JSONObject inProgressObj = jsonObject.getJSONObject("in_progress_sessions");


            ArrayList<ParkingStatusModel> parkingStatusModels = new ArrayList<>();


            if(paymentDueObj.getString("is_available").equals("1")){
                JSONArray paymentDueArr = paymentDueObj.getJSONArray("data");

                for(int i=0; i<paymentDueArr.length(); i++){
                    JSONObject tempObj = paymentDueArr.getJSONObject(i);
                    parkingStatusModels.add(new ParkingStatusModel(tempObj.getString("vehicle_number"), tempObj.getString("vehicle_type"), tempObj.getString("session_end_time"), "Payment Due"));
                }
            }

            if(inProgressObj.getString("is_available").equals("1")){
                JSONArray inProgressArr = inProgressObj.getJSONArray("data");

                for(int i=0; i<inProgressArr.length(); i++){
                    JSONObject tempObj = inProgressArr.getJSONObject(i);
                    parkingStatusModels.add(new ParkingStatusModel(tempObj.getString("vehicle_number"), tempObj.getString("vehicle_type"), tempObj.getString("session_start_time"), "In Progress"));
                }
            }

            RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);
            PSRecycleViewAdapter adapter = new PSRecycleViewAdapter(parkingStatusModels, context);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

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

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


//    public void setupParkingStatusModels(){
//        String[] vehicleNumbers = {"CAF 6565 WP", "CAF 6565 WP", "CAF 6565 WP", "CAF 6565 WP", "CAF 6565 WP"};
//        String[] vehicleTypes = {"CAR", "CAR", "CAR", "CAR", "CAR"};
//        String[] dateAndTime = {"07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM", "07 JUNE 2023 | 10 AM"};
//        String[] parkingStatus = {"Payment Due", "In Progress", "Payment Due", "Payment Due", "Payment Due"};
//
//
//        for(int i=0; i<vehicleNumbers.length; i++){
//            parkingStatusModels.add(new ParkingStatusModel(vehicleNumbers[i], vehicleTypes[i], dateAndTime[i], parkingStatus[i]));
//        }
//    }

    // call the setupParkingStatusModels function
//    setupParkingStatusModels();
//
//    // make a reference to the recycleView
//    RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);
//    PSRecycleViewAdapter adapter = new PSRecycleViewAdapter(parkingStatusModels, getContext());
//        recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));