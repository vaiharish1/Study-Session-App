package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TodaysSessions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Session> sessionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

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
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        sessionModels = gson.fromJson(json, type);

        if (sessionModels == null) {
            setUpSessionModels();
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
                    sessionModels.add(position + 1, session);
                    Log.v("PRESSED BUTTON", "DOWN");
                } else if (v.getId() == R.id.upButton) {
                    Session session = sessionModels.remove(position);
                    sessionModels.add(position - 1, session);
                    Log.v("PRESSED BUTTON", "UP");
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
        String[] sessionStartTimes = getResources().getStringArray(R.array.session_start_times);
        String[] sessionEndTimes = getResources().getStringArray(R.array.session_end_times);
        String[] sessionTypes = getResources().getStringArray(R.array.session_types);

        for (int i = 0; i < sessionNames.length; i++) {
            sessionModels.add(new Session(sessionNames[i], Integer.parseInt(sessionStartTimes[i]), Integer.parseInt(sessionEndTimes[i]), sessionTypes[i]));
            Log.v("MODELS", sessionModels.get(i).toString());
        }
    }

}