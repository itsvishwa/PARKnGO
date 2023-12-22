package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class AddReviewFragment extends Fragment {
    int _id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_review, container, false);
        // Retrieve data from arguments

        if (getArguments() != null) {
            _id = getArguments().getInt("_id", -1);
        }
        Toast.makeText(getContext(), "" + _id, Toast.LENGTH_SHORT).show();

        // getting references to the button
        Button  reviewAddBtn = view.findViewById(R.id.add_review_frag_add_review_btn);

        reviewAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting reference to the views
                TextView contentView = view.findViewById(R.id.add_review_frag_content);
                RatingBar ratingBar = view.findViewById(R.id.add_review_frag_rating_bar);

                // has to get the unix time stamp

                if (TextUtils.isEmpty(contentView.getText())|| ratingBar.getRating() == 0){
                    Toast.makeText(getContext(), "Please fill the both fields" + contentView.getText() + "!", Toast.LENGTH_SHORT).show();
                }else{
                    String content = contentView.getText().toString();
                    int starCount = (int) ratingBar.getRating();

                    // get the token
                    ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());
                    String token = parkngoStorage.getData("token");

                    // get the time stamp
                    long unixTimestamp = System.currentTimeMillis() / 1000L; // Convert to seconds

                    // volley request
                    RequestQueue queue = Volley.newRequestQueue(requireContext());
                    String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/review/add";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, apiURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getContext(), "Review Added Successfully", Toast.LENGTH_SHORT).show();

                                    // Navigate back to the previous fragment
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    fragmentManager.popBackStack();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error response
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
                    ){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            // Add your custom header key-value pair here
                            headers.put("token", token);
                            return headers;
                        }

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("time_stamp", "" + unixTimestamp);
                            params.put("no_of_stars", "" + starCount);
                            params.put("content", content);
                            params.put("parking_id", "" + _id);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }
        });



        return view;
    }
}