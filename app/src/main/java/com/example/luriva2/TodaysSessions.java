package com.example.luriva2;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Task;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.SessionRecyclerViewInterface;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;

public class TodaysSessions extends DisplaySessions implements SessionRecyclerViewInterface {

    private RecyclerView recyclerView; // the recycler view

    Session_RecyclerViewAdapter adapter; // the adapter for the recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

        // setting up the navigation bar
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

        // get today's date
        Date today = getToday();

        // load sessions for today
        loadData(today);

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
        saveData(getToday());
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
