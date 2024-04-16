package com.example.parkngo.session.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

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
import com.example.parkngo.session.AddVehicle;
import com.example.parkngo.session.EditVehicle;
import com.example.parkngo.session.SessionOnGoingFragment;
import com.example.parkngo.session.SessionPaymentFragment;
import com.example.parkngo.session.SessionQRFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SessionMainHelper {

    private View sessionMainView;
    private View loadingView;
    private View errorView;
    private Context context;
    boolean is1Selected;
    boolean is2Selected;
    boolean is3Selected;
    Button continueBtn;
    FragmentManager fragmentManager;
    String[] rowVehicleNumberArr = new String[3];


    public SessionMainHelper(View sessionMainView, View loadingView, View errorView, Context context, FragmentManager fragmentManager){
        this.sessionMainView = sessionMainView;
        this.loadingView = loadingView;
        this.errorView = errorView;
        this.context = context;
        this.checkOpenPaymentSessions();
        this.checkOpenParkingSessions();
        continueBtn = sessionMainView.findViewById(R.id.fragment_session_main_continue_btn);
        this.fragmentManager = fragmentManager;
    }


    public void init(){
        this.fetchData();
        this.initVehicleBtnHandlers();
        this.initTapToAddBtnHandlers();
        this.initContinueBtnHandler();
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
            if(statusCode.equals("SUCCESS")){
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

                Bundle data = new Bundle();
                data.putString("MainText1", "Unknown Error");
                data.putString("subText1", "Please try again later");
                data.putInt("img", R.drawable.not_available);
                data.putString("MainText2", "Unknown error occurred! ");
                data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.onBackPressed();
                mainActivity.replaceFragment(new ErrorFragment(), data);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // open parking session check end.......................................


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
            if(statusCode.equals("SUCCESS")){
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
            }else{
                checkOpenParkingSessions();
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

                Bundle data = new Bundle();
                data.putString("MainText1", "Unknown Error");
                data.putString("subText1", "Please try again later");
                data.putInt("img", R.drawable.not_available);
                data.putString("MainText2", "Unknown error occurred! ");
                data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.onBackPressed();
                mainActivity.replaceFragment(new ErrorFragment(), data);

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

            if(statusCode.equals("SUCCESS")) // has stored data
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
                    rowVehicleNumberArr[i] = tempObj.getString("row_vehicle_number");

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

                Bundle data = new Bundle();
                data.putString("MainText1", "Unknown Error");
                data.putString("subText1", "Please try again later");
                data.putInt("img", R.drawable.not_available);
                data.putString("MainText2", "Unknown error occurred! ");
                data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.onBackPressed();
                mainActivity.replaceFragment(new ErrorFragment(), data);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // btn handlers ...............

    public void initVehicleBtnHandlers(){
        is1Selected = false;
        is2Selected = false;
        is3Selected = false;
        Drawable selectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_primary);
        Drawable unselectedDrawable = ContextCompat.getDrawable(context, R.drawable.radius_6_solid);

        ConstraintLayout car1View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_1);
        ConstraintLayout car2View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_2);
        ConstraintLayout car3View = sessionMainView.findViewById(R.id.fragment_session_main_vehicle_3);
        continueBtn.setEnabled(false);

        car1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is1Selected){
                    is1Selected = false;
                    car1View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(false);
                }else{
                    is1Selected = true;
                    is2Selected = false;
                    is3Selected = false;
                    car1View.setBackground(selectedDrawable);
                    car2View.setBackground(unselectedDrawable);
                    car3View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(true);
                }
            }
        });
        car2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is2Selected){
                    is2Selected = false;
                    car2View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(false);
                }else{
                    is1Selected = false;
                    is2Selected = true;
                    is3Selected = false;
                    car1View.setBackground(unselectedDrawable);
                    car2View.setBackground(selectedDrawable);
                    car3View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(true);
                }
            }
        });
        car3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is3Selected){
                    is3Selected = false;
                    car3View.setBackground(unselectedDrawable);
                    continueBtn.setEnabled(false);
                }else{
                    is1Selected = false;
                    is2Selected = false;
                    is3Selected = true;
                    car1View.setBackground(unselectedDrawable);
                    car2View.setBackground(unselectedDrawable);
                    car3View.setBackground(selectedDrawable);
                    continueBtn.setEnabled(true);
                }
            }
        });

        // long taps
        car1View.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView vehicleNameView = car1View.findViewById(R.id.fragment_session_main_1st_vehicle_name);
                TextView vehicleNumberView = car1View.findViewById(R.id.fragment_session_main_1st_vehicle_number);
                TextView vehicleTypeView = car1View.findViewById(R.id.fragment_session_main_1st_vehicle_type);
                String vehicleName = vehicleNameView.getText().toString();
                String vehicleNumber = vehicleNumberView.getText().toString();
                String vehicleType = vehicleTypeView.getText().toString();
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 1);
                data.putString("vehicleName", vehicleName);
                data.putString("vehicleNumber", rowVehicleNumberArr[0]);
                data.putString("vehicleType", vehicleType);
                mainActivity.replaceFragment(new EditVehicle(), data);
                return true;
            }
        });

        car2View.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView vehicleNameView = car2View.findViewById(R.id.fragment_session_main_2nd_vehicle_name);
                TextView vehicleNumberView = car2View.findViewById(R.id.fragment_session_main_2nd_vehicle_number);
                TextView vehicleTypeView = car2View.findViewById(R.id.fragment_session_main_2nd_vehicle_type);
                String vehicleName = vehicleNameView.getText().toString();
                String vehicleNumber = vehicleNumberView.getText().toString();
                String vehicleType = vehicleTypeView.getText().toString();
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 2);
                data.putString("vehicleName", vehicleName);
                data.putString("vehicleNumber", rowVehicleNumberArr[1]);
                data.putString("vehicleType", vehicleType);
                mainActivity.replaceFragment(new EditVehicle(), data);
                return true;
            }
        });

        car3View.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView vehicleNameView = car3View.findViewById(R.id.fragment_session_main_3rd_vehicle_name);
                TextView vehicleNumberView = car3View.findViewById(R.id.fragment_session_main_3rd_vehicle_number);
                TextView vehicleTypeView = car3View.findViewById(R.id.fragment_session_main_3rd_vehicle_type);
                String vehicleName = vehicleNameView.getText().toString();
                String vehicleNumber = vehicleNumberView.getText().toString();
                String vehicleType = vehicleTypeView.getText().toString();
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 3);
                data.putString("vehicleName", vehicleName);
                data.putString("vehicleNumber", rowVehicleNumberArr[2]);
                data.putString("vehicleType", vehicleType);
                mainActivity.replaceFragment(new EditVehicle(), data);
                return true;
            }
        });
    }

    public void initTapToAddBtnHandlers(){
        View tapToAdd1View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_1);
        View tapToAdd2View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_2);
        View tapToAdd3View = sessionMainView.findViewById(R.id.fragment_session_main_tap_to_add_btn_3);

        tapToAdd1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 1);
                mainActivity.replaceFragment(new AddVehicle(), data);
            }
        });

        tapToAdd2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 2);
                mainActivity.replaceFragment(new AddVehicle(), data);
            }
        });
        tapToAdd3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", 3);
                mainActivity.replaceFragment(new AddVehicle(), data);
            }
        });

    }

    public void initContinueBtnHandler(){
        Button continueBtn = sessionMainView.findViewById(R.id.fragment_session_main_continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)context;
                Bundle data = new Bundle();
                data.putInt("selectedVehicle", getSelectedVehicle());
                mainActivity.replaceFragment(new SessionQRFragment(), data);
            }
        });
    }

    public int getSelectedVehicle(){
        if(is1Selected){
            return 1;
        }else if(is2Selected){
            return 2;
        }else{
            return 3;
        }
    }

}
