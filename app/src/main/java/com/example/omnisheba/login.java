package com.example.omnisheba;

import androidx.appcompat.app.AppCompatActivity ;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.layout.simple_spinner_item;

public class login extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button gotosignupbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("0!");
        Spinner usertype = findViewById(R.id.usertype);
        usertype.setOnItemSelectedListener( this);
        gotosignupbtn = (Button) findViewById(R.id.gotosignup);
    }

    @Override
    public void  onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {     ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void loginbutton(View view) {
        Spinner usertype = findViewById(R.id.usertype);
        String type = usertype.getSelectedItem().toString();
        if(type.equals("Doctor"))
        {
            Intent intent = new Intent(login.this,DoctorMainActivity.class);
            startActivity(intent);
        }
        else if(type.equals("Medical Service Seeker"))
        {
            Intent intent = new Intent(login.this,MainActivity.class);
            startActivity(intent);
        }
        else if(type.equals("Hospital"))
        {
            Intent intent = new Intent(login.this,HospitalMainActivity.class);
            startActivity(intent);
        }
        else if(type.equals("Test Center"))
        {
            Intent intent = new Intent(login.this,HospitalMainActivity.class);
            startActivity(intent);
        }
    }

    public void gotosignupbutton(View view) {
        Spinner usertype = findViewById(R.id.usertype);
        String type = usertype.getSelectedItem().toString();
        if(type.equals("Doctor"))
        {
            Intent intent = new Intent(login.this,signup_doctor.class);
            startActivity(intent);
        }
        else if(type.equals("Medical Service Seeker"))
        {
            Intent intent = new Intent(login.this,signup_mss.class);
            startActivity(intent);
        }
        else if(type.equals("Hospital"))
        {
            Intent intent = new Intent(login.this,signup_hospital.class);
            startActivity(intent);
        }
        else if(type.equals("Test Center"))
        {
            Intent intent = new Intent(login.this,signup_testcenter.class);
            startActivity(intent);
        }
    }
}
