package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import com.example.luriva2.recyclerViewClasses.Task_RecyclerViewAdapter;
import com.example.luriva2.recyclerViewClasses.ViewTasksRecyclerViewInterface;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class AllTasks extends AppCompatActivity implements ViewTasksRecyclerViewInterface {

    private ArrayList<Session> allSessions;

    private ArrayList<Task> allTasks;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        // navigation bar
        NavigationBarView navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.viewTasksNavigation);
        navigationBarView.setOnItemSelectedListener(item -> {
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
        });

        loadTasks();

        checkAllTasks();

        saveTasks();

        recyclerView = findViewById(R.id.recyclerView);

        createAdapter();
    }

    public void createAdapter() {
        Task_RecyclerViewAdapter adapter = new Task_RecyclerViewAdapter(this, allTasks, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        allSessions = gson.fromJson(json, type);

        json = sharedPreferences.getString("task list", null);
        type = new TypeToken<ArrayList<Task>>() {}.getType();
        allTasks = gson.fromJson(json, type);
    }

    public void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Session> savedSessions = new ArrayList<>(allSessions);

        String json = gson.toJson(savedSessions);
        editor.putString("session list", json);
        editor.apply();

        ArrayList<Task> savedTasks = new ArrayList<>(allTasks);
        json = gson.toJson(savedTasks);
        editor.putString("task list", json);
        editor.apply();
    }

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

    public void checkAllTasks() {
        for (int index = 0; index < allTasks.size(); index++) {
            ArrayList<Session> tasksSessions = getTasksSessions(allTasks.get(index));
            int amtSessions = tasksSessions.size();

            if (amtSessions == 0) {
                allTasks.remove(index);
                index--;
            } else {
                allTasks.get(index).setAmtSessions(amtSessions);

                for (int sessionIndex = 0; sessionIndex < tasksSessions.size(); sessionIndex++) {
                    Session sesh = tasksSessions.get(sessionIndex);
                    int allSessionIndex = allSessions.indexOf(sesh);
                    allSessions.remove(allSessionIndex);
                    sesh.setSessionId(sessionIndex);
                    allSessions.add(allSessionIndex, sesh);
                }
            }
        }
    }

    public ArrayList<Session> getTasksSessions(Task t) {
        ArrayList<Session> taskSessions = new ArrayList<>();

        for (Session s : allSessions) {
            if (s.getTask().equals(t)) {
                taskSessions.add(s);
            }
        }

        Log.v("GET TASKS SESSIONS", taskSessions.toString());
        Collections.sort(taskSessions);
        return taskSessions;
    }

    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
        showToast("Viewing Task Manager...");
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AllTasks.this, ViewTaskSessions.class);

        intent.putExtra("taskName", allTasks.get(position).getTaskName());
        intent.putExtra("estimatedTime", Integer.toString(allTasks.get(position).getEstimatedTime()));
        intent.putExtra("difficulty", Integer.toString(allTasks.get(position).getDifficulty()));
        intent.putExtra("taskType", allTasks.get(position).getTaskType());

        if (allTasks.get(position).getDueDate() == null) {
            intent.putExtra("dueDate", "");
        } else {
            intent.putExtra("dueDate", allTasks.get(position).getDueDate().toString());
        }

        intent.putExtra("amtSessions", Integer.toString(allTasks.get(position).getAmtSessions()));

        startActivity(intent);
    }
}