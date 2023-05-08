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

public class RepeatedParameters extends AppCompatActivity {
    private NavigationBarView navigationBarView; // navigation bar

    private ArrayList<Session> allSessions, daysSessions; // all sessions (from shared preferences) and the day's sessions (to be displayed in the recycler view)

    private Date today, doingDate; // today's date and the date of the task

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

        // setting today's date by getting a calendar instance
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
        today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));
    }

    public String getTaskName() {
        EditText taskNameText = findViewById(R.id.editTextTaskName_repeated);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }

//    public void collectTaskName(View v) {
//        String taskName = getTaskName();
//        Toast toast = Toast.makeText(getApplicationContext(), "Task Name: " + taskName, Toast.LENGTH_LONG);
//        toast.show();
//    }

    public String getIncrement() {
        EditText incrementText = findViewById(R.id.editTextIncrementValue_repeated);
        String incrementStr = incrementText.getText().toString();
        if (incrementStr.isEmpty()) {
            incrementStr = "";
        }
        return incrementStr;
    }

//    public void collectIncrement(View v) {
//        String increment = getIncrement();
//        Toast toast = Toast.makeText(getApplicationContext(), "Increment by: " + increment + " days", Toast.LENGTH_LONG);
//        toast.show();
//
//    }

    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_repeated);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

//    public void collectTime(View v) {
//        String time = getTime();
//        Toast toast = Toast.makeText(getApplicationContext(), "Estimated time: " + time, Toast.LENGTH_LONG);
//        toast.show();
//
//    }

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


//    public void onClickEasy(View v){
//        populateEasy();
//        collectDif(v);
//    }
//    public void onClickMedium(View v){
//        populateMid();
//        collectDif(v);
//    }
//    public void onCLickHard(View v){
//        populateHard();
//        collectDif(v);
//    }

    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_repeated);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

//    public void collectDif(View v) {
//        String dif = getDif();
//        Toast toast = Toast.makeText(getApplicationContext(), "Difficulty: " + dif, Toast.LENGTH_LONG);
//        toast.show();
//
//    }

    public void todaysSessionsNav(View v){
        // getting the task name
        String name = getTaskName();
        if (name.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Empty task name.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        String howOftenStr = getIncrement();
        if (howOftenStr.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "How often task is repeated not given.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        int howOften = Integer.parseInt(getIncrement());

        // getting the difficulty of the task
        String difficulty = getDif();
        if (difficulty.equals("Click Difficulty")) {
            Toast toast = Toast.makeText(getApplicationContext(), "No task difficulty stated.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        int estimatedDifficulty;
        if (difficulty.equals("Easy")) estimatedDifficulty = 3;
        else if (difficulty.equals("Medium")) estimatedDifficulty = 2;
        else if (difficulty.equals("Hard")) estimatedDifficulty = 1;
        else estimatedDifficulty = 4;

        // getting the estimated time
        String timeStr = getTime();
        if (timeStr.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "No estimated time given.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        int time = Integer.parseInt(getTime());

        // actually creating the task
        Task newTask = new RepetitiveTask(name, time, estimatedDifficulty, howOften);

        loadData();
        // TODO: add session here
        saveData(v);

        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }

    public void saveData(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Session> savedSessions = new ArrayList<Session>();
        for (int i = 0; i < allSessions.size(); i++) {
            if (allSessions.get(i).getDate().equals(doingDate)) {
                continue;
            } else {
                savedSessions.add(allSessions.get(i));
            }
        }

        for (int i = 0; i < daysSessions.size(); i++) {
            savedSessions.add(daysSessions.get(i));
        }

        String json = gson.toJson(savedSessions);
        editor.putString("session list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        if (allSessions == null) {
            setUpSessionModels();
        }
    }

    public void setUpSessionModels() {
        allSessions = new ArrayList<Session>();
        String[] sessionNames = getResources().getStringArray(R.array.session_names);
        String[] sessionTypes = getResources().getStringArray(R.array.session_types);

        String[] sessionStartTimesHours = getResources().getStringArray(R.array.session_start_times_hours);
        String[] sessionStartTimesMinutes = getResources().getStringArray(R.array.session_start_times_minutes);
        String[] sessionEndTimesHours = getResources().getStringArray(R.array.session_end_times_hours);
        String[] sessionEndTimesMinutes = getResources().getStringArray(R.array.session_end_times_minutes);

        for (int i = 0; i < sessionNames.length; i++) {
            Time startTime = new Time(Integer.parseInt(sessionStartTimesHours[i]), Integer.parseInt(sessionStartTimesMinutes[i]));
            Time endTime = new Time(Integer.parseInt(sessionEndTimesHours[i]), Integer.parseInt(sessionEndTimesMinutes[i]));
            Timeblock tb = new Timeblock(startTime, endTime);
            Task t = new Task(sessionNames[i], 50, 2, sessionTypes[i]);
            allSessions.add(new Session(t, today, tb));
//            Log.v("MODELS", sessionModels.get(i).toString());
        }
    }
}
