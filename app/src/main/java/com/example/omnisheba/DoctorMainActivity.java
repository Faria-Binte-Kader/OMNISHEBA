package com.example.omnisheba;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class DoctorMainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_main_activity);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.doctorprofile).setOnClickListener(this);
        findViewById(R.id.doctorupdate).setOnClickListener(this);
        findViewById(R.id.doctorlist).setOnClickListener(this);
        findViewById(R.id.doctorlogout).setOnClickListener(this);
    }

    private void logout()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,LogoutDoctorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void list()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,ListDoctorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void update()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,UpdateDoctorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void profile()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,ProfileDoctorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.doctorprofile:
                profile();
                break;
            case R.id.doctorupdate:
                update();
                break;
            case R.id.doctorlist:
                list();
                break;
            case R.id.doctorlogout:
                logout();
                break;
        }
    }




   /*public void doctorprofilebutton(View view) {
      // Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
      // startActivity(intent);
   }
    public void doctorupdatebutton(View view) {
        //Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
       // startActivity(intent);
    }
    public void doctorappointmentbutton(View view) {
        //Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
        //startActivity(intent);
    }
    public void doctorlogoutbutton(View view) {
        //Intent intent = new Intent(DoctorMainActivity.this,MainActivity.class);
        //startActivity(intent);
    }*/

}
