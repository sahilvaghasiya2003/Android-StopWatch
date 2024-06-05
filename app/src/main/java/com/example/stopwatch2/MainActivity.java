package com.example.stopwatch2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private TextView timerText;
    private Button startButton, pauseButton, resetButton;

    private Handler handler = new Handler();
    private long startTime, timeInMilliseconds = 0L;
    private boolean isRunning = false;

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if (isRunning) {
                timeInMilliseconds = System.currentTimeMillis() - startTime;
                int secs = (int) (timeInMilliseconds / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (timeInMilliseconds % 1000);
                timerText.setText("" + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));
                handler.postDelayed(this, 0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - timeInMilliseconds;
                    handler.postDelayed(updateTimerThread, 0);
                    isRunning = true;
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    handler.removeCallbacks(updateTimerThread);
                    isRunning = false;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimerThread);
                isRunning = false;
                timeInMilliseconds = 0L;
                timerText.setText("00:00:000");
            }
        });
    }
}