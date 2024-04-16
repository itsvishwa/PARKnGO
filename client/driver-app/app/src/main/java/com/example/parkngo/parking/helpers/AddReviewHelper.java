package com.example.parkngo.parking.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddReviewHelper {
    View view;
    String parkingID;
    Context context;
    FragmentManager fragmentManager;

    public AddReviewHelper(View view, String parkingID, Context context, FragmentManager fragmentManager ){
        this.view = view;
        this.parkingID = parkingID;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void init(){
        addReviewBtnListener();
    }

    private void addReviewBtnListener(){
        Button reviewAddBtn = view.findViewById(R.id.add_review_frag_add_review_btn);
        reviewAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
            }
        });
    }

    private void addReview(){
        // getting reference to the views
        TextView contentView = view.findViewById(R.id.add_review_frag_content);
        RatingBar ratingBar = view.findViewById(R.id.add_review_frag_rating_bar);

        if (TextUtils.isEmpty(contentView.getText())|| ratingBar.getRating() == 0){
            Toast.makeText(context, "Please fill the both fields !", Toast.LENGTH_SHORT).show();
        }else{
            String content = contentView.getText().toString();
            int starCount = (int) ratingBar.getRating();

            // get the token
            ParkngoStorage parkngoStorage = new ParkngoStorage(context);
            String token = parkngoStorage.getData("token");

            // get the time stamp
            long unixTimestamp = System.currentTimeMillis() / 1000L; // Convert to seconds

            // volley request
            RequestQueue queue = Volley.newRequestQueue(context);
            String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/review/add";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, apiURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            successResponseHandler();
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
                    return headers;
                }
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("time_stamp", "" + unixTimestamp);
                    params.put("no_of_stars", "" + starCount);
                    params.put("content", content);
                    params.put("parking_id", "" + parkingID);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }

    }

    private void successResponseHandler(){
        Toast.makeText(context, "Review Added Successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to the previous fragment
        fragmentManager.popBackStack();
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                Toast.makeText(context, jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
