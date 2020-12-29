package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import static android.R.layout.simple_spinner_item;

public class login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText loginEmail, loginPassword;
    Button loginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String type = "";
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("0!");

        Spinner userType = findViewById(R.id.usertype);
        userType.setOnItemSelectedListener(this);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        loginBtn = (Button) findViewById(R.id.loginbutton);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();


                if (email.isEmpty() || !email.contains("@")) {
                    showError(loginEmail, "Email is not Valid");
                    return;
                }
                if (password.isEmpty() || password.length() < 7) {
                    showError(loginPassword, "Password must be at least 7 characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("Usertype").document(userId);
                            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    type = type + (value.getString("Type"));
                                    if (type.equals("Doctor")) {
                                        Toast.makeText(login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), DoctorMainActivity.class));
                                    } else if (type.equals("MSS")) {
                                        Toast.makeText(login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else if (type.equals("Hospital")) {
                                        Toast.makeText(login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HospitalMainActivity.class));
                                    } else if (type.equals("TC")) {
                                        Toast.makeText(login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), TestMainActivity.class));
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void gotosignupbutton(View view) {
        SignUpBasedOnUser();
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }


    public void SignUpBasedOnUser() {
        Spinner userType = findViewById(R.id.usertype);
        String type = userType.getSelectedItem().toString();
        if (type.equals("Doctor")) {
            Intent intent = new Intent(login.this, signup_doctor.class);
            startActivity(intent);
        } else if (type.equals("Medical Service Seeker")) {
            Intent intent = new Intent(login.this, signup_mss.class);
            startActivity(intent);
        } else if (type.equals("Hospital")) {
            Intent intent = new Intent(login.this, signup_hospital.class);
            startActivity(intent);
        } else if (type.equals("Test Center")) {
            Intent intent = new Intent(login.this, signup_testcenter.class);
            startActivity(intent);
        }
    }


}
