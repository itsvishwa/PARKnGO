package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.R;
import com.example.parkngo.login.LoginOtpActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingSelectedFragment extends Fragment {

    ArrayList<ReviewModel> reviewModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parking_selected, container, false);

        // setup the model arraylist
        setupReviewModels();

        // get a reference to the recycle view
        RecyclerView recyclerView = view.findViewById(R.id.ps_frag_recycle_view);

        RMRecycleViewAdapter adapter = new RMRecycleViewAdapter(reviewModels, getContext());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void setupReviewModels(){
        String[] names = {"Harry Potter", "Hermione Granger", "Ron Weasley", "Albus Dumbledore", "Severus Snape", "Luna Lovegood", "Nymphadora Tonks", "Sirius Black", "Remus Lupin", "Ginny Weasley"};
        int[] noOfStars = {4, 5, 3, 2, 4, 5, 3, 4, 4, 3};
        String[] msg = {"Oh, joy. Another parking adventure.", "Simply magical parking experience.", "Parking level: Weasley's flying car.", "Managed to park, but it was no magic.", "Parked with Snape's level of enthusiasm.", "Luna would approve of this parking space.", "Tonks would shape-shift into a better spot.", "Black-worthy parking spot.", "Lupin would find this parking howl-worthy.", "Ginny would hit this parking out of the park."};
        String[] dates = {"2023/09/10", "2023/09/11", "2023/09/12", "2023/09/13", "2023/09/14", "2023/09/15", "2023/09/16", "2023/09/17", "2023/09/18", "2023/09/19"};

        for(int i=0; i< names.length; i++){
            reviewModels.add(new ReviewModel(names[i], noOfStars[i], msg[i], dates[i]));
        }
    }


}