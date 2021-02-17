package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class to hold the main menu of the Test center  type user
 * @author Faria Binte Kader
 */
public class TestMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    /**
     * Method to attach the fxml layout and set the title manually when created
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.testprofile).setOnClickListener(this);
        findViewById(R.id.testupdate).setOnClickListener(this);
        findViewById(R.id.testlogout).setOnClickListener(this);
    }

    /**
     * Method to take the user to the log out confirmation activity
     */
    private void logout() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, LogoutTestCenterActivity.class);
        startActivity(intent);
    }

    /**
     * Method to take the user to update user information activity
     */
    private void update() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, UpdateTestCenterActivity.class);
        startActivity(intent);
    }

    /**
     * Method to take the user to show their profile(information)
     */
    private void profile() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, ProfileTestCenterActivity.class);
        startActivity(intent);
    }

    /**
     * Method to determine which activity will occur when one of the grids are clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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


}
