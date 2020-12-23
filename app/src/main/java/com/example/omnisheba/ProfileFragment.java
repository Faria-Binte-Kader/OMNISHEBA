package com.example.omnisheba;

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

public class ProfileFragment extends Fragment {
    private TextView name, email, description, phone, dob, gender;
    FirebaseAuth fAuthMSS;
    FirebaseFirestore fStore;
    String userID;

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
                name.setText(value.getString("Name"));
                email.setText(value.getString("Email"));
                gender.setText(value.getString("Gender"));
                dob.setText(value.getString("DOB"));
                phone.setText(value.getString("Phone"));
                description.setText(value.getString("Description"));
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
