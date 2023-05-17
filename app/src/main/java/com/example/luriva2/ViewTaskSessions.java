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
import com.example.luriva2.recyclerViewClasses.Task_RecyclerViewAdapter;
import com.example.luriva2.recyclerViewClasses.ViewTasksSessions_RecyclerViewAdapter;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewTaskSessions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Task thisTask;
    private ArrayList<Session> thisTasksSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_sessions);

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

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String taskName = extras.getString("taskName");
            int estimatedTime = Integer.parseInt(extras.getString("estimatedTime"));

            int difficulty = Integer.parseInt(extras.getString("difficulty"));
            String taskType = extras.getString("taskType");

            String dueDateStr = extras.getString("dueDate");

            Date dueDate;
            if (dueDateStr == null || dueDateStr.isEmpty()) {
                dueDate = null;
            } else {
                String[] components = dueDateStr.split("/");
                dueDate = new Date(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2]));
            }

            thisTask = new Task(taskName, estimatedTime, difficulty, taskType, dueDate);
            thisTask.setEstimatedTime(estimatedTime);

            int amtSessions = Integer.parseInt(extras.getString("amtSessions"));
            thisTask.setAmtSessions(amtSessions);

            TextView taskNameText = findViewById(R.id.todaysSessionHeader);
            taskNameText.setText(thisTask.getTaskName());
        }

        Log.v("THIS TASK HERE", thisTask.toString());
        loadData();

        recyclerView = findViewById(R.id.recyclerView);

        createAdapter();
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

    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
        showToast("Viewing Task Manager...");
    }

    public void createAdapter() {
        ViewTasksSessions_RecyclerViewAdapter adapter = new ViewTasksSessions_RecyclerViewAdapter(this, thisTasksSessions);

        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("session list", null);
        Type type = new TypeToken<ArrayList<Session>>() {}.getType();
        ArrayList<Session> allSessions = gson.fromJson(json, type);

        thisTasksSessions = new ArrayList<>();

        for (Session s : allSessions) {
            Log.v("SESSION HERE", s.toString());
            if (s.getTask().equals(thisTask)) {
                thisTasksSessions.add(s);
            }
        }
    }
}