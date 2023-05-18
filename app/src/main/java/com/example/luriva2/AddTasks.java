package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationBarView;

public class AddTasks extends AppCompatActivity {
    NavigationBarView navigationBarView; // getting the navigation bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        // setting up the navigation bar
        navigationBarView = findViewById(R.id.navigationView);
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

    // custom toast message
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

    // going to one time task parameters
    public void oneTimeNav(View v){
        Intent intent = new Intent(this, OneTimeParameters.class );
        startActivity(intent);
        showToast("Viewing One-Time Task Manager...");
    }

    // going to repetitive task parameters
    public void repeatedNav(View v){
        Intent intent = new Intent(this, RepeatedParameters.class );
        startActivity(intent);
        showToast("Viewing Repeated Task Manager...");
    }

    // going to project task parameters
    public void projectNav(View v){
        Intent intent = new Intent(this, ProjectParameters.class );
        startActivity(intent);
        showToast("Viewing Project Task Manager...");
    }
}