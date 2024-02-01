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
import com.example.parkngo.parking.helpers.DeleteReviewData;

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

        if (getArguments() != null) {
            _id = getArguments().getString("_id", "-1");
        }

        DeleteReviewData deleteReviewData = new DeleteReviewData(view, getContext(), requireActivity().getSupportFragmentManager());

        // onclick listeners ......................................................................................................
        // set button listeners - delete review btn
        Button deleteBtn = view.findViewById(R.id.delete_review_frag_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReviewData.deleteReview(_id);
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
        // onclick listeners ......................................................................................................

        return view;
    }
}