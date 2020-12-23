package com.example.omnisheba;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileDoctorActivity extends AppCompatActivity {

    private TextView name, email, description, hospitalName, practiceYear, location;
    FirebaseAuth fAuthDoctor;
    FirebaseFirestore fStore;
    String userID;
    Spinner location_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);
        getSupportActionBar().setTitle("0!");

        name = findViewById(R.id.inputNameDoctor);
        email = findViewById(R.id.inputEmailDoctor);
        description = findViewById(R.id.inputDescriptionDoctor);
        hospitalName = findViewById(R.id.inputHospitalNameDoctor);
        practiceYear = findViewById(R.id.inputPracticeYearDoctor);


        fAuthDoctor = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthDoctor.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Doctor").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("Name"));
                email.setText(value.getString("Email"));
                hospitalName.setText(value.getString("Hospital/Chamber Name"));
                practiceYear.setText(value.getString("Practice Starting Year"));
                description.setText(value.getString("Description"));
            }
        });
    }
}