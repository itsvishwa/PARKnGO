package com.example.parkngo.parking.helpers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
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

public class DeleteReviewHelper {

    View view;
    Context context;
    String _id;
    FragmentManager fragmentManager;
    public DeleteReviewHelper(View view, Context context, FragmentManager fragmentManager, String _id){
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this._id = _id;
    }

    public void init(){
        deleteReviewBtnHandler();
        discardBtnHandler();
    }

    private void deleteReviewBtnHandler(){
        Button deleteBtn = view.findViewById(R.id.delete_review_frag_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReview(_id);
            }
        });
    }

    private void discardBtnHandler(){
        Button discardBtn = view.findViewById(R.id.delete_review_frag_discard_btn);
        discardBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                fragmentManager.popBackStack();
            }
        });
    }

    public void deleteReview(String _id){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/review/delete/" + _id;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, apiURL,
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
        try {
            JSONObject jsonResponse = new JSONObject(response);
            Toast.makeText(context, "Review was deleted", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        fragmentManager.popBackStack();
    }


    private void errorResponseHandler(VolleyError error){
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
