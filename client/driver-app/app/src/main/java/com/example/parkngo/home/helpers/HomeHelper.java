package com.example.parkngo.home.helpers;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;

import java.util.ArrayList;

public class HomeHelper {
    public HomeHelper(View view, Context context){
        // set spinner
        Spinner spinner = view.findViewById(R.id.home_frag_spinner);
        ArrayList<String> vehicleTypes = new ArrayList<>();
        String[] vTypes = {"Car", "Bike", "Van", "Lorry", "Bus"};
        for (int i=0; i<vTypes.length; i++){
            vehicleTypes.add(vTypes[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, vehicleTypes);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        // set the pulse animation for constraint layout
        ConstraintLayout constraintLayout = view.findViewById(R.id.home_frag_cons_layout);
        Animation pulse = AnimationUtils.loadAnimation(context, R.anim.anim_pulse);
        constraintLayout.startAnimation(pulse);

        // replace the user name
        ParkngoStorage parkngoStorage = new ParkngoStorage(context);
        String firstName = parkngoStorage.getData("firstName");
        TextView nameView = view.findViewById(R.id.home_frag_user_name);
        nameView.setText(firstName);
    }
}
