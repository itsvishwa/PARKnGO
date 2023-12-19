package com.example.parkngo.parking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.login.LoginOtpActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkingSelectedFragment extends Fragment {

    private View parkingSelectedView;
    private View loadingView;
    private int _id;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parkingSelectedView =  inflater.inflate(R.layout.fragment_parking_selected, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        ParkngoStorage parkngoStorage = new ParkngoStorage(getContext());
        token = parkngoStorage.getData("token");

        // Retrieve data from arguments
        if (getArguments() != null) {
            _id = getArguments().getInt("_id", -1);
        }
        // Perform data loading in the background
        fetchDataFromAPI();

        return loadingView;
    }


    private void fetchDataFromAPI() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/view_one/" + _id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse JSON response and update UI
                            successResponseHandler(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
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
                                errorResponseHandler(jsonResponse.getString("response"));
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
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void successResponseHandler(String response) throws JSONException {
        // Parse JSON response and update UI
        JSONObject jsonObject = new JSONObject(response);
        jsonObject = jsonObject.getJSONObject("response");

        // views
        TextView nameView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_name);
        TextView addressView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_address);
        TextView isPublicView = parkingSelectedView.findViewById(R.id.parking_selected_frag_is_public);
        TextView statusView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_status);
        ImageView imageView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_image);
        RatingBar ratingBar = parkingSelectedView.findViewById(R.id.parking_Selected_frag_star_rating);
        TextView reviewCount = parkingSelectedView.findViewById(R.id.parking_Selected_frag_review_count);

        // setting basic parking space details
        nameView.setText(jsonObject.getString("name"));
        addressView.setText(jsonObject.getString("address"));
        isPublicView.setText(jsonObject.getString("is_public").equals("1") ? "Public" : "Customer Only");
        statusView.setText(jsonObject.getString("is_closed").equals("1") ? "Closed" :  "Open");
        ratingBar.setRating(Integer.parseInt(jsonObject.getString("avg_star_count")));
        reviewCount.setText("( " + jsonObject.getString("total_review_count") + " )");

        // Decode and set the image
        String base64Image = jsonObject.getString("image");
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imageView.setImageBitmap(decodedBitmap);
        }

        if(isPublicView.getText() !="Public"){
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.round_red_circle);
            isPublicView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.round_green_circle);
            isPublicView.setBackground(drawable);
        }

        if(statusView.getText() !="Open"){
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.round_red_circle);
            statusView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.round_green_circle);
            statusView.setBackground(drawable);
        }


        // setting user own review details
        JSONObject userOwnReview = jsonObject.getJSONObject("user_own_reviews");
        Boolean isUserOwnReviewExist = userOwnReview.getString("availability").equals("N/A") ? false : true;

        // getting views references
        Button writeReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_add_review_btn);
        Button editReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_edit_review_btn);
        Button deleteReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_delete_review_btn);
        ConstraintLayout reviewLayout = parkingSelectedView.findViewById(R.id.parking_selected_frag_review_layout);
        TextView noReviewTextView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_no_review_text);

        if(isUserOwnReviewExist){
            writeReviewBtn.setVisibility(View.GONE);
            noReviewTextView.setVisibility(View.GONE);
            editReviewBtn.setVisibility(View.VISIBLE);
            deleteReviewBtn.setVisibility(View.VISIBLE);
            reviewLayout.setVisibility(View.VISIBLE);

            // getting views references
            TextView userNameView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_ri_name);
            TextView userDateView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_ri_date);
            TextView userContentView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_ri_msg);
            RatingBar userRatingBar1 = parkingSelectedView.findViewById(R.id.parking_Selected_frag_ri_rating_bar);

            userNameView.setText(userOwnReview.getString("name"));
            userDateView.setText(userOwnReview.getString("time_stamp"));
            userContentView.setText(userOwnReview.getString("content"));
            userRatingBar1.setRating(Integer.parseInt(userOwnReview.getString("no_of_stars")));

        }else{
            writeReviewBtn.setVisibility(View.VISIBLE);
            noReviewTextView.setVisibility(View.VISIBLE);
            editReviewBtn.setVisibility(View.GONE);
            deleteReviewBtn.setVisibility(View.GONE);
            reviewLayout.setVisibility(View.GONE);
        }

        // setting other's review details
        JSONObject reviewObject = jsonObject.getJSONObject("reviews");
        Boolean isReviewExist = reviewObject.getString("availability").equals("N/A") ? false : true;
        RecyclerView recyclerView = parkingSelectedView.findViewById(R.id.ps_frag_recycle_view);
        TextView otherNoReviewView = parkingSelectedView.findViewById(R.id.parking_selected_frag_no_others_review_text);

        if(isReviewExist){
            recyclerView.setVisibility(View.VISIBLE);
            otherNoReviewView.setVisibility(View.INVISIBLE);
            ArrayList<ReviewModel> reviewModels = new ArrayList<>();
            JSONArray reviewDataArr = reviewObject.getJSONArray("data");
            for (int i = 0; i<reviewDataArr.length(); i++)
            {
                JSONObject reviewData = reviewDataArr.getJSONObject(i);
                String name = reviewData.getString("name");
                String timeStamp = reviewData.getString("time_stamp");
                String content = reviewData.getString("content");
                int no_of_stars = Integer.parseInt(reviewData.getString("no_of_stars"));

                reviewModels.add(new ReviewModel(name, no_of_stars, content, timeStamp));
            }

            RMRecycleViewAdapter adapter = new RMRecycleViewAdapter(reviewModels, getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
            otherNoReviewView.setVisibility(View.VISIBLE);
        }

        // Replace the loading view with the parking view
        ViewGroup parent = (ViewGroup) loadingView.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(loadingView);
            parent.removeView(loadingView);
            parent.addView(parkingSelectedView, index);
        }
    }

    private void errorResponseHandler(String response) throws JSONException {
        // TODO:: Handle error response
        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
    }
}