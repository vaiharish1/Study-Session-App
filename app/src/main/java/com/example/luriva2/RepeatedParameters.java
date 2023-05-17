package com.example.luriva2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luriva2.dataModelClasses.Constants;
import com.google.android.material.navigation.NavigationBarView;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Task;

public class RepeatedParameters extends TaskParameters {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_parameters); // the layout to be used

        // all the things with the navigation bar
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

    // getting the task name from the edittext
    public String getTaskName() {
        EditText taskNameText = findViewById(R.id.editTextTaskName_repeated);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }

    // getting the "how often" from the edittext
    public String getIncrement() {
        EditText incrementText = findViewById(R.id.editTextIncrementValue_repeated);
        String incrementStr = incrementText.getText().toString();
        if (incrementStr.isEmpty()) {
            incrementStr = "";
        }
        return incrementStr;
    }

    // getting the estimated time from the edittext
    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_repeated);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

    // when pressing the "easy" button, populating the difficulty text with "Easy"
    public void populateEasy(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Easy");
    }
    // when pressing the "medium" button, populating the difficulty text with "Medium"
    public void populateMid(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Medium");
    }
    // when pressing the "hard" button, populating the difficulty text with "Hard"
    public void populateHard(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Hard");
    }

    // getting the estimated difficulty from the difficulty textview
    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_repeated);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

    // when adding the task, we get all the required information (and catch for errors) and then add the task
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
        int addedDays = 0;

        // adding the sessions
        for (int i = 0; i < Constants.MAX_REPEATED_SESSIONS; i++) {
            Date doingDate = today.addDays(addedDays);

            while (!addingSessions(doingDate, time, newTask, 0)) {
                addedDays++;
                doingDate = today.addDays(i * howOften);
            }

            addedDays += howOften;
        }

        // starting new activity
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        showToast("Viewing Today's Sessions...");
    }
}
