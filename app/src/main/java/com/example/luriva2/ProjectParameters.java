package com.example.luriva2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.luriva2.dataModelClasses.Constants;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Task;
import com.google.android.material.navigation.NavigationBarView;

public class ProjectParameters extends TaskParameters {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_parameters); // the layout to be used

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
        EditText taskNameText = findViewById(R.id.editTextTaskName_project);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }

    // getting the due date from the edittext
    public String getDueDate() {
        EditText dueDateText = findViewById(R.id.editTextDueDate_project);
        String dueDateStr = dueDateText.getText().toString();
        if (dueDateStr.isEmpty()) {
            dueDateStr = "";
        }
        return dueDateStr;
    }

    // getting the estimated time from the edittext
    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_project);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

    // when pressing the "easy" button, populating the difficulty text with "Easy"
    public void populateEasy(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Easy");
    }
    // when pressing the "medium" button, populating the difficulty text with "Medium"
    public void populateMid(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Medium");
    }
    // when pressing the "hard" button, populating the difficulty text with "Hard"
    public void populateHard(View v){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_project);
        difficultyText.setText("Hard");
    }

    // getting the estimated difficulty from the difficulty textview
    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_project);
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

        // getting the due date
        String dueDateStr = getDueDate();
        if (checkDueDate(dueDateStr)) return;
        Date dueDate = transformToDate(dueDateStr);

        // getting the estimated time
        String timeStr = getTime();
        if (checkEstimatedTime(timeStr)) return;
        int time = transformToTime(timeStr);

        // getting the difficulty of the task
        String difficulty = getDif();
        if (checkDifficulty(difficulty)) return;
        int estimatedDifficulty = transformToEstimatedDifficulty(difficulty);

        // actually creating the task
        Task newTask = new Task(name, time, estimatedDifficulty, "Project", dueDate);

        // finding the total amount of sessions
        int amtOfSessions = Math.floorDiv(newTask.getEstimatedTime(), Constants.MAX_SESSION_TIME);
        newTask.setAmtSessions(amtOfSessions);

        int addedDays = 1;
        int remainingTime = time;

        // adding the task
        addTask(newTask);

        // adding the sessions
        for (int i = 1; i <= amtOfSessions && remainingTime > 0; i++) {
            Date doingDate = getToday().addDays(addedDays);

            if (doingDate.compareTo(dueDate) > 0) break;

            int sessionTime = Math.min(remainingTime, Constants.MAX_SESSION_TIME);

            if (!addingSessions(doingDate, sessionTime, newTask, i)) {
                amtOfSessions--;
            } else {
                remainingTime -= sessionTime;
            }
            addedDays++;
        }

        // starting new intent
        Intent intent = new Intent(this, ViewCalendar.class);
        startActivity(intent);
        showToast("Viewing Calendar...");
    }
}
