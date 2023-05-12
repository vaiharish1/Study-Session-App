package com.example.luriva2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationBarView;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.OneTimeTask;
import com.example.luriva2.dataModelClasses.Task;

public class OneTimeParameters extends TaskParameters {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_parameters); // the layout to be used

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
        EditText taskNameText = findViewById(R.id.editTextTaskName_onetime);
        String taskNameStr = taskNameText.getText().toString();
        if (taskNameStr.isEmpty()) {
            taskNameStr = "";
        }
        return taskNameStr;
    }

    // getting the due date from the edittext
    public String getDueDate() {
        EditText dueDateText = findViewById(R.id.editTextDueDate_onetime);
        String dueDateStr = dueDateText.getText().toString();
        if (dueDateStr.isEmpty()) {
            dueDateStr = "";
        }
        return dueDateStr;
    }

    // getting the estimated time from the edittext
    public String getTime() {
        EditText timeText = findViewById(R.id.editTextEstTime_onetime);
        String timeStr = timeText.getText().toString();
        if (timeStr.isEmpty()) {
            timeStr = "";
        }
        return timeStr;
    }

    // when pressing the "easy" button, populating the difficulty text with "Easy"
    public void populateEasy(View v) {
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
        difficultyText.setText(R.string.lowDifficultyButton);
    }
    // when pressing the "medium" button, populating the difficulty text with "Medium"
    public void populateMid(View v) {
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
        difficultyText.setText(R.string.midDifficultyButton);
    }
    // when pressing the "hard" button, populating the difficulty text with "Hard"
    public void populateHard(View v) {
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
        difficultyText.setText(R.string.highDifficultyButton);
    }

    // getting the estimated difficulty from the difficulty textview
    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_onetime);
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
        Task newTask = new OneTimeTask(name, time, estimatedDifficulty, dueDate);

        // we will do this task one day before the due date
        Date doingDate = dueDate.subtractDays(1);
        Date today = getToday();

        while (!addingSessions(doingDate, time, newTask)) {
            doingDate = doingDate.subtractDays(1);
            if (doingDate.compareTo(today) < 0) break;
        }

        // starting up another activity with an intent
        Intent intent;
        if (doingDate.equals(today)) { // if we're doing this today, then just make a new session for today
            intent = new Intent(this, TodaysSessions.class);
        } else { // otherwise, do the sessions on the doing date
            intent = new Intent(this, DaySessions.class );
            intent.putExtra("date", doingDate.toString());
        }
        startActivity(intent); // start the new activity

        // toast that we have added the task
        Toast toast = Toast.makeText(getApplicationContext(), "Adding Task...", Toast.LENGTH_LONG);
        toast.show();
    }
}
