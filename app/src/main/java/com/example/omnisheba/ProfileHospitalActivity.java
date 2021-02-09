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

/**
 * Class to show the Hospital type users's information in the profile activity
 */
public class ProfileHospitalActivity extends AppCompatActivity {

    private TextView name, type, description, email, hotline, location, foundationYear;
    FirebaseAuth fAuthHospital;
    FirebaseFirestore fStore;
    String userID;

    /**
     *Method to set the layout and
     *to fetch necessary information from the firebase and show them in their respective Textviews
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_hospital);
        getSupportActionBar().setTitle("0!");

        name = findViewById(R.id.hospitalName);
        type = findViewById(R.id.hospitalType);
        description = findViewById(R.id.hospitalDescription);
        email = findViewById(R.id.hospitalEmail);
        hotline = findViewById(R.id.hospitalHotline);
        location = findViewById(R.id.hospitalLocation);
        foundationYear = findViewById(R.id.hospitalFoundationYear);

        fAuthHospital = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthHospital.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Hospital").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name.setText(value.getString("Name"));
                    type.setText(value.getString("Hospitaltype"));
                    description.setText(value.getString("Description"));
                    email.setText(value.getString("Email"));
                    hotline.setText(value.getString("Hotline"));
                    location.setText(value.getString("Hospitallocation"));
                    foundationYear.setText(value.getString("Foundationyear"));
                }
            }
        });
    }
}