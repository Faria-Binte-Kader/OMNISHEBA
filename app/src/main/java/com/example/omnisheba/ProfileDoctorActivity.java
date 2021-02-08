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

/**
 * Class to show the doctor type users's information in the profile activity
 */
public class ProfileDoctorActivity extends AppCompatActivity {

    private TextView name, email, description, hospitalName, practiceYear, location, specialty, sat, satmon, sateve, sunmon, suneve, monmon, moneve, tuesmon, tueseve, wedmon, wedeve,
            thursmon, thurseve, frimon, frieve;
    FirebaseAuth fAuthDoctor;
    FirebaseFirestore fStore;
    String userID;

    /**
     * Method to set the layout and
     * to fetch necessary information from the firebase and show them in their respective Textviews
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);
        getSupportActionBar().setTitle("0!");

        name = findViewById(R.id.doctorname);
        email = findViewById(R.id.doctoremail);
        description = findViewById(R.id.doctordescription);
        hospitalName = findViewById(R.id.doctorhospital);
        practiceYear = findViewById(R.id.practiceyear);
        location = findViewById(R.id.doctorlocation);

        satmon = findViewById(R.id.showSatMon);
        sunmon = findViewById(R.id.showSunMon);
        monmon = findViewById(R.id.showMonMon);
        tuesmon = findViewById(R.id.showTuesMon);
        wedmon = findViewById(R.id.showWedMon);
        thursmon = findViewById(R.id.showthursMon);
        frimon = findViewById(R.id.showFriMon);

        sateve = findViewById(R.id.showSatEve);
        suneve = findViewById(R.id.showSunEve);
        moneve = findViewById(R.id.showMonEve);
        tueseve = findViewById(R.id.showTuesEve);
        wedeve = findViewById(R.id.showWedEve);
        thurseve = findViewById(R.id.showThursEve);
        frieve = findViewById(R.id.showFriEve);

        fAuthDoctor = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthDoctor.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Doctor").document(userID);
        DocumentReference documentReference2 = fStore.collection("Appointment").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            /**
             * Method to fetch doctor information from firebase
             * @param value
             * @param error
             */
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null)
                {
                name.setText(value.getString("Name"));
                email.setText(value.getString("Email"));
                hospitalName.setText(value.getString("Hospitalchambername"));
                practiceYear.setText(value.getString("Practicesatrtingyear"));
                description.setText(value.getString("Description"));
                location.setText(value.getString("Hospitalchamberlocation"));

            }}
        });
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            /**
             * Method to fetch appointment schedule of the doctor from the firebase
             * @param value
             * @param error
             */
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null)
                {
                satmon.setText(value.getString("Satmon"));
                sunmon.setText(value.getString("Sunmon"));
                monmon.setText(value.getString("Monmon"));
                tuesmon.setText(value.getString("Tuesmon"));
                wedmon.setText(value.getString("Wedmon"));
                thursmon.setText(value.getString("Thursmon"));
                frimon.setText(value.getString("Frimon"));

                sateve.setText(value.getString("Sateve"));
                suneve.setText(value.getString("Suneve"));
                moneve.setText(value.getString("Moneve"));
                tueseve.setText(value.getString("Tuesve"));
                wedeve.setText(value.getString("Wedeve"));
                thurseve.setText(value.getString("Thurseve"));
                frieve.setText(value.getString("Frieve"));

            }}
        });

    }
}