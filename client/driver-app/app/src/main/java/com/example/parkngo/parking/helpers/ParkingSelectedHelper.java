package com.example.parkngo.parking.helpers;

import static android.view.View.GONE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
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
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.home.AvailableParkingSpacesFragment;
import com.example.parkngo.parking.AddReviewFragment;
import com.example.parkngo.parking.DeleteReviewFragment;
import com.example.parkngo.parking.EditReviewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkingSelectedHelper {

    View parkingSelectedView;
    View loadingView;
    String parkingID;
    Context context;
    String userReviewId;
    String userReviewContent;
    int userReviewRating;
    String latitude;
    String longitude;
    Double myLatitude;
    Double myLongitude;
    private LocationManager locationManager;

    public ParkingSelectedHelper(View parkingSelectedView, View loadingView, String _id, Context context){
        this.parkingSelectedView = parkingSelectedView;
        this.loadingView = loadingView;
        this.parkingID = _id;
        this.context = context;
    }


    public void init(){
        fetchData();
        addReviewBtnHandler();
        deleteReviewBtnHandler();
        editReviewBtnHandler();
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
            setVehicleTypesDetails(selectedParkingSpaceData);
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


    // set vehicle types details
    private void setVehicleTypesDetails(JSONObject selectedParkingSpaceData) throws JSONException{
        JSONArray slotDetailsArr =selectedParkingSpaceData.getJSONArray("slot_status");

        TextView vt1Name = parkingSelectedView.findViewById(R.id.ps_frag_vt1_name);
        TextView vt1Slot = parkingSelectedView.findViewById(R.id.ps_frag_vt1_slots);
        TextView vt1Rate = parkingSelectedView.findViewById(R.id.ps_frag_vt1_rate);
        TextView vt2Name = parkingSelectedView.findViewById(R.id.ps_frag_vt2_name);
        TextView vt2Slot = parkingSelectedView.findViewById(R.id.ps_frag_vt2_slots);
        TextView vt2Rate = parkingSelectedView.findViewById(R.id.ps_frag_vt2_rate);
        TextView vt3Name = parkingSelectedView.findViewById(R.id.ps_frag_vt3_name);
        TextView vt3Slot = parkingSelectedView.findViewById(R.id.ps_frag_vt3_slots);
        TextView vt3Rate = parkingSelectedView.findViewById(R.id.ps_frag_vt3_rate);
        TextView vt4Name = parkingSelectedView.findViewById(R.id.ps_frag_vt4_name);
        TextView vt4Slot = parkingSelectedView.findViewById(R.id.ps_frag_vt4_slots);
        TextView vt4Rate = parkingSelectedView.findViewById(R.id.ps_frag_vt4_rate);

        boolean isV1 = false;
        boolean isV2 = false;
        boolean isV3 = false;
        boolean isV4 = false;

        for(int i=0; i<slotDetailsArr.length(); i++){
            JSONObject tempObj = slotDetailsArr.getJSONObject(i);
            String tempVType = tempObj.getString("vehicle_type");
            String tempFSlots = tempObj.getString("free_slots");
            String tempTSlots = tempObj.getString("total_slots");
            String tempRate = tempObj.getString("rate");

            if(tempVType.equals("Car|Tuktuk|Mini Van")){
                isV1 = true;
                vt1Rate.setText("Rs. " + tempRate);
                vt1Slot.setText(tempFSlots + " free out of " + tempTSlots);
            } else if (tempVType.equals("Bicycle")) {
                isV2 = true;
                vt2Rate.setText("Rs. " + tempRate);
                vt2Slot.setText(tempFSlots + " free out of " + tempTSlots);
            } else if (tempVType.equals("Van|Lorry|Mini Bus")) {
                isV3 = true;
                vt3Rate.setText("Rs. " + tempRate);
                vt3Slot.setText(tempFSlots + " free out of " + tempTSlots);
            }else{
                isV4 = true;
                vt4Rate.setText("Rs. " + tempRate);
                vt4Slot.setText(tempFSlots + " free out of " + tempTSlots);
            }
        }

        if(!isV1){
            vt1Name.setVisibility(GONE);
            vt1Rate.setVisibility(GONE);
            vt1Slot.setVisibility(GONE);
        } else if (!isV2) {
            vt2Name.setVisibility(GONE);
            vt2Rate.setVisibility(GONE);
            vt2Slot.setVisibility(GONE);
        } else if (!isV3) {
            vt3Name.setVisibility(GONE);
            vt3Rate.setVisibility(GONE);
            vt3Slot.setVisibility(GONE);
        } else if (!isV4) {
            vt4Name.setVisibility(GONE);
            vt4Rate.setVisibility(GONE);
            vt4Slot.setVisibility(GONE);
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
            writeReviewBtn.setVisibility(GONE);
            noReviewTextView.setVisibility(GONE);
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
            editReviewBtn.setVisibility(GONE);
            deleteReviewBtn.setVisibility(GONE);
            reviewLayout.setVisibility(GONE);
        }
    }


    // set other user's reviews
    private void setOtherUsersReviews(JSONObject selectedParkingSpaceData) throws JSONException{
        // setting other's review details
        JSONObject reviewObject = selectedParkingSpaceData.getJSONObject("reviews");
        Boolean isReviewExist = reviewObject.getString("availability").equals("N/A") ? false : true;

        RecyclerView recyclerView = parkingSelectedView.findViewById(R.id.ps_frag_recycle_view);
        TextView otherNoReviewView = parkingSelectedView.findViewById(R.id.parking_selected_frag_no_others_review_text);
        ImageView noOtherReviewImageView = parkingSelectedView.findViewById(R.id.ps_frag_noreview_img);

        if(isReviewExist){
            recyclerView.setVisibility(View.VISIBLE);
            otherNoReviewView.setVisibility(View.INVISIBLE);
            noOtherReviewImageView.setVisibility(View.INVISIBLE);
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
            recyclerView.setVisibility(GONE);
            otherNoReviewView.setVisibility(View.VISIBLE);
            noOtherReviewImageView.setVisibility(View.VISIBLE);
        }
    }


    public void addReviewBtnHandler(){
        Button addReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_add_review_btn);
        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("parkingID", parkingID);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new AddReviewFragment(), data);
            }
        }
        );
    }


    public void deleteReviewBtnHandler(){
        // set delete review btn handler
        Button deleteReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_delete_review_btn);
        deleteReviewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bundle data = new Bundle();
                data.putString("_id", userReviewId);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new DeleteReviewFragment(), data);
            }
        });
    }


    public void editReviewBtnHandler(){
        // set edit review btn handler
        Button editReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_edit_review_btn);
        editReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("_id", userReviewId);
                data.putString("content", userReviewContent);
                data.putInt("rating", userReviewRating);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.replaceFragment(new EditReviewFragment(), data);
            }
        });
    }


    private void navigateBtnProcess(){

        double sourceLatitude = myLatitude;
        double sourceLongitude = myLongitude;
        double destinationLatitude = Double.parseDouble(latitude);
        double destinationLongitude = Double.parseDouble(longitude);

      //   String uri = "https://www.google.com/maps/dir/?api=1&origin=" + sourceLatitude + "," + sourceLongitude + "&destination=" + destinationLatitude + "," + destinationLongitude; // uncomment for TRUE GPS
        String uri = "https://www.google.com/maps/dir/?api=1&origin=" + 6.900662 + "," + 79.8617228 + "&destination=" + destinationLatitude + "," + destinationLongitude;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.startActivity(intent);

    }


    public void getLocationAndContinue() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Get latitude and longitude from location object
                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();
                locationManager.removeUpdates(this);

                navigateBtnProcess();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}
