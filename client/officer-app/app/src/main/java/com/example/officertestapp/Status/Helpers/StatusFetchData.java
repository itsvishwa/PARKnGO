package com.example.officertestapp.Status.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.officertestapp.Helpers.ParkngoStorage;
import com.example.officertestapp.HeroActivity;
import com.example.officertestapp.MainActivity;
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
    ParkngoStorage parkngoStorage;

    public StatusFetchData(Context context, View view, View loadingView){
        this.context = context;
        this.view = view;
        this.loadingView = loadingView;
        this.parkngoStorage = new ParkngoStorage(context);
        this.fetchData("all", "all");
    }

    public void fetchData(String vehicleType, String statusType){
        RequestQueue queue = Volley.newRequestQueue(context);

        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/session/view_status/" + vehicleType + "/" + statusType;

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
                String token = parkngoStorage.getData("token");
                String parkingID = parkngoStorage.getData("parkingID");
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                headers.put("X-Encoded-Data", parkingID);
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

            String responseCode = jsonObject.getString("response_code");

            JSONObject sessionStatObj = jsonObject.getJSONObject("session_stat");

            TextView paymentDueSlotsView = view.findViewById(R.id.status_main_farg_payment_due);

            paymentDueSlotsView.setText(sessionStatObj.getString("payment_due"));

            // setting vehicle slot data
            JSONObject slotData = sessionStatObj.getJSONObject("slot_status");
            JSONObject typeAData = slotData.getJSONObject("A");
            JSONObject typeBData = slotData.getJSONObject("B");
            JSONObject typeCData = slotData.getJSONObject("C");
            JSONObject typeDData = slotData.getJSONObject("D");

            if (typeAData.getString("availability").equals("1")) // means data are available for this vehicle type
            {
                JSONObject dataObj = typeAData.getJSONObject("data");

                TextView freeSlotView = view.findViewById(R.id.sm_frag_A_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_A_filed_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_A_vehicle_rate);

                freeSlotView.setText(dataObj.getString("free_slots"));
                filledSlotView.setText(dataObj.getString("total_slots"));
                rateView.setText("Rs. " + dataObj.getString("rate") + ".00");
            }else {
                TextView freeSlotView = view.findViewById(R.id.sm_frag_A_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_A_filed_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_A_vehicle_rate);
                TextView textView = view.findViewById(R.id.sm_frag_A_text); // "free out of" text
                TextView titleView = view.findViewById(R.id.sm_frag_A_vehicle_type); // vehicle type text

                freeSlotView.setVisibility(View.GONE);
                filledSlotView.setVisibility(View.GONE);
                rateView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                titleView.setVisibility(View.GONE);
            }

            if (typeBData.getString("availability").equals("1")) // means data are available for this vehicle type
            {
                JSONObject dataObj = typeBData.getJSONObject("data");

                TextView freeSlotView = view.findViewById(R.id.sm_frag_B_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_B_filled_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_B_vehicle_rate);

                freeSlotView.setText(dataObj.getString("free_slots"));
                filledSlotView.setText(dataObj.getString("total_slots"));
                rateView.setText("Rs. " + dataObj.getString("rate") + ".00");
            }else {
                TextView freeSlotView = view.findViewById(R.id.sm_frag_B_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_B_filled_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_B_vehicle_rate);
                TextView textView = view.findViewById(R.id.sm_frag_B_text); // "free out of" text
                TextView titleView = view.findViewById(R.id.sm_frag_B_vehicle_type); // vehicle type text

                freeSlotView.setVisibility(View.GONE);
                filledSlotView.setVisibility(View.GONE);
                rateView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                titleView.setVisibility(View.GONE);
            }

            if (typeCData.getString("availability").equals("1")) // means data are available for this vehicle type
            {
                JSONObject dataObj = typeCData.getJSONObject("data");

                TextView freeSlotView = view.findViewById(R.id.sm_frag_C_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_C_filled_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_C_vehicle_rate);

                freeSlotView.setText(dataObj.getString("free_slots"));
                filledSlotView.setText(dataObj.getString("total_slots"));
                rateView.setText("Rs. " + dataObj.getString("rate") + ".00");
            }else {
                TextView freeSlotView = view.findViewById(R.id.sm_frag_C_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_C_filled_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_C_vehicle_rate);
                TextView textView = view.findViewById(R.id.sm_frag_C_text); // "free out of" text
                TextView titleView = view.findViewById(R.id.sm_frag_C_vehicle_type); // vehicle type text

                freeSlotView.setVisibility(View.GONE);
                filledSlotView.setVisibility(View.GONE);
                rateView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                titleView.setVisibility(View.GONE);
            }

            if (typeDData.getString("availability").equals("1")) // means data are available for this vehicle type
            {
                JSONObject dataObj = typeDData.getJSONObject("data");

                TextView freeSlotView = view.findViewById(R.id.sm_frag_D_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_D_filled_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_D_vehicle_rate);

                freeSlotView.setText(dataObj.getString("free_slots"));
                filledSlotView.setText(dataObj.getString("total_slots"));
                rateView.setText("Rs. " + dataObj.getString("rate") + ".00");
            }else {
                TextView freeSlotView = view.findViewById(R.id.sm_frag_D_free_slots);
                TextView filledSlotView = view.findViewById(R.id.sm_frag_D_filled_slots);
                TextView rateView = view.findViewById(R.id.sm_frag_D_vehicle_rate);
                TextView textView = view.findViewById(R.id.sm_frag_D_text); // "free out of" text
                TextView titleView = view.findViewById(R.id.sm_frag_D_vehicle_type); // vehicle type text

                freeSlotView.setVisibility(View.GONE);
                filledSlotView.setVisibility(View.GONE);
                rateView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                titleView.setVisibility(View.GONE);
            }


            JSONObject paymentDueObj = jsonObject.getJSONObject("payment_due_sessions");
            JSONObject inProgressObj = jsonObject.getJSONObject("in_progress_sessions");


            ArrayList<ParkingStatusModel> parkingStatusModels = new ArrayList<>();


            if(paymentDueObj.getString("is_available").equals("1")){
                RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);
                recyclerView.setVisibility(View.VISIBLE);
                ConstraintLayout constraintLayout = view.findViewById(R.id.status_frag_empty_list_view);
                constraintLayout.setVisibility(View.GONE);
                JSONArray paymentDueArr = paymentDueObj.getJSONArray("data");

                for(int i=0; i<paymentDueArr.length(); i++){
                    JSONObject tempObj = paymentDueArr.getJSONObject(i);
                    parkingStatusModels.add(new ParkingStatusModel(tempObj.getString("_id"), tempObj.getString("vehicle_number"), tempObj.getString("vehicle_type"), tempObj.getString("session_end_time"), "Payment Due"));
                }
            }


            if(inProgressObj.getString("is_available").equals("1")){
                RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);
                recyclerView.setVisibility(View.VISIBLE);
                ConstraintLayout constraintLayout = view.findViewById(R.id.status_frag_empty_list_view);
                constraintLayout.setVisibility(View.GONE);
                JSONArray inProgressArr = inProgressObj.getJSONArray("data");

                for(int i=0; i<inProgressArr.length(); i++){
                    JSONObject tempObj = inProgressArr.getJSONObject(i);
                    parkingStatusModels.add(new ParkingStatusModel(tempObj.getString("_id"), tempObj.getString("vehicle_number"), tempObj.getString("vehicle_type"), tempObj.getString("session_start_time"), "In Progress"));
                }
            }

            // empty list
            if(paymentDueObj.getString("is_available").equals("0") && inProgressObj.getString("is_available").equals("0")){
                RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);
                recyclerView.setVisibility(View.GONE);
                ConstraintLayout constraintLayout = view.findViewById(R.id.status_frag_empty_list_view);
                constraintLayout.setVisibility(View.VISIBLE);
            }


            RecyclerView recyclerView = view.findViewById(R.id.status_frag_recycle_view);
            PSRecycleViewAdapter adapter = new PSRecycleViewAdapter(parkingStatusModels, context, view);
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