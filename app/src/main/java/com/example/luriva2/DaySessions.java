package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Time;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DaySessions extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Session> allSessions, sessionModels;
    private Date thisDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String dateDisplay = extras.getString("date");

            String[] components = dateDisplay.split("/");
            thisDay = new Date(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2]));

            TextView daySessionsHeader = findViewById(R.id.todaysSessionHeader);
            daySessionsHeader.setText("Sessions For: " + thisDay.toString());
        }

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

        ArrayList<Session> savedSessions = new ArrayList<Session>();
        for (int i = 0; i < allSessions.size(); i++) {
            if (allSessions.get(i).getDate().equals(thisDay)) {
                continue;
            } else {
                savedSessions.add(allSessions.get(i));
            }
        }

        for (int i = 0; i < sessionModels.size(); i++) {
            savedSessions.add(sessionModels.get(i));
        }

        String json = gson.toJson(savedSessions);

        editor.putString("session list", json);
        editor.apply();

        makeSaveButtonDisabled();
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

    private void loadData() {
        sessionModels = new ArrayList<Session>();

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        for (Session s : allSessions) {
//            Log.v("all sessions here", "adding these sessions: " + s.toString());
            if (s.getDate().equals(thisDay)) {
                sessionModels.add(s);
//                Log.v("TODAYS SESSION ADDING", "adding these sessions: " + s.toString());
            }
        }
    }
}