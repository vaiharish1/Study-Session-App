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
    TextView status;
    TextView timer;
    TextView motivationalQuote;
    CountDownTimer countdown;
    NavigationBarView navigationBarView;

    private ArrayList<Session> allSessions, todaySessions; // all sessions (from shared preferences) and the day's sessions (to be displayed in the recycler view)

    private Date today; // today's date and the date of the task

    private Time curTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

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

        // getting the current time
        sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(c.getTime());
        components = formattedTime.split(":");
        curTime = new Time(Integer.parseInt(components[0]), Integer.parseInt(components[1]));

        checkSessionsStartTimer();
    }

    private void checkSessionsStartTimer() {
        boolean flag = true;
        for (int i = 0; i < todaySessions.size(); i++) {
            Session sesh = todaySessions.get(i);
            if (sesh.getTimeblock().contains(curTime)) {
                Task currentTask = sesh.getTask();
                status.setText("You're working on: " + currentTask.getTaskName());

                Time endTime = sesh.getTimeblock().getEndTime();
                int hours = endTime.getHour() - curTime.getHour();
                int minutes = endTime.getMinute() - curTime.getMinute();
                if (minutes < 0) {
                    minutes += 60;
                    hours--;
                }
                Time timeDif = new Time(hours, minutes);
                int totalMinutesRemaining = timeDif.getHour() * 60 + timeDif.getMinute();
                flag = false;
                startTimer(totalMinutesRemaining);
            }
        }
        if (flag) status.setText("You're on a break!");
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);
        todaySessions = new ArrayList<>();

        for (int i = 0; i < allSessions.size(); i++) {
            if (allSessions.get(i).getDate().equals(today)) {
                todaySessions.add(allSessions.get(i));
                Log.v("TASK IN TIMER", allSessions.get(i).toString());
            }
        }
    }

    public void startTimer(int min){
        countdown = new CountDownTimer((long) min *60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(msToMin(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                String[] motivation = getResources().getStringArray(R.array.motivationQuotes);
                int motivationLen = motivation.length;
                Random randy = new Random();
                motivationalQuote.setText(motivation[randy.nextInt(motivationLen)]);
                startTimer(min);
            }

        };
        countdown.start();
    }

    private String msToMin(long ms) {
        int sec = (int)(ms/1000)%60;
        int min = (int)(ms/1000)/60;
        return String.format("%02d",min) + ":" + String.format("%02d",sec);
    }

    public void cancel(View v){
        status.setText(R.string.cancelledStr);
        countdown.cancel();
    }
}