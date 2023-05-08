package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


//Made with help from https://developer.android.com/develop/ui/views/components/spinner#java
public class EditBlackoutTimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blackout_times);

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

