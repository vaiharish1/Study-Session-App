package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProjectParameters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_parameters);
    }

    public void getTaskName(View v){
        EditText taskName = findViewById(R.id.editTextTaskName_project);
    }

    public void collectTaskName(View v){
        Toast toast = Toast.makeText(getApplicationContext(), "Viewing One-Time Task Manager", Toast.LENGTH_LONG);
        toast.show();
    }
}