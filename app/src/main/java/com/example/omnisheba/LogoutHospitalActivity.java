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
import com.google.firebase.auth.FirebaseUser;

/**
 * Class to log out doctor type users from the system
 */
public class LogoutHospitalActivity extends AppCompatActivity  {
   private Button yes,no;

    /**
     * Method to attach layout, set title manually,
     * Log out and take the user to the log in activity when yes button is clicked
     * and clear tasks so the users cannot go back to their profile when back button pressed
     * Take the user to the hospital main menu when no button is clicked
     * @param savedInstanceState
     */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_hospital);
        getSupportActionBar().setTitle("0!");

        yes= (Button)findViewById(R.id.yes_btnhospital);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( FirebaseAuth.getInstance()!=null)
                {FirebaseAuth.getInstance().signOut();
                Toast.makeText(LogoutHospitalActivity.this, "Logged out Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogoutHospitalActivity.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();}
            }
        });
        no= (Button)findViewById(R.id.no_btnhospital);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogoutHospitalActivity.this, HospitalMainActivity.class));
            }
        });
    }

}