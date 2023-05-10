package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Random;

public class Timer extends AppCompatActivity {
    TextView status;
    TextView timer;
    TextView motivationalQuote;
    CountDownTimer countdown;
    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setSelectedItemId(R.id.timerNavigation);
        navigationBarView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.timerNavigation:
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

        status = findViewById(R.id.timerStatus);
        timer = findViewById(R.id.timer);
        motivationalQuote = findViewById(R.id.motivationalQuote);

        int min = 1;
       startTimer(min);
    }

    public void startTimer(int min){
        if(min == 45)
            status.setText(R.string.statusWorkingString);
        else if(min == 15)
            status.setText(R.string.statusBreakTimeString);
        else
            status.setText(R.string.statusNotWorkingString);
        countdown = new CountDownTimer((long) min *60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(msToMin(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                String[] motivation = getResources().getStringArray(R.array.motivationQuotes);
                int motivationLen = motivation.length;
                Random randy = new Random();
                motivationalQuote.setText(motivation[randy.nextInt(motivationLen)]);
                startTimer(min);
            }

        };
        countdown.start();
    }

    private String msToMin(long ms) {
        int sec = (int)(ms/1000)%60;
        int min = (int)(ms/1000)/60;
        return String.format("%02d",min) + ":" + String.format("%02d",sec);
    }

    public void cancel(View v){
        status.setText(R.string.cancelledStr);
        countdown.cancel();
    }


}