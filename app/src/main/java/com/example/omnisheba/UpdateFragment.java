package com.example.omnisheba;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateFragment extends Fragment implements View.OnClickListener {
    private EditText name, email, oldpass, pass, pass2, description, phone;
    public static final String TAG = "TAG";
    FirebaseAuth fAuthMSS;
    private FirebaseUser user;
    FirebaseFirestore fStore;
    String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");

        View v = inflater.inflate(R.layout.fragment_update, container, false);
        name = v.findViewById(R.id.updatenamemss);
        email = v.findViewById(R.id.mailmss);
        oldpass = v.findViewById(R.id.oldpassmss);
        pass = v.findViewById(R.id.updatepassmss);
        pass2 = v.findViewById(R.id.confirmupdatepassmss);
        description = v.findViewById(R.id.updatedescmss);
        phone = v.findViewById(R.id.updatephonemss);
        fAuthMSS = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthMSS.getCurrentUser().getUid();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.mssUpdate_button).setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.mssUpdate_button:
                UpdateMss();
                break;
        }
    }

    private void UpdateMss() {
        final String nam = name.getText().toString();
        final String mail = email.getText().toString();
        final String oldpassword = oldpass.getText().toString();
        final String Password = pass.getText().toString();
        String conPassword = pass2.getText().toString();
        final String desc = description.getText().toString();
        final String phon = phone.getText().toString();

        if (!nam.isEmpty() && nam.length() < 7) {
            showError(name, "Your Name is not valid");
            return;
        }
        if ((mail.isEmpty() || oldpassword.isEmpty()) && !Password.isEmpty()) {
            showError(email, "Please input mail and old password");
            return;
        }
        if (!Password.isEmpty() && Password.length() < 7) {
            showError(pass, "Password must be at least 7 characters");
            return;
        }
        if ((conPassword.isEmpty() || !conPassword.equals(Password)) && !Password.isEmpty()) {
            showError(pass2, "Password does not match");
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
                                    Toast.makeText(getActivity(), "Password changed ", Toast.LENGTH_SHORT).show();
                                    oldpass.setText("");
                                    pass.setText("");
                                    pass2.setText("");
                                    email.setText("");
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (!nam.isEmpty()) {
            fStore.collection("MSS").document(userID)
                    .update("Name", nam.toUpperCase())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Updated Name", Toast.LENGTH_SHORT).show();
                            name.setText("");
                        }
                    });
        }

        if (!desc.isEmpty()) {
            fStore.collection("MSS").document(userID)
                    .update("Description", desc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Updated Description", Toast.LENGTH_SHORT).show();
                            description.setText("");
                        }
                    });
        }

        if (!phon.isEmpty()) {
            fStore.collection("MSS").document(userID)
                    .update("Phone", phon)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Updated phone number", Toast.LENGTH_SHORT).show();
                            phone.setText("");
                        }
                    });
        }
    }


    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
