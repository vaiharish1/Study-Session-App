package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Time;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Timer extends AppCompatActivity {
    TextView status; // the status of the timer
    TextView timer; // the timer screen
    TextView motivationalQuote; // the motivation quote
    CountDownTimer countdown; // countdown
    NavigationBarView navigationBarView; // the navigation bar

    private ArrayList<Session> todaySessions; // all sessions (from shared preferences) and the day's sessions (to be displayed in the recycler view)

    private Date today; // today's date

    private Time curTime; // the current time

    boolean onBreak = false; // if we are on a break or not

    private Task curTask; // the current task
    private Session curSession; // the current session

    private ArrayList<Task> allTasks; // all tasks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // setting up the navigation bar
        navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.timerNavigation);
        navigationBarView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.timerNavigation:
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
        });

        // setting today's date by getting a calendar instance
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
        today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));

        loadData(); // loading all the sessions

        // setting up the timer
        status = findViewById(R.id.timerStatus);
        timer = findViewById(R.id.timer);
        motivationalQuote = findViewById(R.id.motivationalQuote);

        // getting a random motivational quote
        String[] motivation = getResources().getStringArray(R.array.motivationQuotes);
        int motivationLen = motivation.length;
        Random randy = new Random();
        motivationalQuote.setText(motivation[randy.nextInt(motivationLen)]);

        // getting the current time
        sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(c.getTime());
        components = formattedTime.split(":");
        curTime = new Time(Integer.parseInt(components[0]), Integer.parseInt(components[1]));

        // check sessions and start the timer
        checkSessionsStartTimer();
    }

    // check all sessions and start timer if necessary
    private void checkSessionsStartTimer() {
        boolean flag = true;

        // for all sessions
        for (int i = 0; i < todaySessions.size(); i++) {
            Session sesh = todaySessions.get(i);
            // if the timeblock contains current time
            if (sesh.getTimeblock().contains(curTime)) {
                // show the session
                curSession = sesh;
                curTask = sesh.getTask();
                status.setText("You're working on: " + curTask.getTaskName());

                // get end time
                Time endTime = sesh.getTimeblock().getEndTime();
                int hours = endTime.getHour() - curTime.getHour();
                int minutes = endTime.getMinute() - curTime.getMinute();
                if (minutes < 0) {
                    minutes += 60;
                    hours--;
                }

                // get time difference
                Time timeDif = new Time(hours, minutes);
                // get minutes remaining
                int totalMinutesRemaining = timeDif.getHour() * 60 + timeDif.getMinute();

                // you're not on break
                flag = false;
                onBreak = false;

                // start the timer
                startTimer(totalMinutesRemaining);
            }
        }
        // otherwise, you're on break
        if (flag) {
            status.setText("You're on a break!");
            onBreak = true;
        }
    }

    // load all the sessions
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        ArrayList<Session> allSessions = gson.fromJson(json, type);
        todaySessions = new ArrayList<>();

        for (int i = 0; i < allSessions.size(); i++) {
            if (allSessions.get(i).getDate().equals(today)) {
                todaySessions.add(allSessions.get(i));
                Log.v("TASK IN TIMER", allSessions.get(i).toString());
            }
        }

        json = sharedPreferences.getString("task list", null);
        type = new TypeToken<ArrayList<Task>>() {}.getType();
        allTasks = gson.fromJson(json, type);
    }

    // save the sessions
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Task> savedTasks = new ArrayList<>(allTasks);
        String json = gson.toJson(savedTasks);
        editor.putString("task list", json);
        editor.apply();
    }

    // start the timer
    public void startTimer(int min){
        // initialize new countdown timer
        countdown = new CountDownTimer((long) min *60*1000,1000) {

            // count down
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(msToMin(millisUntilFinished));
            }

            // when finished
            @Override
            public void onFinish() {
                // remove the task if finished
                if (curSession.getSessionId() == curTask.getAmtSessions()) {
                    allTasks.remove(curTask);
                    saveData();
                }

                // get the motivational quote!
                String[] motivation = getResources().getStringArray(R.array.motivationQuotes);
                int motivationLen = motivation.length;
                Random randy = new Random();
                motivationalQuote.setText(motivation[randy.nextInt(motivationLen)]);
            }
        };

        // start the countdown timer
        countdown.start();
    }

    // turn milliseconds to minutes
    private String msToMin(long ms) {
        int sec = (int)(ms/1000)%60;
        int min = (int)(ms/1000)/60;
        return String.format("%02d",min) + ":" + String.format("%02d",sec);
    }

    // cancel (or pause) the timer
    public void cancel(View v){
        // only do it if you're on a break
        if (!onBreak) {
            status.setText(R.string.cancelledStr);
            countdown.cancel();
        }
    }
}