package com.example.parkngo.session.helpers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ErrorFragment;
import com.example.parkngo.helpers.ParkngoStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SessionQRHelper {

    Context context;
    int selected;
    View sessionQRView;
    View loadingView;
    View errorView;

    public SessionQRHelper(Context context, int selected, View sessionQRView, View loadinView, View errorView){
        this.context = context;
        this.selected = selected;
        this.sessionQRView = sessionQRView;
        this.loadingView = loadinView;
        this.errorView = errorView;
    }

    public void init(){
        RequestQueue queue = Volley.newRequestQueue(context);

        String apiUrl = "http://192.168.56.1/PARKnGO/server/mobile/qr/get_qr_code/" + selected;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                // status code 200
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String responseJSON = jsonResponse.getString("response");

                            // load the qr
                            ImageView qrImageView = sessionQRView.findViewById(R.id.fragment_session_qr_imageView);
                            Glide.with(context).load("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + responseJSON).into(qrImageView);

                            // replace the loading view
                            ViewGroup parent = (ViewGroup) loadingView.getParent();
                            if (parent != null) {
                                int index = parent.indexOfChild(loadingView);
                                parent.removeView(loadingView);
                                parent.addView(sessionQRView, index);
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                // status code 400
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorResponse;
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorResponse = new String(error.networkResponse.data);
                            try {
                                JSONObject jsonResponse = new JSONObject(errorResponse);
                                String response = jsonResponse.getString("response");

                                Bundle data = new Bundle();

                                data.putString("MainText1", "Unknown Error");
                                data.putString("subText1", "Please try again later");
                                data.putInt("img", R.drawable.not_available);
                                data.putString("MainText2", "Unknown error occurred! ");
                                data.putString("subText2", "We sincerely apologize for the inconvenience this has caused.");

                                MainActivity mainActivity = (MainActivity) context;
                                mainActivity.replaceFragment(new ErrorFragment(), data);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // get the token
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                headers.put("token", token);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
