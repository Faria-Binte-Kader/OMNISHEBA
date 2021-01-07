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

public class LogoutTestCenterActivity extends AppCompatActivity implements View.OnClickListener {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_test_center);
        getSupportActionBar().setTitle("0!");

        findViewById(R.id.yes_btntc).setOnClickListener(this);
        findViewById(R.id.no_btntc).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_btntc:
                if( FirebaseAuth.getInstance()!=null)
                {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged out Successfully!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(this, login.class));
                Intent intent = new Intent(this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();}
                break;
            case R.id.no_btntc:
                startActivity(new Intent(this, TestMainActivity.class));
                break;
        }
    }
}