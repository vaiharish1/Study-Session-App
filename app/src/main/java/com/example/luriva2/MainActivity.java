package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void todaysSessionsNav(View v){
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }
    public void viewCalNav(View v){
        Log.d("vai","in viewCalNav method");
        Intent intent = new Intent(this, ViewCalendar.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Calendar", Toast.LENGTH_LONG);
        toast.show();
    }
    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Task Manager", Toast.LENGTH_LONG);
        toast.show();
    }
    public void settingsNav(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Settings", Toast.LENGTH_LONG);
        toast.show();
    }
}