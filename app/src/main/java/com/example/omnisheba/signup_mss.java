package com.example.omnisheba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class signup_mss extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button alreadyHaveAccount;
    private EditText inputName, inputEmail, inputPassword, confirmPassword, inputDescription, inputPhone, inputDob;
    Spinner genderType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mss);
        getSupportActionBar().setTitle("0!");
        genderType = findViewById(R.id.gender_type);
        genderType.setOnItemSelectedListener(this);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountMSS);
        inputName = findViewById(R.id.inputNameMSS);
        inputEmail = findViewById(R.id.inputEmailMSS);
        inputPassword = findViewById(R.id.inputPasswordMSS);
        confirmPassword = findViewById(R.id.confirmPasswordMSS);
        inputDescription = findViewById(R.id.inputDescriptionMSS);
        inputDob = findViewById(R.id.inputDobMSS);
        inputPhone = findViewById(R.id.inputPhoneMSS);
    }

    private void checkCredentials() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String conPassword = confirmPassword.getText().toString();

        if(name.isEmpty() || name.length() < 7)
            showError(inputName, "Your Name is not valid");
        else if(email.isEmpty() || !email.contains("@"))
            showError(inputEmail, "Email is not Valid");
        else if(password.isEmpty() || password.length()<7)
            showError(inputPassword, "Password must be at least 7 characters");
        else if(conPassword.isEmpty() || !conPassword.equals(password))
            showError(confirmPassword, "Password does not match");
        else
            Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    public void  onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {     ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void signupbutton(View view) {
        checkCredentials();
    }
    public void gotoLoginPage(View view) {
        Intent intent = new Intent(signup_mss.this,login.class);
        startActivity(intent);
    }
}