package com.example.parkngo.parking.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

public class EditReviewData {
    View editReviewView;
    Context context;
    FragmentManager fragmentManager;

    public EditReviewData(View editReviewView, Context context, FragmentManager fragmentManager){
        this.editReviewView = editReviewView;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void setupDefaultReview(String content, int rating){
        // getting views
        RatingBar ratingBarView = editReviewView.findViewById(R.id.edit_review_frag_rating_bar);
        EditText contentView = editReviewView.findViewById(R.id.edit_review_frag_content);

        contentView.setText(content);
        ratingBarView.setRating(rating);
    }

    public void editReview(String userReviewID){
        // getting reference to the views
        RatingBar ratingBarView = editReviewView.findViewById(R.id.edit_review_frag_rating_bar);
        EditText contentView = editReviewView.findViewById(R.id.edit_review_frag_content);

        if (TextUtils.isEmpty(contentView.getText())|| ratingBarView.getRating() == 0){
            Toast.makeText(context, "Please fill the both fields !", Toast.LENGTH_SHORT).show();
        }else{
            String content = contentView.getText().toString();
            int starCount = (int) ratingBarView.getRating();

            // get the token
            ParkngoStorage parkngoStorage = new ParkngoStorage(context);
            String token = parkngoStorage.getData("token");

            // get the time stamp
            long unixTimestamp = System.currentTimeMillis() / 1000L; // Convert to seconds

            // volley request
            RequestQueue queue = Volley.newRequestQueue(context);
            String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/review/edit";

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
                    params.put("_id", userReviewID);
                    params.put("time_stamp", "" + unixTimestamp);
                    params.put("no_of_stars", "" + starCount);
                    params.put("content", content);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }

    }

    private void successResponseHandler(){
        Toast.makeText(context, "Review is Updated Successfully", Toast.LENGTH_SHORT).show();

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
