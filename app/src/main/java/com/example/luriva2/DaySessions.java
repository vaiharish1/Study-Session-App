package com.example.luriva2;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import java.util.ArrayList;
import android.content.Intent;

import com.google.android.material.navigation.NavigationBarView;

public class DaySessions extends DisplaySessions {
    NavigationBarView navigationBarView;
    private RecyclerView recyclerView;
    private Date thisDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

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

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String dateDisplay = extras.getString("date");

            String[] components = dateDisplay.split("/");
            thisDay = new Date(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2]));

            TextView daySessionsHeader = findViewById(R.id.todaysSessionHeader);
            daySessionsHeader.setText("Sessions For: " + thisDay.toString());
        }

        loadData(thisDay);

        recyclerView = findViewById(R.id.recyclerView);

        createAdapter();

        makeSaveButtonDisabled();
    }

    public void onClickSaveData(View v) {
        saveData(thisDay);
    }

    public void createAdapter() {
        ArrayList<Session> sessionModels = getSessionModels();
        Session_RecyclerViewAdapter adapter = new Session_RecyclerViewAdapter(this, sessionModels);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new Session_RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (v.getId() == R.id.downButton) {
                    Session session = sessionModels.remove(position);

                    Session curPosition = sessionModels.get(position);
                    Timeblock temp = curPosition.getTimeblock();
                    curPosition.setTimeblock(session.getTimeblock());
                    session.setTimeblock(temp);

                    sessionModels.add(position + 1, session);
                } else if (v.getId() == R.id.upButton) {
                    Session session = sessionModels.remove(position);

                    Session curPosition = sessionModels.get(position-1);
                    Timeblock temp = curPosition.getTimeblock();
                    curPosition.setTimeblock(session.getTimeblock());
                    session.setTimeblock(temp);

                    sessionModels.add(position - 1, session);
                }

                adapter.notifyDataSetChanged();
                makeSaveButtonEnabled();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}