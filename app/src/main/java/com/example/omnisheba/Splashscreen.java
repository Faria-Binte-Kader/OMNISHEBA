package com.example.omnisheba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Class to show the screen with user logo for 3 seconds when the application is opened
 */
public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent screen = new Intent(Splashscreen.this, login.class);
                startActivity(screen);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
