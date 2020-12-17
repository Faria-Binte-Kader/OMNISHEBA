package com.example.omnisheba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UpdateDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);
        getSupportActionBar().setTitle("0!");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}