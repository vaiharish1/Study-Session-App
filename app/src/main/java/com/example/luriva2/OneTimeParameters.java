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
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.TodaysSessions;
import com.example.luriva2.dataModelClasses.Time;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OneTimeParameters extends AppCompatActivity {
    private NavigationBarView navigationBarView; // navigation bar

    private ArrayList<Session> allSessions, daysSessions; // all sessions (from shared preferences) and the day's sessions (to be displayed in the recycler view)

    private Date today, doingDate; // today's date and the date of the task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_parameters); // the layout to be used

        // all the things with the navigation bar
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

    // getting the task name from the edittext
    public String getTaskName() {
        EditText taskNameText = findViewById(R.id.editTextTaskName_onetime);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }

//        public void collectTaskName(View v) {
//            String taskName = getTaskName();
//            Toast toast = Toast.makeText(getApplicationContext(), "Task Name: " + taskName, Toast.LENGTH_LONG);
//            toast.show();
//        }

    // getting the due date from the edittext
    public String getDueDate() {
        EditText dueDateText = findViewById(R.id.editTextDueDate_onetime);
        String dueDateStr = dueDateText.getText().toString();
        if (dueDateStr.isEmpty()) {
            dueDateStr = "";
        }
        return dueDateStr;
    }

//        public void collectDueDate(View v) {
//            String dueDate = getDueDate();
//            Toast toast = Toast.makeText(getApplicationContext(), "Due Date: " + dueDate, Toast.LENGTH_LONG);
//            toast.show();
//        }

    // getting the estimated time from the edittext
    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_onetime);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

//        public void collectTime(View v) {
//            String time = getTime();
//            Toast toast = Toast.makeText(getApplicationContext(), "Estimated time: " + time, Toast.LENGTH_LONG);
//            toast.show();
//        }

    // when pressing the "easy" button, populating the difficulty text with "Easy"
    public void populateEasy(View v) {
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
        difficultyText.setText("Easy");
    }
    // when pressing the "medium" button, populating the difficulty text with "Medium"
    public void populateMid(View v) {
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
        difficultyText.setText("Medium");
    }
    // when pressing the "hard" button, populating the difficulty text with "Hard"
    public void populateHard(View v) {
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
        difficultyText.setText("Hard");
    }

//        public void onClickEasy(View v){
//            populateEasy();
//            collectDif(v);
//        }
//        public void onClickMedium(View v){
//            populateMid();
//            collectDif(v);
//        }
//        public void onClickHard(View v){
//            populateHard();
//            collectDif(v);
//        }

    // getting the estimated difficulty from the difficulty textview
    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_onetime);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

//        public void collectDif(View v) {
//            String dif = getDif();
//            Toast toast = Toast.makeText(getApplicationContext(), "Difficulty: " + dif, Toast.LENGTH_LONG);
//            toast.show();
//        }

    // when adding the task, we get all the required information (and catch for errors) and then add the task
    public void todaysSessionsNav(View v){
        // getting the task name
        String name = getTaskName();
        if (name.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Empty task name.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        // getting the due date
        String dueDateRegEx = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        String dueDateStr = getDueDate();

        if (!dueDateStr.matches(dueDateRegEx)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect date format.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        String[] dueDateComponents = dueDateStr.split("/");
        Date dueDate = new Date(Integer.parseInt(dueDateComponents[0]), Integer.parseInt(dueDateComponents[1]), Integer.parseInt(dueDateComponents[2]));

        // getting the estimated time
        String timeStr = getTime();
        if (timeStr.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "No estimated time given.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        int time = Integer.parseInt(getTime());

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

        // actually creating the task
        Task newTask = new OneTimeTask(name, time, estimatedDifficulty, dueDate);

        // we will do this task one day before the duedate
        doingDate = dueDate.subtractDays(1);

        // load the data and save all the sessions that are on this "doing date"
        loadData(doingDate);
//        Log.v("adding task", "ADDED TASK TO " + doingDate.toString());

        // if there aren't any sessions on this date YET, then add one at 4pm
        if (daysSessions.size() == 0) {
            Time startTime = new Time(12+4, 0);
            Time endTime = startTime.add(0, time);
            Timeblock newTB = new Timeblock(startTime, endTime);
            Session newSession = new Session(newTask, doingDate, newTB);
            daysSessions.add(newSession);
        } // otherwise, add this session right after the previous session according to buffer times
        else {
            Session lastSession = daysSessions.get(daysSessions.size()-1);
            Time startTime = lastSession.getNextStartTime();
            Time endTime = startTime.add(0, time);
            Timeblock newTB = new Timeblock(startTime, endTime);
            Session newSession = new Session(newTask, doingDate, newTB);
            daysSessions.add(newSession);
        }

        // save this data
        saveData(v);

        // starting up another activity with an intent
        Intent intent;
        if (doingDate.equals(today)) { // if we're doing this today, then just make a new session for today
            intent = new Intent(this, TodaysSessions.class);
        } else { // otherwise, do the sessions on the doing date
            intent = new Intent(this, DaySessions.class );
            intent.putExtra("date", doingDate.toString());
        }
        startActivity(intent); // start the new activity

        // toast that we have added the task
        Toast toast = Toast.makeText(getApplicationContext(), "Adding Task...", Toast.LENGTH_LONG);
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

    private void loadData(Date doingDate) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        if (allSessions == null) {
            setUpSessionModels();
        }

        daysSessions = new ArrayList<Session>();

        for (int i = 0; i < allSessions.size(); i++) {
            if (allSessions.get(i).getDate().equals(doingDate)) {
                daysSessions.add(allSessions.get(i));
            }
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
