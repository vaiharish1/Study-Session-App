package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RepeatedParameters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_parameters);
    }

    public String getTaskName() {
        EditText taskNameText = findViewById(R.id.editTextTaskName_repeated);
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
        EditText dueDateText = findViewById(R.id.editTextTaskName_repeated);
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

    public String getIncrement() {
        EditText incrementText = findViewById(R.id.editTextTaskName_repeated);
        String incrementStr = incrementText.getText().toString();
        if (incrementStr.isEmpty()) {
            incrementStr = "";
        }
        return incrementStr;
    }

    public void collectIncrement(View v) {
        String increment = getIncrement();
        Toast toast = Toast.makeText(getApplicationContext(), "Increment by: " + increment + " days", Toast.LENGTH_LONG);
        toast.show();

    }

    public String getTime() {
        EditText timeText = findViewById(R.id.editTextTaskName_repeated);
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


}