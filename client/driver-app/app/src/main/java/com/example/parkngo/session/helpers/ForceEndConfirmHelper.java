package com.example.parkngo.session.helpers;


import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.parkngo.MainActivity;
import com.example.parkngo.R;
import com.example.parkngo.session.ForceEndSucessfullFragment;

public class ForceEndConfirmHelper {
    View forceEndConfirmView;
    Context context;
    public ForceEndConfirmHelper(Context context, View forceEndConfirmView){
        this.context = context;
        this.forceEndConfirmView = forceEndConfirmView;
    }

    public void initAllBtnListeners(){
        initConfirmBtnListener();
    }

    private void initConfirmBtnListener(){
        Button button = forceEndConfirmView.findViewById(R.id.force_end_confirm_fragment_confirm_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity  = (MainActivity) context;
                mainActivity.replaceFragment(new ForceEndSucessfullFragment());
            }
        });
    }
}
