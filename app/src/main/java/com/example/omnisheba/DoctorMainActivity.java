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

import androidx.appcompat.app.AppCompatActivity;

public class DoctorMainActivity extends AppCompatActivity {

    public Button doctorprofilebtn, doctorupdatebtn, doctorpatientbtn,doctorlogoutbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_main_activity);
        getSupportActionBar().setTitle("0!");

       /*doctorprofilebtn = (Button) findViewById(R.id.doctorprofilebutton);
        doctorupdatebtn = (Button) findViewById(R.id.doctorupdatebutton);
        doctorpatientbtn = (Button) findViewById(R.id.doctorappointmentbutton);
        doctorlogoutbtn = (Button) findViewById(R.id.doctorlogoutbutton);*/

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
