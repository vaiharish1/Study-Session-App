package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luriva2.dataModelClasses.Constants;
import com.example.luriva2.dataModelClasses.OneTimeTask;
import com.example.luriva2.dataModelClasses.RepetitiveTask;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProjectParameters extends AppCompatActivity {

    private ArrayList<Session> sessionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_parameters);
    }

    public String getTaskName() {
        EditText taskNameText = findViewById(R.id.editTextTaskName_project);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }


    public void collectTaskName(View v) {
        String taskName = getTaskName();
        Toast toast = Toast.makeText(getApplicationContext(), "Task Name: " + taskName, Toast.LENGTH_LONG);
        toast.show();
    }

    public String getDueDate() {
        EditText dueDateText = findViewById(R.id.editTextDueDate_project);
        String dueDateStr = dueDateText.getText().toString();
        if (dueDateStr.isEmpty()) {
            dueDateStr = "";
        }
        return dueDateStr;
    }

    public void collectDueDate(View v) {
        String dueDate = getDueDate();
        Toast toast = Toast.makeText(getApplicationContext(), "Due Date: " + dueDate, Toast.LENGTH_LONG);
        toast.show();
    }

    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_onetime);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

    public void collectTime(View v) {
        String time = getTime();
        Toast toast = Toast.makeText(getApplicationContext(), "Estimated time: " + time, Toast.LENGTH_LONG);
        toast.show();

    }

    public void populateEasy(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Easy");
    }
    public void populateMid(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Medium");
    }
    public void populateHard(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Hard");
    }


    public void onClickEasy(View v){
        populateEasy();
        collectDif(v);
    }
    public void onClickMedium(View v){
        populateMid();
        collectDif(v);
    }
    public void onCLickHard(View v){
        populateHard();
        collectDif(v);
    }
    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_project);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

    public void collectDif(View v) {
        String dif = getDif();
        Toast toast = Toast.makeText(getApplicationContext(), "Difficulty: " + dif, Toast.LENGTH_LONG);
        toast.show();

    }

    public void todaysSessionsNav(View v){
        String name = getTaskName();
        int dueDate = Integer.parseInt(getDueDate());

        String difficulty = getDif();
        int estimatedDifficulty;
        if (difficulty.equals("Easy")) estimatedDifficulty = 3;
        else if (difficulty.equals("Medium")) estimatedDifficulty = 2;
        else if (difficulty.equals("Hard")) estimatedDifficulty = 1;
        else estimatedDifficulty = 4;

        Task newTask = new OneTimeTask(name, dueDate, estimatedDifficulty);

        loadData();
        sessionModels.add(new Session(newTask.getName(), sessionModels.get(sessionModels.size()-1).getEndTime() + Constants.BUFFER_TIME, sessionModels.get(sessionModels.size()-1).getEndTime() + Constants.BUFFER_TIME + newTask.getEstimatedTime()/newTask.getDifficulty(), "Project"));
        saveData(v);

        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }

    public void saveData(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sessionModels);
        editor.putString("session list", json);
        editor.apply();
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
