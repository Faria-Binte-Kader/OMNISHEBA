package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class UpdateHospitalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name, email, oldpass, newpass, conpass, description, hotline, year;
    Spinner hospital_type, locationhos_type;
    public static final String TAG = "TAG";
    FirebaseAuth fAuthhos;
    private FirebaseUser user;
    FirebaseFirestore fStore;
    String userID;
    String loc;


    ArrayList<String> test = new ArrayList<String>();
    Button deptunitBtn, updatebtn;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hospital);
        getSupportActionBar().setTitle("0!");

        hospital_type = findViewById(R.id.hospital_types);
        hospital_type.setOnItemSelectedListener(this);
        locationhos_type = findViewById(R.id.location_type);
        locationhos_type.setOnItemSelectedListener(this);
        name = findViewById(R.id.updatehosname);
        email = findViewById(R.id.hosmail);
        oldpass = findViewById(R.id.hosoldpass);
        year = findViewById(R.id.updatehosyear);
        newpass = findViewById(R.id.hosnewpass);
        conpass = findViewById(R.id.hosconpass);
        description = findViewById(R.id.updatehosdesc);
        hotline = findViewById(R.id.updatehoshotline);
        fAuthhos = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthhos.getCurrentUser().getUid();
        updatebtn = findViewById(R.id.hospitalUpdate_button);

        deptunitBtn = findViewById(R.id.btnUpdateDeptunit);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.deptunit_list);
        checkedItems = new boolean[listItems.length];

        DocumentReference documentReference = fStore.collection("Hospital").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                loc = value.getString("Hospitallocation");
            }
        });


        deptunitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateHospitalActivity.this);
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
                            test.add(listItems[mUserItems.get(i)]);
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
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updatehospital();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void Updatehospital() {
        final String nam = name.getText().toString();
        final String mail = email.getText().toString();
        final String oldpassword = oldpass.getText().toString();
        final String Password = newpass.getText().toString();
        String conPassword = conpass.getText().toString();
        final String desc = description.getText().toString();
        final String phon = hotline.getText().toString();
        final String yr = year.getText().toString();
        final String type = hospital_type.getSelectedItem().toString();
        final String location = locationhos_type.getSelectedItem().toString();


        if (!nam.isEmpty() && nam.length() < 7) {
            showError(name, "Your Name is not valid");
            return;
        }
        if ((mail.isEmpty() || oldpassword.isEmpty()) && !Password.isEmpty()) {
            showError(email, "Please input mail and old password");
            return;
        }
        if (!Password.isEmpty() && Password.length() < 7) {
            showError(newpass, "Password must be at least 7 characters");
            return;
        }
        if ((conPassword.isEmpty() || !conPassword.equals(Password)) && !Password.isEmpty()) {
            showError(conpass, "Password does not match");
            return;
        }

        if (!mail.isEmpty() && !oldpassword.isEmpty() && !conPassword.isEmpty()) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(mail, oldpassword);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        user.updatePassword(Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                View coordinatorLayout;
                                if (!task.isSuccessful()) {
                                    Log.d(TAG, "Error");
                                } else {
                                    Toast.makeText(UpdateHospitalActivity.this, "Password changed ", Toast.LENGTH_SHORT).show();
                                    oldpass.setText("");
                                    newpass.setText("");
                                    conpass.setText("");
                                    email.setText("");
                                }
                            }
                        });
                    } else {
                        Toast.makeText(UpdateHospitalActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!nam.isEmpty()) {
            fStore.collection("Hospital").document(userID)
                    .update("Name", nam.toUpperCase())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateHospitalActivity.this, "Updated Name", Toast.LENGTH_SHORT).show();
                            name.setText("");
                        }
                    });
        }

        if (!desc.isEmpty()) {
            fStore.collection("Hospital").document(userID)
                    .update("Description", desc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateHospitalActivity.this, "Updated Description", Toast.LENGTH_SHORT).show();
                            description.setText("");
                        }
                    });
        }

        if (!phon.isEmpty()) {
            fStore.collection("Hospital").document(userID)
                    .update("Hotline", phon)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateHospitalActivity.this, "Updated Hotline", Toast.LENGTH_SHORT).show();
                            hotline.setText("");
                        }
                    });
        }

        if (test.size() > 0) {
            fStore.collection("Hospital").document(userID)
                    .update("Departmentunit", test)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateHospitalActivity.this, "Updated departments", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < checkedItems.length; i++) {
                                checkedItems[i] = false;
                                mUserItems.clear();
                                mItemSelected.setText("");
                            }
                        }
                    });
        }

        if (!type.equals("No Type")) {
            fStore.collection("Hospital").document(userID)
                    .update("Hospitaltype", type)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateHospitalActivity.this, "Updated Hospital type", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        if (!yr.isEmpty()) {
            fStore.collection("Hospital").document(userID)
                    .update("Foundationyear", yr)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateHospitalActivity.this, "Updated Foundation Year", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        if (!location.equals("No Location")) {
            fStore.collection("Hospital").document(userID)
                    .update("Hospitallocation", location)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateHospitalActivity.this, "Updated Location", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}