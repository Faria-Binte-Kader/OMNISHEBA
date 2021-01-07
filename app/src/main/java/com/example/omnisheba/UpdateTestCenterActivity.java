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

public class UpdateTestCenterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button testsBtn,updatebtn;
    private EditText name, email,oldpass, newpass, conpass,description, hotline;
    Spinner testcenter_type, locationTC_type;
    public static final String TAG = "TAG";
    FirebaseAuth fAuthtc;
    private FirebaseUser user;
    FirebaseFirestore fStore;
    String userID;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<String> test = new ArrayList<String>();
    private String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_test_center);
        getSupportActionBar().setTitle("0!");

        testcenter_type = findViewById(R.id.hospital_types);
        testcenter_type.setOnItemSelectedListener(this);
        locationTC_type = findViewById(R.id.location_type);
        locationTC_type.setOnItemSelectedListener(this);
        name = findViewById(R.id.tcname);
        email = findViewById(R.id.tcmail);
        oldpass = findViewById(R.id.tcoldpass);
        newpass = findViewById(R.id.tcnewpass);
        conpass= findViewById(R.id.tcconpass);
        description = findViewById(R.id.tcdesc);
        hotline = findViewById(R.id.tchotline);
        fAuthtc = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthtc.getCurrentUser().getUid();
        updatebtn = findViewById(R.id.tcUpdate_button);
        testsBtn = findViewById(R.id.btnUpdateTests);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.test_list);
        checkedItems = new boolean[listItems.length];

        DocumentReference documentReference = fStore.collection("TC").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                loc=value.getString("Testcenterlocation");


            }
        });

        testsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateTestCenterActivity.this);
                mBuilder.setTitle(R.string.dialog_title5);
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
                Updatetc();
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void Updatetc() {
        final String nam = name.getText().toString();
        final String mail = email.getText().toString();
        final String oldpassword = oldpass.getText().toString();
        final String Password = newpass.getText().toString();
        String conPassword = conpass.getText().toString();
        final String desc = description.getText().toString();
        final String phon = hotline.getText().toString();
        final String type = testcenter_type.getSelectedItem().toString();
        final String location = locationTC_type.getSelectedItem().toString();

        if ( !nam.isEmpty() && nam.length() < 7) {
            showError(name, "Your Name is not valid");
            return;
        }
        if((mail.isEmpty() || oldpassword.isEmpty()) && !Password.isEmpty())
        {  showError(email, "Please input mail and old password");
            return;
        }
        if (!Password.isEmpty() && Password.length() < 7) {
            showError(newpass, "Password must be at least 7 characters");
            return;
        }
        if ((conPassword.isEmpty() || !conPassword.equals(Password))&& !Password.isEmpty()) {
            showError(conpass, "Password does not match");
            return;
        }

        if(!mail.isEmpty() && !oldpassword.isEmpty() && !conPassword.isEmpty())
        {

            user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(mail,oldpassword);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                View coordinatorLayout;
                                if(!task.isSuccessful()){
                                    Log.d(TAG, "Error");
                                }else {
                                    Toast.makeText(UpdateTestCenterActivity.this, "Password changed ", Toast.LENGTH_SHORT).show();
                                    oldpass.setText("");
                                    newpass.setText("");
                                    conpass.setText("");
                                    email.setText("");

                                }
                            }
                        });
                    }else {
                        Toast.makeText(UpdateTestCenterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        if(!nam.isEmpty())
        {
            fStore.collection("TC").document(userID)
                    .update("Name",nam)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Name", Toast.LENGTH_SHORT).show();
                            name.setText("");
                        }
                    });
            fStore.collection("Location").document(loc).collection("TestCenters").document(userID)
                    .update("Name",nam)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Name", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

        if(!desc.isEmpty())
        {
            fStore.collection("TC").document(userID)
                    .update("Description",desc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Description", Toast.LENGTH_SHORT).show();
                            description.setText("");
                        }
                    });
            fStore.collection("Location").document(loc).collection("TestCenters").document(userID)
                    .update("Description",desc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Description", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

        if(!phon.isEmpty())
        {
            fStore.collection("TC").document(userID)
                    .update("Hotline",phon)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Hotline", Toast.LENGTH_SHORT).show();
                            hotline.setText("");
                        }
                    });
            fStore.collection("Location").document(loc).collection("TestCenters").document(userID)
                    .update("Hotline",phon)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Hotline", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

        if(test.size()>0)
        {
            fStore.collection("TC").document(userID)
                    .update("Test",test)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated tests", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < checkedItems.length; i++) {
                                checkedItems[i] = false;
                                mUserItems.clear();
                                mItemSelected.setText("");
                            }
                        }
                    });
            fStore.collection("Location").document(loc).collection("TestCenters").document(userID)
                    .update("Test",test)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated departments", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

        if(!type.equals("No Type"))
        {
            fStore.collection("TC").document(userID)
                    .update("Testcentertype",type)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Test Center type", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Location").document(loc).collection("TestCenters").document(userID)
                    .update("Testcentertype",type)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Test Center type", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

        if(!location.equals("No Location"))
        {
            fStore.collection("TC").document(userID)
                    .update("Testcenterlocation",location)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateTestCenterActivity.this, "Updated Location", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}