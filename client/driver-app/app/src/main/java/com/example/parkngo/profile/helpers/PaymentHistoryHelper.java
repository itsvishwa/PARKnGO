package com.example.parkngo.profile.helpers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ErrorFragment;
import com.example.parkngo.helpers.ParkngoStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentHistoryHelper {

    View view;
    View loadingView;
    View errorView;
    Context context;
    MainActivity mainActivity;

    public PaymentHistoryHelper(View view, View loadingView, View errorView, Context context, MainActivity mainActivity){
        this.view = view;
        this.loadingView = loadingView;
        this.context = context;
        this.errorView = errorView;
        this.mainActivity = mainActivity;
    }

    public void init(){
        fetchData();
    }

    private void fetchData(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/profile/driver_payment_history";


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
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                return headers;
            }
        };

        queue.add(stringRequest);
    }


    private void successResponseHandler(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultDataArr = jsonObject.getJSONArray("response");

            ArrayList<PaymentHistoryModel> paymentHistoryModels = new ArrayList<>();

            for(int i=0; i<resultDataArr.length(); i++){
                JSONObject resultData = resultDataArr.getJSONObject(i);
                String amount = resultData.getString("amount");
                String paymentMethod = resultData.getString("payment_method");
                String timeDuration = resultData.getString("time_duration");
                String vehicleType = resultData.getString("vehicle_type");
                String vehicleNumber = resultData.getString("vehicle_number");
                String parkingSpaceName = resultData.getString("parking_space_name");
                String paymentTimeStamp = resultData.getString("payment_time_stamp");

                paymentHistoryModels.add(new PaymentHistoryModel(paymentTimeStamp, amount, timeDuration, vehicleType, vehicleNumber, parkingSpaceName, paymentMethod));

                // setting up the available parking spaces recycle view
                RecyclerView recyclerView = view.findViewById(R.id.payment_frag_recycle_view);
                PHRecycleViewAdapter adapter = new PHRecycleViewAdapter(context, paymentHistoryModels);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

                // Replace the loading view with the parking view
                ViewGroup parent = (ViewGroup) loadingView.getParent();
                if (parent != null) {
                    int index = parent.indexOfChild(loadingView);
                    parent.removeView(loadingView);
                    parent.addView(view, index);
                }
            }
        }catch(JSONException e){
            throw new RuntimeException(e);
        }
    }


    private void errorResponseHandler(VolleyError error){
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");

                if (response.equals("PRF_NPY")){
                    Bundle data = new Bundle();
                    data.putString("MainText1", "No Payments Yet!");
                    data.putString("subText1", "Please try again later");
                    data.putInt("img", R.drawable.not_available);
                    data.putString("MainText2", "You haven't made any payments yet");
                    data.putString("subText2", "Once you made it, the payment details will display here");

                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.onBackPressed();
                    mainActivity.replaceFragment(new ErrorFragment(), data);
                }else{
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
