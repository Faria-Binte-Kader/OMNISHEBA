package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

/**
 * Class to show the Medical service seeker's information in the profile fragment
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView name, email, description, phone, dob, gender;
    FirebaseAuth fAuthMSS;
    FirebaseFirestore fStore;
    String userID;

    /**
     * When created, set layout to fragment_profile
     * Setup FirebaseAuthand FirebaseFirestore
     * Fetch the information of the user from firebase and
     * set them to their respective Textview
     * @param inflater
     * @param container
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        name = v.findViewById(R.id.mssname);
        email = v.findViewById(R.id.mssemail);
        description = v.findViewById(R.id.mssdescription);
        dob = v.findViewById(R.id.mssdob);
        phone = v.findViewById(R.id.mssphone);
        gender = v.findViewById(R.id.mssgender);
        fAuthMSS = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthMSS.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("MSS").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null)
                {
                name.setText(value.getString("Name"));
                email.setText(value.getString("Email"));
                gender.setText(value.getString("Gender"));
                dob.setText(value.getString("DOB"));
                phone.setText(value.getString("Phone"));
                description.setText(value.getString("Description"));
            }}
        });

        return v;
    }

    /**
     * When view is created set on Click listener to the mssAppointment button
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.mssAppointment_btn).setOnClickListener((View.OnClickListener) this);
    }

    /**
     * Method to go to the activity to show the appointmnets of the user from the profile fragment
     */
    private void view() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), AppointmentsMss.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Set the action to the mssAppointment button when clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mssAppointment_btn:
                view();
                break;
        }
    }
}