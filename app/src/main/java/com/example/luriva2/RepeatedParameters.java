package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.example.luriva2.dataModelClasses.Constants;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.OneTimeTask;
import com.example.luriva2.dataModelClasses.RepetitiveTask;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Time;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RepeatedParameters extends TaskParameters {
    private NavigationBarView navigationBarView; // navigation bar

    private Date doingDate; // today's date and the date of the task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_parameters);
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
    }

    public String getTaskName() {
        EditText taskNameText = findViewById(R.id.editTextTaskName_repeated);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }

    public String getIncrement() {
        EditText incrementText = findViewById(R.id.editTextIncrementValue_repeated);
        String incrementStr = incrementText.getText().toString();
        if (incrementStr.isEmpty()) {
            incrementStr = "";
        }
        return incrementStr;
    }

    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_repeated);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

    public void populateEasy(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Easy");
    }
    public void populateMid(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Medium");
    }
    public void populateHard(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Hard");
    }

    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_repeated);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

    public void todaysSessionsNav(View v){
        // getting the task name
        String name = getTaskName();
        if (!checkTaskName(name)) return;

        // getting how often to repeat
        String howOftenStr = getIncrement();
        if (!checkHowOften(howOftenStr)) return;
        int howOften = transformToHowOften(howOftenStr);

        // getting the estimated time
        String timeStr = getTime();
        if (!checkEstimatedTime(timeStr)) return;
        int time = transformToTime(timeStr);

        // getting the difficulty of the task
        String difficulty = getDif();
        if (!checkDifficulty(difficulty)) return;
        int estimatedDifficulty = transformToEstimatedDifficulty(difficulty);

        // actually creating the task
        Task newTask = new RepetitiveTask(name, time, estimatedDifficulty, howOften);
        Date today = getToday();

        for (int i = 0; i < 30; i++) {
            doingDate = today.addDays(i * howOften);
            addingSessions(doingDate, time, newTask);
        }

        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }
}
