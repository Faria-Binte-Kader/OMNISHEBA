package com.example.omnisheba;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HospitalMainActivity extends AppCompatActivity {

    public Button hospitalprofilebtn, hospitalupdatebtn, addDoctorbtn,hospitallogoutbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_main_activity);
        getSupportActionBar().setTitle("0!");

        

       /* hospitalprofilebtn = (Button) findViewById(R.id.hospitalprofilebutton);
        hospitalupdatebtn = (Button) findViewById(R.id.hospitalupdatebutton);
        addDoctorbtn = (Button) findViewById(R.id.addDoctorbutton);
        hospitallogoutbtn = (Button) findViewById(R.id.hospitalLogoutbutton);*/

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
