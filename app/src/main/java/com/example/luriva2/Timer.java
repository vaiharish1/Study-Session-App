package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Timer extends AppCompatActivity {
    TextView status;
    TextView timer;
    TextView motivationalQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        status = findViewById(R.id.timerStatus);
        timer = findViewById(R.id.timer);
        motivationalQuote = findViewById(R.id.motivationalQuote);
        int min = 2;
        CountDownTimer countdown = new CountDownTimer(min*60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(msToMin(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                timer.setText("Done!");
            }

        };

         countdown.start();

    }

    private String msToMin(long ms) {
        int sec = (int)(ms/1000)%60;
        int min = (int)(ms/1000)/60;
        String time = String.format("%02d",min) + ":" + String.format("%02d",sec);
        return time;
    }


}