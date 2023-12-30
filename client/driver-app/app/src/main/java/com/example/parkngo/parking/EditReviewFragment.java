package com.example.parkngo.parking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkngo.R;
import com.example.parkngo.parking.helpers.EditReviewData;

public class EditReviewFragment extends Fragment {

    String _id;
    String content;
    int rating;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View editReviewView =  inflater.inflate(R.layout.fragment_edit_review, container, false);

        if (getArguments() != null) {
            _id = getArguments().getString("_id", "");
            content = getArguments().getString("content", "");
            rating = getArguments().getInt("rating", -1);
        }

        EditReviewData editReviewData = new EditReviewData(editReviewView, getContext(), requireActivity().getSupportFragmentManager());
        editReviewData.setupDefaultReview(content, rating); // show a preview of current review

        // onclick listeners ......................................................................................................
        // confirm btn
        Button editReviewConfirmBtn = editReviewView.findViewById(R.id.edit_review_frag_confirm_btn);
        editReviewConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editReviewData.editReview(_id);
            }
        });

        // discard btn
        Button editReviewDiscardBtn = editReviewView.findViewById(R.id.edit_review_frag_discard_btn);
        editReviewDiscardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editReviewData.setupDefaultReview(content, rating);
            }
        });
        // onclick listeners ......................................................................................................

        return editReviewView;
    }
}