package com.example.parkngo.profile.helpers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkngo.R;
import com.example.parkngo.helpers.ParkngoStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileRequestHelper {
    View editProfileView;
    FragmentManager fragmentManager;
    Context context;
    ParkngoStorage parkngoStorage;

    public EditProfileRequestHelper(View editProfileView, Context context, FragmentManager fragmentManager){
        this.editProfileView = editProfileView;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.parkngoStorage = new ParkngoStorage(context);
    }

    public void init(){
        setUpDefaultValues();
        updateBtnHandler();
        discardBtnHandler();
    }

    private void setUpDefaultValues(){
        TextView firstNameView = editProfileView.findViewById(R.id.edit_prof_frag_first_name);
        TextView lastNameView = editProfileView.findViewById(R.id.edit_prof_frag_last_name);
        firstNameView.setText(parkngoStorage.getData("firstName"));
        lastNameView.setText(parkngoStorage.getData("lastName"));
    }


    private void updateBtnHandler(){
        Button saveChangesBtn = editProfileView.findViewById(R.id.edit_prof_frag_save_btn);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateName();
            }
        });
    }


    private void discardBtnHandler(){
        Button discardBtn = editProfileView.findViewById(R.id.edit_prof_frag_discard_btn);
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDefaultValues();
            }
        });
    }

    private void updateName(){
        TextView firstNameView = editProfileView.findViewById(R.id.edit_prof_frag_first_name);
        TextView lastNameView = editProfileView.findViewById(R.id.edit_prof_frag_last_name);

        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();

        if(firstName.equals("") || lastName.equals("")){
            Toast.makeText(context, "Name Fields can't be empty!", Toast.LENGTH_LONG).show();
        }else{
            sendUpdateRequest(firstName, lastName);
        }
    }

    private void sendUpdateRequest(String fn, String ln){
        String firstName = Character.toUpperCase(fn.charAt(0)) + fn.substring(1);
        String lastName = Character.toUpperCase(ln.charAt(0)) + ln.substring(1);

        RequestQueue queue = Volley.newRequestQueue(context);
        String apiURL = "http://192.168.56.1/PARKnGO/server/mobile/profile/update_name/" + firstName + "/" + lastName;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        successResponseHandler(firstName, lastName);
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
                String token = parkngoStorage.getData("token");
                headers.put("token", token);
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    private void successResponseHandler(String firstName, String lastName){
        parkngoStorage.updateData("firstName", firstName);
        parkngoStorage.updateData("lastName", lastName);
        Toast.makeText(context, "Your name is updated successfully!", Toast.LENGTH_SHORT).show();
        // Navigate back to the previous fragment
        fragmentManager.popBackStack();
    }

    private void errorResponseHandler(VolleyError error) {
        String errorResponse;
        if (error.networkResponse != null && error.networkResponse.data != null) {
            errorResponse = new String(error.networkResponse.data);
            try {
                JSONObject jsonResponse = new JSONObject(errorResponse);
                Toast.makeText(context, jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
