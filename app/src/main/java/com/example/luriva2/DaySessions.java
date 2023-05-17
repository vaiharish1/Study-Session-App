package com.example.luriva2;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.SessionRecyclerViewInterface;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class DaySessions extends DisplaySessions implements SessionRecyclerViewInterface {
    NavigationBarView navigationBarView; // getting the navigation bar
    private RecyclerView recyclerView; // the recycler view
    private Date thisDay; // the day for the sessions

    Session_RecyclerViewAdapter adapter; // the adapter for the recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

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

        // get all the attributes from the intent
        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            // get the date and format it correctly
            String dateDisplay = extras.getString("date");

            String[] components = dateDisplay.split("/");
            thisDay = new Date(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2]));

            TextView daySessionsHeader = findViewById(R.id.todaysSessionHeader);
            daySessionsHeader.setText("Sessions For: " + thisDay.toString());
        }

        loadData(thisDay); // load the session for this day

        // creating the recycler view
        recyclerView = findViewById(R.id.recyclerView);
        createAdapter();

        // disable save button
        makeSaveButtonDisabled();
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

    // going to add task page
    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
        showToast("Viewing Task Manager...");
    }

    // save data when clicking the button
    public void onClickSaveData(View v) {
        saveData(thisDay);
    }

    // creating the adapter for the recycler view
    public void createAdapter() {
        ArrayList<Session> sessionModels = getSessionModels();
        adapter = new Session_RecyclerViewAdapter(this, sessionModels, this);

        recyclerView.setAdapter(adapter);

        // set item click listeners
        adapter.setOnItemClickListener((v, position) -> {
            // for the down button, move session down
            if (v.getId() == R.id.downButton) {
                Session session = sessionModels.remove(position);

                Session curPosition = sessionModels.get(position);
                Timeblock temp = curPosition.getTimeblock();
                curPosition.setTimeblock(session.getTimeblock());
                session.setTimeblock(temp);

                sessionModels.add(position + 1, session);
            }
            // for up button, move session up
            else if (v.getId() == R.id.upButton) {
                Session session = sessionModels.remove(position);

                Session curPosition = sessionModels.get(position-1);
                Timeblock temp = curPosition.getTimeblock();
                curPosition.setTimeblock(session.getTimeblock());
                session.setTimeblock(temp);

                sessionModels.add(position - 1, session);
            }

            adapter.notifyDataSetChanged();
            makeSaveButtonEnabled();
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // on item long click method
    @Override
    public void onItemLongClick(int position) {
        // simply delete the session
        ArrayList<Session> sessionModels = getSessionModels();
        sessionModels.remove(position);
        adapter.notifyItemRemoved(position);
        makeSaveButtonEnabled();
    }
}