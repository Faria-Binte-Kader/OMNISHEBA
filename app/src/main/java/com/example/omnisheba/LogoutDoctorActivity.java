package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Class to log out doctor type users from the system
 */
public class LogoutDoctorActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Method to attach the fxml layout and set the title manually when created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_doctor);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.yes_btndoctor).setOnClickListener(this);
        findViewById(R.id.no_btndoctor).setOnClickListener(this);
    }

    /**
     * Log out and take the user to the log in activity when yes button is clicked
     * and clear tasks so the users cannot go back to their profile when back button pressed
     * Take the user to the doctor main menu when no button is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_btndoctor:
                if( FirebaseAuth.getInstance()!=null)
                {FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged out Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                }

                break;
            case R.id.no_btndoctor:
                startActivity(new Intent(this, DoctorMainActivity.class));
                break;
        }
    }
}