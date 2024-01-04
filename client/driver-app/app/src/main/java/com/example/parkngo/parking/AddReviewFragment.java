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
import com.example.parkngo.parking.helpers.AddReviewData;

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

        if (getArguments() != null) {
            _id = getArguments().getInt("_id", -1);
        }

        // onclick listeners ......................................................................................................
        // getting references to the button
        Button  reviewAddBtn = view.findViewById(R.id.add_review_frag_add_review_btn);
        reviewAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddReviewData(view, _id, getContext(), requireActivity().getSupportFragmentManager());
            }
        });
        // onclick listeners ......................................................................................................

        return view;
    }
}