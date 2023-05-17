package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DisplaySessions extends AppCompatActivity {

    private ArrayList<Session> allSessions, sessionModels; // sessions for ALL and sessions for a certain day

    private ArrayList<Task> allTasks; // all tasks
    private Date today; // today's date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

        // get today's date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(c.getTime());
        String[] components = formattedDate.split("-");
        today = new Date(Integer.parseInt(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));

        // disable the save button
        makeSaveButtonDisabled();
    }

    // get session models
    public ArrayList<Session> getSessionModels() {
        return sessionModels;
    }

    // get today's date
    public Date getToday() {
        return today;
    }

    // make the save button disabled
    public void makeSaveButtonDisabled() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(false);
    }

    // make the save button enabled
    public void makeSaveButtonEnabled() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(true);
    }

    // save data if a particular date is modified
    public void saveData(Date changedThisDate) {
        // getting shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // get all the saved sessions
        ArrayList<Session> savedSessions = new ArrayList<>();
        for (int i = 0; i < allSessions.size(); i++) {
            if (!allSessions.get(i).getDate().equals(changedThisDate)) {
                savedSessions.add(allSessions.get(i));
            }
        }

        // add all of them
        savedSessions.addAll(sessionModels);

        String json = gson.toJson(savedSessions);

        // put it in shared references
        editor.putString("session list", json);
        editor.apply();

        // put all tasks in shared references
        ArrayList<Task> savedTasks = new ArrayList<>(allTasks);
        json = gson.toJson(savedTasks);
        editor.putString("task list", json);
        editor.apply();

        makeSaveButtonDisabled();
    }

    // loading data from shared preferences
    public void loadData(Date theDay) {
        sessionModels = new ArrayList<>();

        // getting shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        // getting all sessions
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        // if they're in the specified date, then add it to session models
        for (Session s : allSessions) {
            if (s.getDate().equals(theDay)) {
                sessionModels.add(s);
            }
        }

        // getting all tasks
        json = sharedPreferences.getString("task list", null);
        type = new TypeToken<ArrayList<Task>>() {}.getType();
        allTasks = gson.fromJson(json, type);
    }
}