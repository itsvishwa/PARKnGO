package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.EditReviewHelper;

public class EditReviewFragment extends Fragment {

    String parkingID;
    String content;
    int rating;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editReviewView =  inflater.inflate(R.layout.fragment_edit_review, container, false);

        if (getArguments() != null) {
            parkingID = getArguments().getString("_id");
            content = getArguments().getString("content");
            rating = getArguments().getInt("rating");
        }

        EditReviewHelper editReviewHelper = new EditReviewHelper(editReviewView, getContext(), requireActivity().getSupportFragmentManager(), parkingID, content, rating);
        editReviewHelper.init();

        return editReviewView;
    }
}