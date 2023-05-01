package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class OneTimeParameters extends AppCompatActivity {
    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_parameters);

        navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.viewTasksNavigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });
    }

        public String getTaskName() {
            EditText taskNameText = findViewById(R.id.editTextTaskName_onetime);
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
            EditText dueDateText = findViewById(R.id.editTextDueDate_onetime);
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
            TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
            difficultyText.setText("Easy");
        }
        public void populateMid(){
            TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
            difficultyText.setText("Medium");
        }
        public void populateHard(){
            TextView difficultyText = findViewById(R.id.TextViewdifficulty_onetime);
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
        public void onClickHard(View v){
            populateHard();
            collectDif(v);
        }
        public String getDif() {
            TextView difText = findViewById(R.id.TextViewdifficulty_onetime);
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
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }

    }
