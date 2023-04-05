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

        String start = s.getText().toString();
        String end = e.getText().toString();
        String day = spinner.getSelectedItem().toString();

        Toast toast = Toast.makeText(getApplicationContext(),"You are unavailable from " + start + " to " + end,Toast.LENGTH_LONG);
        toast.show();

        s.setText("");
        e.setText("");
    }


}

