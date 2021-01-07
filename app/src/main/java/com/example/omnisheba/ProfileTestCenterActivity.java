package com.example.omnisheba;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileTestCenterActivity extends AppCompatActivity {

    private TextView name, type, description, email, hotline, location, foundationYear;
    FirebaseAuth fAuthTestCenter;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_test_center);
        getSupportActionBar().setTitle("0!");

        name = findViewById(R.id.testCenterName);
        type = findViewById(R.id.testCenterType);
        description = findViewById(R.id.testCenterDescription);
        email = findViewById(R.id.testCenterEmail);
        hotline = findViewById(R.id.testCenterHotline);
        location = findViewById(R.id.testCenterLocation);
        foundationYear = findViewById(R.id.testCenterFoundationYear);

        fAuthTestCenter = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthTestCenter.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("TC").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null)
                {
                name.setText(value.getString("Name"));
                type.setText(value.getString("Testcentertype"));
                description.setText(value.getString("Description"));
                email.setText(value.getString("Email"));
                hotline.setText(value.getString("Hotline"));
                location.setText(value.getString("Testcenterlocation"));
                foundationYear.setText(value.getString("Foundationyear"));
            }}
        });
    }
}