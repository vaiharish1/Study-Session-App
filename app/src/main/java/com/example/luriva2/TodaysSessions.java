package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Time;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class TodaysSessions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Session> sessionModels;
    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);
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
        loadData();

        recyclerView = findViewById(R.id.recyclerView);

        createAdapter();

        makeSaveButtonDisabled();
    }

    private void makeSaveButtonDisabled() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(false);
    }

    private void makeSaveButtonEnabled() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(true);
    }

    public void saveData(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sessionModels);
        editor.putString("session list", json);
        editor.apply();

        makeSaveButtonDisabled();
    }

    private void loadData() {
        sessionModels = new ArrayList<Session>();

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        ArrayList<Session> allSessions = gson.fromJson(json, type);

        if (allSessions == null) {
            setUpSessionModels();
        } else {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = sdf.format(c.getTime());
            String[] components = formattedDate.split("-");
            Date today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));

            for (Session s : allSessions) {
                if (s.getDate().equals(today)) {
                    sessionModels.add(s);
                }
            }
        }
    }

    public void createAdapter() {
        Session_RecyclerViewAdapter adapter = new Session_RecyclerViewAdapter(this, sessionModels);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new Session_RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (v.getId() == R.id.downButton) {
                    Session session = sessionModels.remove(position);

                    Session curPosition = sessionModels.get(position);
                    Timeblock temp = curPosition.getTimeblock();
                    curPosition.setTimeblock(session.getTimeblock());
                    session.setTimeblock(temp);

                    sessionModels.add(position + 1, session);
//                    Log.v("PRESSED BUTTON", "DOWN");
                } else if (v.getId() == R.id.upButton) {
                    Session session = sessionModels.remove(position);

                    Session curPosition = sessionModels.get(position-1);
                    Timeblock temp = curPosition.getTimeblock();
                    curPosition.setTimeblock(session.getTimeblock());
                    session.setTimeblock(temp);

                    sessionModels.add(position - 1, session);
//                    Log.v("PRESSED BUTTON", "UP");
                }

                adapter.notifyDataSetChanged();
                makeSaveButtonEnabled();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setUpSessionModels() {
        sessionModels = new ArrayList<Session>();
        String[] sessionNames = getResources().getStringArray(R.array.session_names);
        String[] sessionTypes = getResources().getStringArray(R.array.session_types);

        String[] sessionStartTimesHours = getResources().getStringArray(R.array.session_start_times_hours);
        String[] sessionStartTimesMinutes = getResources().getStringArray(R.array.session_start_times_minutes);
        String[] sessionEndTimesHours = getResources().getStringArray(R.array.session_end_times_hours);
        String[] sessionEndTimesMinutes = getResources().getStringArray(R.array.session_end_times_minutes);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
//        Log.v("TODAYS DATE", formattedDate);

        for (int i = 0; i < sessionNames.length; i++) {
            Time startTime = new Time(Integer.parseInt(sessionStartTimesHours[i]), Integer.parseInt(sessionStartTimesMinutes[i]));
            Time endTime = new Time(Integer.parseInt(sessionEndTimesHours[i]), Integer.parseInt(sessionEndTimesMinutes[i]));
            Timeblock tb = new Timeblock(startTime, endTime);
            Task t = new Task(sessionNames[i], 50, 2, sessionTypes[i]);
            sessionModels.add(new Session(t, new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2])), tb));
//            Log.v("MODELS", sessionModels.get(i).toString());
        }
    }
}
