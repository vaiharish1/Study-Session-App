package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DisplaySessions extends AppCompatActivity {

    private ArrayList<Session> allSessions, sessionModels;
    private Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
        today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));

//        loadData(today);
        makeSaveButtonDisabled();
    }

    public ArrayList<Session> getSessionModels() {
        return sessionModels;
    }

    public Date getToday() {
        return today;
    }

    public void makeSaveButtonDisabled() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(false);
    }

    public void makeSaveButtonEnabled() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(true);
    }

    public void saveData(Date changedThisDate) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Session> savedSessions = new ArrayList<Session>();
        for (int i = 0; i < allSessions.size(); i++) {
            if (allSessions.get(i).getDate().equals(changedThisDate)) {
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

    public void loadData(Date theDay) {
        sessionModels = new ArrayList<Session>();

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        for (Session s : allSessions) {
            Log.v("all sessions here", "adding these sessions: " + s.toString());
            if (s.getDate().equals(theDay)) {
                sessionModels.add(s);
                Log.v("TODAYS SESSION ADDING", "adding these sessions: " + s.toString());
            }
        }
    }
}