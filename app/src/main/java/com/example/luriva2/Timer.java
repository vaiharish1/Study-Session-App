package com.example.luriva2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Timer extends AppCompatActivity {
    TextView status;
    TextView timer;
    TextView motivationalQuote;
    int timeInt;
    String timerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        TextView status = findViewById(R.id.timerStatus);
        TextView timer = findViewById(R.id.timer);
        TextView motivationalQuote = findViewById(R.id.motivationalQuote);
        timeInt = 1;
        timerText = "45:00";
    }

    public void countdown(){
        while(timeInt!=0){
            timeToInt();
            timeInt--;
            delay(1);
            intToTime();
            timer.setText(timerText);
        }
    }

    private void timeToInt(){
        int min = Integer.parseInt(timerText.substring(0,2))*60;
        int sec = Integer.parseInt(timerText.substring(3,5));
        timeInt = min + sec;
    }
    private void intToTime() {
        int min = timeInt/60;
        int sec = timeInt%60;
        timerText = (String.format("%02d",min) + ":" + String.format("%02d",sec));
    }

    public void delay(long n)
    {
        n *= 1000000000;
        long startDelay = System.nanoTime();
        long endDelay = 0;
        while (endDelay - startDelay < n)
            endDelay = System.nanoTime();
    }

}