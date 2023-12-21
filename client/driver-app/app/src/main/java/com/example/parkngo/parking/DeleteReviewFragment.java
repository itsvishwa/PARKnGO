package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

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

public class DeleteReviewFragment extends Fragment {

    private String _id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_review, container, false);

        // get the token
        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());
        String token = parkngoStorage.getData("token");

        if (getArguments() != null) {
            _id = getArguments().getString("_id", "-1");
        }

        // set button listeners - delete review btn
        Button deleteBtn = view.findViewById(R.id.delete_review_frag_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/review/delete/" + _id;

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, apiURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    Toast.makeText(getContext(), jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                // Navigate back to the previous fragment
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                fragmentManager.popBackStack();
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                String errorResponse;
                                if (error.networkResponse != null && error.networkResponse.data != null) {
                                    errorResponse = new String(error.networkResponse.data);
                                    try {
                                        JSONObject jsonResponse = new JSONObject(errorResponse);
                                        Toast.makeText(getContext(), jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
                                        // Handle specific errors
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        // Add your custom header key-value pair here
                        headers.put("token", token);
                        return headers;
                    }
                };
                queue.add(stringRequest);
            }
        });


        // set button listeners - discard button
        Button discardBtn = view.findViewById(R.id.delete_review_frag_discard_btn);

        discardBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}