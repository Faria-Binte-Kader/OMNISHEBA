package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HospitalMainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_main_activity);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.hospitalprofile).setOnClickListener(this);
        findViewById(R.id.hospitalupdate).setOnClickListener(this);
        findViewById(R.id.doctors).setOnClickListener(this);
        findViewById(R.id.hospitallogout).setOnClickListener(this);
    }

    private void logout()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,LogoutHospitalActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void doctor()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,DoctorsHospitalActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void update()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,UpdateHospitalActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void profile()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,ProfileHospitalActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.hospitalprofile:
                profile();
                break;
            case R.id.hospitalupdate:
                update();
                break;
            case R.id.doctors:
                doctor();
                break;
            case R.id.hospitallogout:
                logout();
                break;
        }
    }

   /* public void hospitalprofilebutton(View view) {
        //Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
        //startActivity(intent);
   }

    public void hospitalupdatebutton(View view) {
        //Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
        //startActivity(intent);
    }
    public void addDoctorbutton(View view) {
        //Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
        //startActivity(intent);
    }
    public void hospitalLogoutbutton(View view) {
        //Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
        //startActivity(intent);
    }*/

}
