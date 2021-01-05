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

public class LogoutHospitalActivity extends AppCompatActivity  {
   private Button yes,no;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_hospital);
        getSupportActionBar().setTitle("0!");

        yes= (Button)findViewById(R.id.yes_btnhospital);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(LogoutHospitalActivity.this, "Logged out Successfully!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LogoutHospitalActivity.this, login.class));
               // finish();
                Intent intent = new Intent(LogoutHospitalActivity.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    /*private void logout()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void main()
    {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,DoctorMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }*/

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_btn:

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, login.class));
                //finish();


                break;
            case R.id.no_btn:
                startActivity(new Intent(this, HospitalMainActivity.class));
                break;
        }
    }*/
}