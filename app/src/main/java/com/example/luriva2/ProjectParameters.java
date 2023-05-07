package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luriva2.dataModelClasses.Constants;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.OneTimeTask;
import com.example.luriva2.dataModelClasses.ProjectTask;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProjectParameters extends AppCompatActivity {
    NavigationBarView navigationBarView;

    private ArrayList<Session> sessionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_parameters);
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
        EditText taskNameText = findViewById(R.id.editTextTaskName_project);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }


    public void collectTaskName(View v) {
        String taskName = getTaskName();
        Toast toast = Toast.makeText(getApplicationContext(), "Task Name: " + taskName, Toast.LENGTH_LONG);
        toast.show();
    }

    public String getDueDate() {
        EditText dueDateText = findViewById(R.id.editTextDueDate_project);
        String dueDateStr = dueDateText.getText().toString();
        if (dueDateStr.isEmpty()) {
            dueDateStr = "";
        }
        return dueDateStr;
    }

    public void collectDueDate(View v) {
        String dueDate = getDueDate();
        Toast toast = Toast.makeText(getApplicationContext(), "Due Date: " + dueDate, Toast.LENGTH_LONG);
        toast.show();
    }

    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_onetime);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

    public void collectTime(View v) {
        String time = getTime();
        Toast toast = Toast.makeText(getApplicationContext(), "Estimated time: " + time, Toast.LENGTH_LONG);
        toast.show();

    }

    public void populateEasy(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Easy");
    }
    public void populateMid(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Medium");
    }
    public void populateHard(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Hard");
    }


    public void onClickEasy(View v){
        populateEasy();
        collectDif(v);
    }
    public void onClickMedium(View v){
        populateMid();
        collectDif(v);
    }
    public void onCLickHard(View v){
        populateHard();
        collectDif(v);
    }
    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_project);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

    public void collectDif(View v) {
        String dif = getDif();
        Toast toast = Toast.makeText(getApplicationContext(), "Difficulty: " + dif, Toast.LENGTH_LONG);
        toast.show();

    }

    public void todaysSessionsNav(View v){
        String name = getTaskName();
        String dueDate = getDueDate();

        String difficulty = getDif();
        int estimatedDifficulty;
        if (difficulty.equals("Easy")) estimatedDifficulty = 3;
        else if (difficulty.equals("Medium")) estimatedDifficulty = 2;
        else if (difficulty.equals("Hard")) estimatedDifficulty = 1;
        else estimatedDifficulty = 4;

        int time = Integer.parseInt(getTime());

        Task newTask = new ProjectTask(name, time, estimatedDifficulty, new Date(0, 0, 2025));

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
        String json = gson.toJson(sessionModels);
        editor.putString("session list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        sessionModels = gson.fromJson(json, type);

        if (sessionModels == null) {
            setUpSessionModels();
        }
    }

    public void setUpSessionModels() {
        sessionModels = new ArrayList<Session>();
        String[] sessionNames = getResources().getStringArray(R.array.session_names);
        String[] sessionTypes = getResources().getStringArray(R.array.session_types);

        String[] sessionStartTimesHours = getResources().getStringArray(R.array.session_start_times_hours);
        String[] sessionStartTimesMinutes = getResources().getStringArray(R.array.session_start_times_minutes);
        String[] sessionEndTimesHours = getResources().getStringArray(R.array.session_end_times_hours);
        String[] sessionEndTimesMinutes = getResources().getStringArray(R.array.session_end_times_minutes);

        android.icu.util.Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("/");

        for (int i = 0; i < sessionNames.length; i++) {
            Time startTime = new Time(Integer.parseInt(sessionStartTimesHours[i]), Integer.parseInt(sessionStartTimesMinutes[i]));
            Time endTime = new Time(Integer.parseInt(sessionEndTimesHours[i]), Integer.parseInt(sessionEndTimesMinutes[i]));
            Timeblock tb = new Timeblock(startTime, endTime);
            Task t = new Task(sessionNames[i], 50, 2, sessionTypes[i]);
            sessionModels.add(new Session(t, new Date(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2])), tb));
//            Log.v("MODELS", sessionModels.get(i).toString());
        }
    }
}
