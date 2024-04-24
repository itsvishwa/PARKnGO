package com.example.officertestapp.Attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.officertestapp.Helpers.DateTimeHelper;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

public class MarkAttendanceOffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_off);

        // Find the TextViews in the layout
        TextView recordedTimeView = findViewById(R.id.attendance_activity_off_time);
        TextView recordedDateView = findViewById(R.id.attendance_activity_off_date);

        // Retrieve the timestamp value from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("timestamp")) {
            String timestamp = extras.getString("timestamp");

            // Convert timestamp to formatted date and time strings
            String recordedTime = DateTimeHelper.convertTimeStampToTime(timestamp);
            String recordedDate = DateTimeHelper.convertTimeStampToDate(timestamp);

            // Set the formatted date and time strings to TextViews
            recordedTimeView.setText(recordedTime);
            recordedDateView.setText(recordedDate);
        }

        Button mainMenuBtn = findViewById(R.id.duty_off_main_menu_btn);
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to start MarkAttendanceActivity
                Intent intent = new Intent(MarkAttendanceOffActivity.this, MarkAttendanceActivity.class);
                startActivity(intent);
            }
        });
    }
}