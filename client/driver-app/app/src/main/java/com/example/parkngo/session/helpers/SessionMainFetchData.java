package com.example.parkngo.session.helpers;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ErrorFragmentHandler;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.session.EditVehicle;
import com.example.parkngo.session.SessionOnGoingFragment;
import com.example.parkngo.session.SessionPaymentFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SessionMainFetchData {

    private View sessionMainView;
    private View loadingView;
    private View errorView;
    private Context context;

    public SessionMainFetchData(View sessionMainView, View loadingView, View errorView, Context context){
        this.sessionMainView = sessionMainView;
        this.loadingView = loadingView;
        this.errorView = errorView;
        this.context = context;
        this.checkOpenPaymentSessions();
        this.checkOpenParkingSessions();
    }


    // open parking session check
    private void checkOpenParkingSessions(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/driver/view_open_parking_session";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandleOpenParking(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        errorResponseHandlerFetchOpenParking(error);
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

    private void successResponseHandleOpenParking(String response){
        try{
            JSONObject mainJsonObject = new JSONObject(response);
            JSONObject jsonDataObject = mainJsonObject.getJSONObject("response");

            String statusCode = jsonDataObject.getString("status_code");
            if(statusCode.equals("S_D010")){
                JSONObject jsonData = jsonDataObject.getJSONObject("data");
                JSONObject timeDataObj = jsonData.getJSONObject("time_went");
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                SessionOnGoingModel sessionOnGoingModel = new SessionOnGoingModel(
                        jsonData.getString("session_id"),
                        jsonData.getString("parking_name"),
                        jsonData.getString("parking_rate"),
                        jsonData.getString("start_time"),
                        jsonData.getString("vehicle_number"),
                        jsonData.getString("vehicle_type"),
                        jsonData.getString("officer_id"),
                        jsonData.getString("officer_name"),
                        timeDataObj.getString("hours"),
                        timeDataObj.getString("minutes")
                );
                data.putSerializable("sessionOnGoingModelObj", sessionOnGoingModel);
                mainActivity.replaceFragment(new SessionOnGoingFragment(), data);
            }
        }catch(JSONException e){
            throw new RuntimeException(e);
        }
    }

    private void errorResponseHandlerFetchOpenParking(VolleyError error){
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");

                if (response.equals("ERROR_6001")){
                    String appBarMainText = "Something Went Wrong hlo";
                    String appBarSubText = "";
                    int bodyImg = R.drawable.not_available;
                    String bodyMainText = "I don't know what to type here! xD";
                    String bodySubText = "Please try again later, cause we don't know what happened either";

                    ErrorFragmentHandler errorFragmentHandler = new ErrorFragmentHandler(appBarMainText, appBarSubText, bodyImg, bodyMainText, bodySubText, errorView);
                    View newErrorView = errorFragmentHandler.setupView();

                    ViewGroup parent = (ViewGroup) loadingView.getParent();
                    if (parent != null) {
                        int index = parent.indexOfChild(loadingView);
                        parent.removeView(loadingView);
                        parent.addView(newErrorView, index);
                    }
                }else{
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // open parking session check end


    // open payment session check start
    private void checkOpenPaymentSessions(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/driver/view_open_payments";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandleOpenPayment(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        errorResponseHandlerFetchOpenPayment(error);
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
    private void successResponseHandleOpenPayment(String response){
        try{
            JSONObject mainJsonObject = new JSONObject(response);
            JSONObject jsonDataObject = mainJsonObject.getJSONObject("response");
            String statusCode = jsonDataObject.getString("status_code");
            if(statusCode.equals("S_D002")){
                JSONObject jsonData = jsonDataObject.getJSONObject("data");
                JSONObject timeDataObj = jsonData.getJSONObject("time_went");
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                PaymentOnGoingModel paymentOnGoingModel = new PaymentOnGoingModel(
                        jsonData.getString("payment_id"),
                        jsonData.getString("parking_space_name"),
                        jsonData.getString("rate"),
                        jsonData.getString("start_time"),
                        jsonData.getString("end_time"),
                        jsonData.getString("vehicle_number"),
                        jsonData.getString("vehicle_type"),
                        jsonData.getString("officer_id"),
                        jsonData.getString("officer_name"),
                        timeDataObj.getString("hours"),
                        timeDataObj.getString("minutes"),
                        jsonData.getString("amount")
                );
                data.putSerializable("paymentOnGoingModelObj", paymentOnGoingModel);
                mainActivity.replaceFragment(new SessionPaymentFragment(), data);
            }
        }catch(JSONException e){
            throw new RuntimeException(e);
        }
    }

    private void errorResponseHandlerFetchOpenPayment(VolleyError error){
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // open payment session check end
    public void fetchData(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/driver/view_vehicle_info";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandleFetch(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        errorResponseHandlerFetch(error);
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

    private void successResponseHandleFetch(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonDataObject = jsonObject.getJSONObject("response");


            String statusCode = jsonDataObject.getString("status_code");
            int lenOfArr = 0;

            if(statusCode.equals("S_DR_2031")) // has stored data
            {
                // setting user data to corresponding views
                JSONArray jsonDataArr = jsonDataObject.getJSONArray("data");
                lenOfArr = jsonDataArr.length();
                for (int i=0; i < lenOfArr; i++) {
                    JSONObject tempObj = jsonDataArr.getJSONObject(i);
                    String selected = tempObj.getString("selected");
                    String vehicleName = tempObj.getString("vehicle_name");
                    String vehicleNumber = tempObj.getString("vehicle_number");
                    String vehicleType = tempObj.getString("vehicle_type");

                    TextView vehicleNameView;
                    TextView vehicleNumberView;
                    TextView vehicleTypeView;

                    if(selected.equals("1")){
                        vehicleNameView = sessionMainView.findViewById(R.id.fragment_session_main_1st_vehicle_name);
                        vehicleNumberView = sessionMainView.findViewById(R.id.fragment_session_main_1st_vehicle_number);
                        vehicleTypeView = sessionMainView.findViewById(R.id.fragment_session_main_1st_vehicle_type);
                    } else if (selected.equals("2")) {
                        vehicleNameView = sessionMainView.findViewById(R.id.fragment_session_main_2nd_vehicle_name);
                        vehicleNumberView = sessionMainView.findViewById(R.id.fragment_session_main_2nd_vehicle_number);
                        vehicleTypeView = sessionMainView.findViewById(R.id.fragment_session_main_2nd_vehicle_type);
                    }else{
                        vehicleNameView = sessionMainView.findViewById(R.id.fragment_session_main_3rd_vehicle_name);
                        vehicleNumberView = sessionMainView.findViewById(R.id.fragment_session_main_3rd_vehicle_number);
                        vehicleTypeView = sessionMainView.findViewById(R.id.fragment_session_main_3rd_vehicle_type);
                    }

                    vehicleNameView.setText(vehicleName);
                    vehicleNumberView.setText(vehicleNumber);
                    vehicleTypeView.setText(vehicleType);
                }
            }

            ConstraintLayout car1View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_1);
            ConstraintLayout car2View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_2);
            ConstraintLayout car3View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_3);
            ConstraintLayout tapToAdd1View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_1);
            ConstraintLayout tapToAdd2View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_2);
            ConstraintLayout tapToAdd3View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_3);

            // changing the visibility
            if(lenOfArr == 0){
                tapToAdd1View.setVisibility(View.VISIBLE);
                tapToAdd2View.setVisibility(View.VISIBLE);
                tapToAdd3View.setVisibility(View.VISIBLE);
                car1View.setVisibility(View.GONE);
                car2View.setVisibility(View.GONE);
                car3View.setVisibility(View.GONE);
            } else if (lenOfArr == 1) {
                tapToAdd1View.setVisibility(View.GONE);
                tapToAdd2View.setVisibility(View.VISIBLE);
                tapToAdd3View.setVisibility(View.VISIBLE);
                car1View.setVisibility(View.VISIBLE);
                car2View.setVisibility(View.GONE);
                car3View.setVisibility(View.GONE);
            } else if (lenOfArr == 2) {
                tapToAdd1View.setVisibility(View.GONE);
                tapToAdd2View.setVisibility(View.GONE);
                tapToAdd3View.setVisibility(View.VISIBLE);
                car1View.setVisibility(View.VISIBLE);
                car2View.setVisibility(View.VISIBLE);
                car3View.setVisibility(View.GONE);
            }else{
                tapToAdd1View.setVisibility(View.GONE);
                tapToAdd2View.setVisibility(View.GONE);
                tapToAdd3View.setVisibility(View.GONE);
                car1View.setVisibility(View.VISIBLE);
                car2View.setVisibility(View.VISIBLE);
                car3View.setVisibility(View.VISIBLE);
            }

            // Replace the loading view with the parking view
            ViewGroup parent = (ViewGroup) loadingView.getParent();
            if (parent != null) {
                int index = parent.indexOfChild(loadingView);
                parent.removeView(loadingView);
                parent.addView(sessionMainView, index);
            }

        }catch(JSONException e){
            throw new RuntimeException(e);
        }
    }

    // response-error handler
    private void errorResponseHandlerFetch(VolleyError error){
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");

                if (response.equals("ERROR_6001")){
                    String appBarMainText = "Something Went Wrong hlo";
                    String appBarSubText = "";
                    int bodyImg = R.drawable.not_available;
                    String bodyMainText = "I don't know what to type here! xD";
                    String bodySubText = "Please try again later, cause we don't know what happened either";

                    ErrorFragmentHandler errorFragmentHandler = new ErrorFragmentHandler(appBarMainText, appBarSubText, bodyImg, bodyMainText, bodySubText, errorView);
                    View newErrorView = errorFragmentHandler.setupView();

                    ViewGroup parent = (ViewGroup) loadingView.getParent();
                    if (parent != null) {
                        int index = parent.indexOfChild(loadingView);
                        parent.removeView(loadingView);
                        parent.addView(newErrorView, index);
                    }
                }else{
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
