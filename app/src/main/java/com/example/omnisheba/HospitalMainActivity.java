package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class to hold the main menu of the Hospital type user
 * @author Tasmia Binte Sogir
 */
public class HospitalMainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TEXT2 = "com.example.application.example.EXTRA_TEXT2";

    /**
     * Method to attach the fxml layout and set the title manually when created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_main_activity);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.hospitalprofile).setOnClickListener(this);
        findViewById(R.id.hospitalupdate).setOnClickListener(this);
        findViewById(R.id.doctors).setOnClickListener(this);
        findViewById(R.id.hospitallogout).setOnClickListener(this);
    }

    /**
     * Method to take the user to the log out confirmation activity
     */
    private void logout() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, LogoutHospitalActivity.class);
        startActivity(intent);
    }

    /**
     * Method to take the user to the activity to see the doctors' list under the hospital and
     * to add new doctors
     */
    private void doctor() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, DoctorsHospitalActivity.class);
        FirebaseUser userHospital;
        FirebaseAuth fAuthHos;
        fAuthHos = FirebaseAuth.getInstance();
        userHospital = fAuthHos.getCurrentUser();
        String hospitalId = userHospital.getUid();
        intent.putExtra(EXTRA_TEXT2, hospitalId);
        startActivity(intent);
    }

    /**
     * Method to take the user to update user information activity
     */
    private void update() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, UpdateHospitalActivity.class);
        startActivity(intent);
    }

    /**
     * Method to take the user to show their profile(information)
     */
    private void profile() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, ProfileHospitalActivity.class);
        startActivity(intent);
    }

    /**
     * Method to determine which activity will occur when one of the grids are clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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


}
