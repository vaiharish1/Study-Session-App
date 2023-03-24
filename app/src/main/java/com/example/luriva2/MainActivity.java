package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void todaysSessionsNav(View v){
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
    }
    public void viewCalNav(View v){
        Intent intent = new Intent(this, ViewCalendar.class );
        startActivity(intent);
    }
    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
    }
    public void settingsNav(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}