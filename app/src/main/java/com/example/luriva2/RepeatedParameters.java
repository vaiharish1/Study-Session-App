package com.example.luriva2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Task;

public class RepeatedParameters extends TaskParameters {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_parameters);
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
    }

    public String getTaskName() {
        EditText taskNameText = findViewById(R.id.editTextTaskName_repeated);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }

    public String getIncrement() {
        EditText incrementText = findViewById(R.id.editTextIncrementValue_repeated);
        String incrementStr = incrementText.getText().toString();
        if (incrementStr.isEmpty()) {
            incrementStr = "";
        }
        return incrementStr;
    }

    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_repeated);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

    public void populateEasy(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Easy");
    }
    public void populateMid(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Medium");
    }
    public void populateHard(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Hard");
    }

    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_repeated);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

    public void todaysSessionsNav(View v){
        // getting the task name
        String name = getTaskName();
        if (checkTaskName(name)) return;

        // getting how often to repeat
        String howOftenStr = getIncrement();
        if (!checkHowOften(howOftenStr)) return;
        int howOften = transformToHowOften(howOftenStr);

        // getting the estimated time
        String timeStr = getTime();
        if (checkEstimatedTime(timeStr)) return;
        int time = transformToTime(timeStr);

        // getting the difficulty of the task
        String difficulty = getDif();
        if (checkDifficulty(difficulty)) return;
        int estimatedDifficulty = transformToEstimatedDifficulty(difficulty);

        // actually creating the task
        Task newTask = new Task(name, time, estimatedDifficulty, "Repetitive", null);
        newTask.setAmtSessions(-1);

        // adding the task
        addTask(newTask);

        Date today = getToday();

        for (int i = 0; i < 30; i++) {
            Date doingDate = today.addDays(i * howOften);
            addingSessions(doingDate, time, newTask, 0);
        }

        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        showToast("Viewing Today's Sessions...");
    }
}
