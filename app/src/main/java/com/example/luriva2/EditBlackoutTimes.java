package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


//Made with help from https://developer.android.com/develop/ui/views/components/spinner#java
public class EditBlackoutTimes extends AppCompatActivity {
    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_blackout_times);
        navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.viewTasksNavigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.timerNavigation:
                        startActivity(new Intent(getApplicationContext(),Timer.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.homeNavigation:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.viewTasksNavigation:
                        startActivity(new Intent(getApplicationContext(),TodaysSessions.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.chooseDaysDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.editDaysList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void save(View v){
        EditText s = findViewById(R.id.startTime);
        EditText e = findViewById(R.id.endTime);
        Spinner spinner = (Spinner) findViewById(R.id.chooseDaysDropdown);

        String pattern = "^([0-1]?[0-9]|2[0-3]):?[0-5][0-9]$";
        String start = s.getText().toString();
        String end = e.getText().toString();
        String day = spinner.getSelectedItem().toString();
        int startTimeInt = timeToInt(start);
        int endTimeInt = timeToInt(end);

        s.setText("");
        e.setText("");

        if(!start.matches(pattern))
            start = "Invalid time";
        if(!end.matches(pattern))
            end = "Invalid time";

        Toast toast = Toast.makeText(getApplicationContext(),"You are unavailable from " + start + " to " + end + " on " + day,Toast.LENGTH_LONG);
        toast.show();

    }

    private static int timeToInt(String time) {
        int timeInt = 0;
        int hr1 = Integer.parseInt(time.substring(0, 1)) * 60;
        int hr2 = Integer.parseInt(time.substring(0, 2)) * 60;

        if(time.contains(":")) {
            if (time.length() == 4) {
                timeInt+= hr1;
                timeInt+=Integer.parseInt(time.substring(2));
            }
            else if(time.length() == 5){
                timeInt+= hr2;
                timeInt+=Integer.parseInt(time.substring(3));
            }
        }

        else {
            if(time.length()==3) {
                timeInt+= hr1;
                timeInt+=Integer.parseInt(time.substring(1));
            }
            else if(time.length() == 4){
                timeInt+= hr2;
                timeInt+=Integer.parseInt(time.substring(2));
            }
        }
        return timeInt;
    }
}

