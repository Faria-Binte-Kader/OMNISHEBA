package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to show the signup activity of the Medical Service Seeker type user
 */
public class signup_mss extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG";
    Button alreadyHaveAccount, signUpBtnMSS;
    private EditText inputName, inputEmail, inputPassword, confirmPassword, inputDescription, inputPhone, inputDob;
    Spinner genderType;
    String userId;
    FirebaseAuth fAuthMSS;
    FirebaseFirestore fstoreMSS;

    /**
     * Initialize every object after created
     * Take all the user inputs and show errors for the respective input constraints
     * Create a Medical Service Seeker type user through firebase and save all his information in the respective collections
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
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
        fstoreMSS = FirebaseFirestore.getInstance();

        signUpBtnMSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString().toUpperCase();
                final String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String conPassword = confirmPassword.getText().toString();
                final String description = inputDescription.getText().toString();
                final String dateofbirth = inputDob.getText().toString();
                final String phone = inputPhone.getText().toString();
                final String type = genderType.getSelectedItem().toString();

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

                fAuthMSS.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signup_mss.this, "User Created", Toast.LENGTH_SHORT).show();
                            userId = fAuthMSS.getCurrentUser().getUid();
                            DocumentReference documentReference = fstoreMSS.collection("MSS").document(userId);
                            Map<String, Object> mss = new HashMap<>();
                            mss.put("Name", name);
                            mss.put("Email", email);
                            mss.put("Gender", type);
                            mss.put("Description", description);
                            mss.put("DOB", dateofbirth);
                            mss.put("Phone", phone);
                            mss.put("Type", "MSS");
                            documentReference.set(mss).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created");
                                }
                            });
                            DocumentReference documentReference3 = fstoreMSS.collection("Usertype").document(userId);
                            Map<String, Object> type = new HashMap<>();
                            type.put("Type", "MSS");

                            documentReference3.set(type).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user type is created");
                                }

                            });


                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(signup_mss.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * to show the popup error
     * @param input
     * @param s
     */
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    /**
     * To make the selected word white colored in the spinner and
     * to make the word pop up when selected using toast
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Method to go to the login activity when button clicked
     * @param view
     */
    public void gotoLoginPage(View view) {
        Intent intent = new Intent(signup_mss.this, login.class);
        startActivity(intent);
    }
}