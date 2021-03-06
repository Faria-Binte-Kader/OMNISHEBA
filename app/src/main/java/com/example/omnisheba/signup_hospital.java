package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to show the signup activity of the Hospital type user
 * @author Faria Binte Kader
 */
public class signup_hospital extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG";

    private EditText inputName, inputEmail, inputPassword, confirmPassword, inputDescription, inputHotline, inputFoundationYear;
    Spinner hospital_type, locationHos_type;

    Button alreadyHaveAccount, deptunitBtn, signUpBtnHospital;
    String userId;

    FirebaseAuth fAuthHospital;
    FirebaseFirestore fstoreHospital;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    ArrayList<String> deptunit = new ArrayList<String>();

    /**
     * Initialize every object after created
     * Take all the user inputs and show errors for the respective input constraints
     * Create a Hospital type user through firebase and save all his information in the respective collections
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_hospital);
        getSupportActionBar().setTitle("0!");
        hospital_type = findViewById(R.id.hospital_types);
        hospital_type.setOnItemSelectedListener(this);
        locationHos_type = findViewById(R.id.location_type);
        locationHos_type.setOnItemSelectedListener(this);

        fAuthHospital = FirebaseAuth.getInstance();
        fstoreHospital = FirebaseFirestore.getInstance();

        deptunitBtn = findViewById(R.id.btnDeptunit);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.deptunit_list);
        checkedItems = new boolean[listItems.length];

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountHospital);
        signUpBtnHospital = findViewById(R.id.signup_button);

        inputName = findViewById(R.id.inputNameHospital);
        inputEmail = findViewById(R.id.inputEmailHospital);
        inputPassword = findViewById(R.id.inputPasswordHospital);
        confirmPassword = findViewById(R.id.confirmPasswordHospital);
        inputDescription = findViewById(R.id.inputDescriptionHospital);
        inputHotline = findViewById(R.id.inputHotlineHospital);
        inputFoundationYear = findViewById(R.id.inputFoundationYearHospital);

        signUpBtnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString().toUpperCase();
                final String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String conPassword = confirmPassword.getText().toString();
                final String descript = inputDescription.getText().toString();
                final String line = inputHotline.getText().toString();
                final String found = inputFoundationYear.getText().toString();
                final String type = hospital_type.getSelectedItem().toString();
                final String location = locationHos_type.getSelectedItem().toString();


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

                fAuthHospital.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signup_hospital.this, "User Created", Toast.LENGTH_SHORT).show();
                            userId = fAuthHospital.getCurrentUser().getUid();

                            DocumentReference documentReference = fstoreHospital.collection("Hospital").document(userId);
                            Map<String, Object> hospital = new HashMap<>();
                            hospital.put("Name", name);
                            hospital.put("Email", email);
                            hospital.put("Description", descript);
                            hospital.put("Hotline", line);
                            hospital.put("Foundationyear", found);
                            hospital.put("Hospitaltype", type);
                            hospital.put("Hospitallocation", location);
                            hospital.put("Departmentunit", deptunit);
                            hospital.put("Type", "Hospital");


                            DocumentReference documentReference3 = fstoreHospital.collection("Usertype").document(userId);
                            Map<String, Object> type = new HashMap<>();
                            type.put("Type", "Hospital");


                            documentReference3.set(type).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user type is created");
                                }

                            });


                            documentReference.set(hospital).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created");
                                }

                            });


                            startActivity(new Intent(getApplicationContext(), HospitalMainActivity.class));
                        } else {
                            Toast.makeText(signup_hospital.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        deptunitBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * After clicking the specialty button, all the item chosen in the dialogue box, previously added
             * in the arraylist, will be shown in the respective Textview
             * @param view
             */
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_hospital.this);
                mBuilder.setTitle(R.string.dialog_title4);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            deptunit.add(listItems[mUserItems.get(i)]);
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
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
        Intent intent = new Intent(signup_hospital.this, login.class);
        startActivity(intent);
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
}
