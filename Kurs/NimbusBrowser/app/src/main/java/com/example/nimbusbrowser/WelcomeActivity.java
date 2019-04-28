package com.example.nimbusbrowser;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.rapidbrowser.R;

public class WelcomeActivity extends AppCompatActivity {

    private ProgressBar progressBar_bar;
    private int inProgress = 0;

    private Handler nHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressBar_bar = (ProgressBar) findViewById(R.id.progressBar);
        StartProgress();


        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent home = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(home);
                    overridePendingTransition(R.anim.slidein, R.anim.slideout);
                }
            }
        };
        th.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    private void StartProgress() {

        progressBar_bar = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (inProgress < 100) {
                    inProgress++;
                    android.os.SystemClock.sleep(30);
                    nHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar_bar.setProgress(inProgress);

                        }
                    });
                }
            }
        }).start();

    }

}
