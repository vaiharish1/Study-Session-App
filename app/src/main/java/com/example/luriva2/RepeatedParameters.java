package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

    public String getIncrement() {
        EditText incrementText = findViewById(R.id.editTextIncrementValue_repeated);
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
        EditText timeText = findViewById(R.id.editTextEstTime_repeated);
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
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Easy");
    }
    public void populateMid(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Medium");
    }
    public void populateHard(){
        TextView difficultyText = findViewById(R.id.TextViewdifficulty_repeated);
        difficultyText.setText("Hard");
    }


    public void onClickEasy(View v){
        populateEasy();
        collectDif();
    }
    public void onClickMedium(View v){
        populateMid();
        collectDif();
    }
    public void onCLickHard(View v){
        populateHard();
        collectDif();
    }
    public String getDif() {
        TextView difText = findViewById(R.id.TextViewdifficulty_repeated);
        String difStr = difText.getText().toString();
        if (difStr.isEmpty()) {
            difStr = "";
        }
        return difStr;
    }

    public void collectDif() {
        String dif = getDif();
        Toast toast = Toast.makeText(getApplicationContext(), "Difficulty: " + dif, Toast.LENGTH_LONG);
        toast.show();

    }

    public void todaysSessionsNav(View v){
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }

}