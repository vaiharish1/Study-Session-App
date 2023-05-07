package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DaySessions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_sessions);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String dateDisplay = extras.getString("date");
            TextView dateDisplayText = findViewById(R.id.daySessionTextView);
            dateDisplayText.setText("Sessions For: " + dateDisplay);
        }



    }

}