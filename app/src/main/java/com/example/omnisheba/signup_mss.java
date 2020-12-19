package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class signup_mss extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button alreadyHaveAccount, signUpBtnMSS;
    private EditText inputName, inputEmail, inputPassword, confirmPassword, inputDescription, inputPhone, inputDob;
    Spinner genderType;
    FirebaseAuth fAuthMSS;
    ProgressBar progBarMSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mss);
        getSupportActionBar().setTitle("0!");
        genderType = findViewById(R.id.gender_type);
        genderType.setOnItemSelectedListener(this);

        signUpBtnMSS = findViewById(R.id.signup_button);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountMSS);
        inputName = findViewById(R.id.inputNameMSS);
        inputEmail = findViewById(R.id.inputEmailMSS);
        inputPassword = findViewById(R.id.inputPasswordMSS);
        confirmPassword = findViewById(R.id.confirmPasswordMSS);
        inputDescription = findViewById(R.id.inputDescriptionMSS);
        inputDob = findViewById(R.id.inputDobMSS);
        inputPhone = findViewById(R.id.inputPhoneMSS);

        fAuthMSS = FirebaseAuth.getInstance();
        //progBarMSS = findViewById(R.id.progressBarMSS);

        /*if (fAuthMSS.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/

        signUpBtnMSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String conPassword = confirmPassword.getText().toString();

                if (name.isEmpty() || name.length() < 7) {
                    showError(inputName, "Your Name is not valid");
                    return;
                }
                if (email.isEmpty() || !email.contains("@")) {
                    showError(inputEmail, "Email is not Valid");
                    return;
                }
                if (password.isEmpty() || password.length() < 7) {
                    showError(inputPassword, "Password must be at least 7 characters");
                    return;
                }
                if (conPassword.isEmpty() || !conPassword.equals(password)) {
                    showError(confirmPassword, "Password does not match");
                    return;
                }

                //progBarMSS.setVisibility(View.VISIBLE);

                fAuthMSS.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signup_mss.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(signup_mss.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /*private void checkCredentials() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String conPassword = confirmPassword.getText().toString();

        if (name.isEmpty() || name.length() < 7)
            showError(inputName, "Your Name is not valid");
        else if (email.isEmpty() || !email.contains("@"))
            showError(inputEmail, "Email is not Valid");
        else if (password.isEmpty() || password.length() < 7)
            showError(inputPassword, "Password must be at least 7 characters");
        else if (conPassword.isEmpty() || !conPassword.equals(password))
            showError(confirmPassword, "Password does not match");
        else
            Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
    }*/

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*public void signupbutton(View view) {
        checkCredentials();
        progBarMSS.setVisibility(View.VISIBLE);

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        fAuthMSS.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(signup_mss.this, "User Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(signup_mss.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    public void gotoLoginPage(View view) {
        Intent intent = new Intent(signup_mss.this, login.class);
        startActivity(intent);
    }
}