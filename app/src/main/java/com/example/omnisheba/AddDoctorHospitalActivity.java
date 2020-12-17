package com.example.omnisheba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddDoctorHospitalActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor_hospital);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.addbtn).setOnClickListener(this);
    }

    private void add()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,signup_doctor.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addbtn:
                add();
                break;
        }
    }
}