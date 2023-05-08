package com.example.luriva2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.homeNavigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.timerNavigation:
                        startActivity(new Intent(getApplicationContext(),Timer.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.homeNavigation:
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigationLayout,fragment);
        fragmentTransaction.commit();
    }

    public void todaysSessionsNav(View v){
        Intent intent = new Intent(this, TodaysSessions.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Today's Sessions", Toast.LENGTH_LONG);
        toast.show();
    }

    public void viewCalNav(View v){
        // Log.d("vai","in viewCalNav method");
        Intent intent = new Intent(this, ViewCalendar.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Calendar", Toast.LENGTH_LONG);
        toast.show();
    }

    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Task Manager", Toast.LENGTH_LONG);
        toast.show();
    }

    public void settingsNav(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Settings", Toast.LENGTH_LONG);
        toast.show();
    }

    public void timerNav(View v){
        Intent intent = new Intent(this,Timer.class);
        startActivity(intent);
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing Timer", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onTitleClick(){
        Toast toast = Toast.makeText(getApplicationContext(), ":P", Toast.LENGTH_LONG);
        toast.show();
    }
}
