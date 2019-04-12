package com.example.nimbusbrowser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rapidbrowser.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Thread th = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                finally
                {
                    Intent home = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(home);
                }
            }
        };
        th.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}

