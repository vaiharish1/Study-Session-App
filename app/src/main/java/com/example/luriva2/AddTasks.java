package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AddTasks extends AppCompatActivity {
    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

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

    public void oneTimeNav(View v){
        Intent intent = new Intent(this, OneTimeParameters.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing One-Time Task Manager", Toast.LENGTH_LONG);
        toast.show();
    }
    public void repeatedNav(View v){
        Intent intent = new Intent(this, RepeatedParameters.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Repeated Task Manager", Toast.LENGTH_LONG);
        toast.show();
    }
    public void projectNav(View v){
        Intent intent = new Intent(this, ProjectParameters.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Project Task Manager", Toast.LENGTH_LONG);
        toast.show();
    }
}