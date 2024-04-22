package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.DeleteReviewHelper;

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

        DeleteReviewHelper deleteReviewHelper = new DeleteReviewHelper(view, getContext(), requireActivity().getSupportFragmentManager(), _id);
        deleteReviewHelper.init();

        return view;
    }
}