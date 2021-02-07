package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * For the main homepage of the Doctor
 * @author
 */
public class DoctorMainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * When created, make the views doctorprofile, doctorupdate, doctoranswer and doctorlogout clickable
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_main_activity);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.doctorprofile).setOnClickListener(this);
        findViewById(R.id.doctorupdate).setOnClickListener(this);
        findViewById(R.id.doctoranswer).setOnClickListener(this);
        findViewById(R.id.doctorlogout).setOnClickListener(this);
    }

    /**
     * Logout function for the doctor
     */
    private void logout() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, LogoutDoctorActivity.class);
        startActivity(intent);
    }

    /**
     * To go to the Discussion Forum
     */
    private void list() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, DoctorDiscussionForum.class);
        startActivity(intent);
    }

    /**
     * Update function for the doctor
     */
    private void update() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, UpdateDoctorActivity.class);
        startActivity(intent);
    }

    /**
     * For viewing their own profile
     */
    private void profile() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, ProfileDoctorActivity.class);
        startActivity(intent);
    }

    /**
     * What functions are called when different views are clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doctorprofile:
                profile();
                break;
            case R.id.doctorupdate:
                update();
                break;
            case R.id.doctoranswer:
                list();
                break;
            case R.id.doctorlogout:
                logout();
                break;
        }
    }
}
