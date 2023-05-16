package com.example.luriva2;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;

public class TodaysSessions extends DisplaySessions {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_sessions);

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

        Date today = getToday();

        loadData(today);

        recyclerView = findViewById(R.id.recyclerView);

        createAdapter();

        makeSaveButtonDisabled();

        // TODO: make it so that if the user is in a session it automatically switches to the timer
    }

    public void onClickSaveData(View v) {
        saveData(getToday());
    }

    public void createAdapter() {
        ArrayList<Session> sessionModels = getSessionModels();
        Session_RecyclerViewAdapter adapter = new Session_RecyclerViewAdapter(this, sessionModels);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((v, position) -> {
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
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
