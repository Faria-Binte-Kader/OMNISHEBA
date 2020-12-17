package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TestMainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.testprofile).setOnClickListener(this);
        findViewById(R.id.testupdate).setOnClickListener(this);
        findViewById(R.id.testlogout).setOnClickListener(this);
    }

    private void logout()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,LogoutTestCenterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void update()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,UpdateTestCenterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void profile()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,ProfileTestCenterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.testprofile:
                profile();
                break;
            case R.id.testupdate:
                update();
                break;
            case R.id.testlogout:
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
