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
import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.parking.helpers.ParkingSelectedFetchData;
import com.example.parkngo.parking.helpers.RMRecycleViewAdapter;
import com.example.parkngo.parking.helpers.ReviewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkingSelectedFragment extends Fragment {

    private View parkingSelectedView;
    private View loadingView;
    private int _id;
    private String user_review_id;

    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get main activity context
        mainActivity = (MainActivity)requireContext();

        // Inflate the layout for this fragment
        parkingSelectedView =  inflater.inflate(R.layout.fragment_parking_selected, container, false);
        loadingView = inflater.inflate(R.layout.loading_frag, container, false);

        // Retrieve data from arguments
        if (getArguments() != null) {
            _id = getArguments().getInt("_id", -1);
        }

        // Perform data loading in the background
        ParkingSelectedFetchData parkingSelectedFetchData = new ParkingSelectedFetchData(parkingSelectedView, loadingView, _id, getContext());

        // onclick listeners ......................................................................................................

        //set write review btn handler
        Button addReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_add_review_btn);
        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putInt("_id", _id);
                mainActivity.replaceFragment(new AddReviewFragment(), data);
            }
        }
        );

        // set delete review btn handler
        Button deleteReviewBtn = parkingSelectedView.findViewById(R.id.parking_Selected_frag_delete_review_btn);
        deleteReviewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bundle data = new Bundle();
                data.putString("_id", parkingSelectedFetchData.getUser_review_id());

                mainActivity.replaceFragment(new DeleteReviewFragment(), data);
            }
        });
        // onclick listeners ......................................................................................................


        return loadingView;
    }
}