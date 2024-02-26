package com.example.parkngo.parking.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkingSelectedFetchData {

    View parkingSelectedView;
    View loadingView;
    String parkingID;
    Context context;
    String userReviewId;
    String userReviewContent;
    int userReviewRating;
    String latitude;
    String longitude;

    public ParkingSelectedFetchData(View parkingSelectedView, View loadingView, String _id, Context context){
        this.parkingSelectedView = parkingSelectedView;
        this.loadingView = loadingView;
        this.parkingID = _id;
        this.context = context;
        fetchData();
    }

    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    private void fetchData(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/parkingSpace/view_one/" + parkingID;

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
                Map<String, String> headers = new HashMap<>();
                ParkngoStorage parkngoStorage = new ParkngoStorage(context);
                String token = parkngoStorage.getData("token");
                headers.put("token", token);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    // response-success handler
    private void successResponseHandler(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject selectedParkingSpaceData = jsonObject.getJSONObject("response");

            setParkingDetails(selectedParkingSpaceData);
            setUserOwnReviews(selectedParkingSpaceData);
            setOtherUsersReviews(selectedParkingSpaceData);

            // Replace the loading view with the parking view
            ViewGroup parent = (ViewGroup) loadingView.getParent();
            if (parent != null) {
                int index = parent.indexOfChild(loadingView);
                parent.removeView(loadingView);
                parent.addView(parkingSelectedView, index);
            }
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    // response-error handler
    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                String response = jsonResponse.getString("response");
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                // TODO:: find each 400, 404 failure cases and implement necessary functionalities and views
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // set parking details to the respective views
    private void setParkingDetails(JSONObject selectedParkingSpaceData) throws JSONException{
        TextView topAppBarNameView = parkingSelectedView.findViewById(R.id.appbar_main_text);
        TextView nameView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_name);
        TextView addressView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_address);
        TextView isPublicView = parkingSelectedView.findViewById(R.id.parking_selected_frag_is_public);
        TextView statusView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_status);
        ImageView imageView = parkingSelectedView.findViewById(R.id.parking_Selected_frag_image);
        RatingBar ratingBar = parkingSelectedView.findViewById(R.id.parking_Selected_frag_star_rating);
        TextView reviewCount = parkingSelectedView.findViewById(R.id.parking_Selected_frag_review_count);

        // setting basic parking space details
        latitude = selectedParkingSpaceData.getString("latitude");
        longitude = selectedParkingSpaceData.getString("longitude");

        topAppBarNameView.setText(selectedParkingSpaceData.getString("name"));
        nameView.setText(selectedParkingSpaceData.getString("name"));
        addressView.setText(selectedParkingSpaceData.getString("address"));
        isPublicView.setText(selectedParkingSpaceData.getString("is_public").equals("1") ? "Public" : "Customer Only");
        statusView.setText(selectedParkingSpaceData.getString("is_closed").equals("1") ? "Closed" :  "Open");
        ratingBar.setRating(Integer.parseInt(selectedParkingSpaceData.getString("avg_star_count")));
        reviewCount.setText("( " + selectedParkingSpaceData.getString("total_review_count") + " )");

        // set backgrounds
        if(isPublicView.getText() !="Public"){
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.round_red_circle);
            isPublicView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.round_green_circle);
            isPublicView.setBackground(drawable);
        }

        // set backgrounds
        if(statusView.getText() !="Open"){
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.round_red_circle);
            statusView.setBackground(drawable);
        }else{
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.round_green_circle);
            statusView.setBackground(drawable);
        }

        // Decode and set the image
        String base64Image = selectedParkingSpaceData.getString("image");
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imageView.setImageBitmap(decodedBitmap);
        }
    }


    // set user own reviews
    private  void setUserOwnReviews(JSONObject selectedParkingSpaceData) throws JSONException{
        // setting user own review details
        JSONObject userOwnReview = selectedParkingSpaceData.getJSONObject("user_own_reviews");
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

            this.userReviewId = userOwnReview.getString("_id");
            this.userReviewContent = userOwnReview.getString("content");
            this.userReviewRating = Integer.parseInt(userOwnReview.getString("no_of_stars"));

        }else{
            writeReviewBtn.setVisibility(View.VISIBLE);
            noReviewTextView.setVisibility(View.VISIBLE);
            editReviewBtn.setVisibility(View.GONE);
            deleteReviewBtn.setVisibility(View.GONE);
            reviewLayout.setVisibility(View.GONE);
        }
    }


    // set other user's reviews
    private void setOtherUsersReviews(JSONObject selectedParkingSpaceData) throws JSONException{
        // setting other's review details
        JSONObject reviewObject = selectedParkingSpaceData.getJSONObject("reviews");
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

            RMRecycleViewAdapter adapter = new RMRecycleViewAdapter(reviewModels, context);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }else{
            recyclerView.setVisibility(View.GONE);
            otherNoReviewView.setVisibility(View.VISIBLE);
        }
    }


    // return user_review_id
    public String getUserReviewId(){
        return userReviewId;
    }

    // return user review content
    public String getUserReviewContent(){return userReviewContent;}

    // return user review rating
    public int getUserReviewRating(){return userReviewRating;}
}
