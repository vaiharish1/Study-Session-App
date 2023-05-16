package com.example.luriva2;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.luriva2.dataModelClasses.Date;
import com.example.luriva2.dataModelClasses.Session;
import com.example.luriva2.dataModelClasses.Timeblock;
import com.example.luriva2.recyclerViewClasses.Session_RecyclerViewAdapter;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.Toast;

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

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String dateDisplay = extras.getString("date");

            String[] components = dateDisplay.split("/");
            thisDay = new Date(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2]));

            TextView daySessionsHeader = findViewById(R.id.todaysSessionHeader);
            daySessionsHeader.setText("Sessions For: " + thisDay.toString());
        }

//        Log.v("THE DATE", thisDay.toString());

        loadData(thisDay);

        recyclerView = findViewById(R.id.recyclerView);

        createAdapter();

        makeSaveButtonDisabled();
    }

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

    public void addTasksNav(View v){
        Intent intent = new Intent(this, AddTasks.class );
        startActivity(intent);
        showToast("Viewing Task Manager...");
    }

    public void onClickSaveData(View v) {
        saveData(thisDay);
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