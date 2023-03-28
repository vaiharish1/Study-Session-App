package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
    }

    public void oneTimeNav(View v){
        Intent intent = new Intent(this, OneTimeParameters.class );
        startActivity(intent);
    }
    public void repeatedNav(View v){
        Intent intent = new Intent(this, RepeatedParameters.class );
        startActivity(intent);
    }
    public void projectNav(View v){
        Intent intent = new Intent(this, ProjectParameters.class );
        startActivity(intent);
    }
}