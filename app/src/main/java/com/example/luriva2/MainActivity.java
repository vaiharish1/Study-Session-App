package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    private NavigationBarView navigationBarView; // navigation bar

    private ArrayList<Session> allSessions; // all sessions (from shared preferences) and the day's sessions (to be displayed in the recycler view)

    private Date today; // today's date and the date of the task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.homeNavigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });

        // setting today's date by getting a calendar instance
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
        today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));

        // adding the sessions automatically
        loadData();

        // saving all the data in the app so that other screens just need to load it
        saveData();

        // remove the settings button as we decided that is outside of the scope
        Button settingsButton = findViewById(R.id.settings);
        settingsButton.setVisibility(View.GONE);
        // TODO: add settings button
    }

    public void todaysSessionsNav(View v){
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }

    public void viewCalNav(View v){
        // Log.d("vai","in viewCalNav method");
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

    public void timerNav(View v){
        Intent intent = new Intent(this,Timer.class);
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Timer", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onTitleClick(){
        Toast toast = Toast.makeText(getApplicationContext(), ":P", Toast.LENGTH_LONG);
        toast.show();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        if (allSessions == null) {
            // TODO: add popup screen that briefly introduces the app
            setUpSessionModels();
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Session> savedSessions = new ArrayList<Session>();
        for (int i = 0; i < allSessions.size(); i++) {
            savedSessions.add(allSessions.get(i));
        }

        String json = gson.toJson(savedSessions);
        editor.putString("session list", json);
        editor.apply();
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
