package com.example.luriva2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Time;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Session> allSessions, todaySessions; // all sessions (from shared preferences) and the day's sessions (to be displayed in the recycler view)

    private ArrayList<Task> allTasks; // all tasks

    private Date today; // today's date and the date of the task

    private Time curTime; // the current time

    private ConstraintLayout layout; // the constraint layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting up the navigation bar
        NavigationBarView navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.homeNavigation);
        navigationBarView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.timerNavigation:
                    startActivity(new Intent(getApplicationContext(),Timer.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.homeNavigation:
                    return true;
                case R.id.viewTasksNavigation:
                    startActivity(new Intent(getApplicationContext(),TodaysSessions.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });

        layout = findViewById(R.id.mainConstraintLayout); // getting the layout

        // setting today's date by getting a calendar instance
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
        today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));

        // get the current time
        sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        formattedDate = sdf.format(c.getTime());
        components = formattedDate.split(":");
        curTime = new Time(Integer.parseInt(components[0]), Integer.parseInt(components[1]));

        // adding the sessions automatically
        loadData();

        // saving all the data in the app so that other screens just need to load it
        saveData();

        // remove the settings button as we decided that is outside of the scope
        Button settingsButton = findViewById(R.id.settings);
        settingsButton.setVisibility(View.GONE);

        // check sessions and start timer when needed
        checkSessionsStartTimer();
    }

    // check all sessions and start timer if needed
    public void checkSessionsStartTimer() {
        boolean flag = true; // if timer not started

        // for all sessions
        for (int i = 0; i < todaySessions.size(); i++) {
            Session sesh = todaySessions.get(i); // get the session
            if (sesh.getTimeblock().contains(curTime)) { // if it contains current time
                showToast("You're currently in a session."); // tell to user

                // delay going to timer
                new Handler().postDelayed(() -> {
                    Intent mainIntent = new Intent(MainActivity.this, Timer.class);
                    MainActivity.this.startActivity(mainIntent);
                    finish();
                }, 2000);

                // set flag to false
                flag = false;
            }
        }
        // if there's no session, say the user is on a break
        if (flag) {
            showToast("You're on a break!");
        }
    }

    // custom toast message
    public void showToast(String str) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.navigation_bar_toast, (ViewGroup) findViewById(R.id.toastLayoutRoot));

        TextView text = (TextView) layout.findViewById(R.id.toastText);
        text.setText(str);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    // go today's sessions
    public void todaysSessionsNav(View v){
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        showToast("Viewing Today's Sessions...");
    }

    // view the calendar
    public void viewCalNav(View v){
        Intent intent = new Intent(this, ViewCalendar.class );
        startActivity(intent);
        showToast("Viewing Calendar...");
    }

    // view all tasks
    public void viewAllTasks(View v) {
        Intent intent = new Intent(this, AllTasks.class );
        startActivity(intent);
        showToast("Viewing All Tasks...");
    }

    // go to add tasks page
    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
        showToast("Viewing Task Manager...");
    }

    // go to settings page (not used)
    public void settingsNav(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        showToast("Viewing Settings...");
    }

    // go to timer page
    public void timerNav(View v){
        Intent intent = new Intent(this,Timer.class);
        startActivity(intent);
        showToast("Viewing Timer...");
    }

    // create popup window if user is starting for the first time
    private void createPopupWindow() {
        // get the inflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.startup_popup_window, null);

        // get width and height
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        // get the popup window
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        layout.post(() -> popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0));
        popUpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    // loading data from shared preferences
    private void loadData() {
        // getting shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        // getting all sessions
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        // getting all tasks
        json = sharedPreferences.getString("task list", null);
        type = new TypeToken<ArrayList<Task>>() {}.getType();
        allTasks = gson.fromJson(json, type);

        todaySessions = new ArrayList<>();

        // if either of these are null, then create them from the strings menu
        if (allTasks == null || allSessions == null) {
            createPopupWindow();
            setUpSessionModels();
        }

        // if they're part of today's sessions, then add it to session models
        for (Session s : allSessions) {
            if (s.getDate().equals(today)) {
                todaySessions.add(s);
            }
        }
    }

    // save data
    public void saveData() {
        // getting shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // get all the saved sessions
        ArrayList<Session> savedSessions = new ArrayList<>(allSessions);
        String json = gson.toJson(savedSessions);
        editor.putString("session list", json);
        editor.apply();

        // put all tasks in shared references
        ArrayList<Task> savedTasks = new ArrayList<>(allTasks);
        json = gson.toJson(savedTasks);
        editor.putString("task list", json);
        editor.apply();
    }

    // set up session models if not made
    public void setUpSessionModels() {
        // initialize the array lists
        allSessions = new ArrayList<>();
        allTasks = new ArrayList<>();

        // get all string arrays from string resources
        String[] sessionNames = getResources().getStringArray(R.array.session_names);
        String[] sessionTypes = getResources().getStringArray(R.array.session_types);
        String[] sessionStartTimesHours = getResources().getStringArray(R.array.session_start_times_hours);
        String[] sessionStartTimesMinutes = getResources().getStringArray(R.array.session_start_times_minutes);
        String[] sessionEndTimesHours = getResources().getStringArray(R.array.session_end_times_hours);
        String[] sessionEndTimesMinutes = getResources().getStringArray(R.array.session_end_times_minutes);

        // go through them one at a time
        for (int i = 0; i < sessionNames.length; i++) {
            // get the time block
            Time startTime = new Time(Integer.parseInt(sessionStartTimesHours[i]), Integer.parseInt(sessionStartTimesMinutes[i]));
            Time endTime = new Time(Integer.parseInt(sessionEndTimesHours[i]), Integer.parseInt(sessionEndTimesMinutes[i]));
            Timeblock tb = new Timeblock(startTime, endTime);

            // get the date
            Date d;
            if (sessionTypes[i].equals("Repetitive")) d = null;
            else if (sessionTypes[i].equals("Project")) d = new Date(5, 23, 2023);
            else d = new Date(5, 13, 2023);

            // initialize the task
            Task t = new Task(sessionNames[i], 10, 2, sessionTypes[i], d);
            t.setAmtSessions(1);

            // add them to the array lists
            allTasks.add(t);
            allSessions.add(new Session(t, today, tb, 1));
        }
    }
}

