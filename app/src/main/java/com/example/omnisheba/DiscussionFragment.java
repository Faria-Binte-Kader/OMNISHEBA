package com.example.omnisheba;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to post new questions to the discussion forum by the Medical Service Seeker
 * @author
 */
public class DiscussionFragment extends Fragment implements View.OnClickListener {
    private TextView key, ques;
    ImageView addbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Dialog popup;
    String userID;

    /**
     * When created, set layout to fragment_discussion
     * Setup FirebaseAuthand FirebaseFirestore
     * @param inflater
     * @param container
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        View view = inflater.inflate(R.layout.fragment_discussion, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        return view;
    }

    /**
     * When view is created, initialize the popup to add posts to the discussion forum
     * Set action of the floating action button to show the popup dialogue.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.qbtn).setOnClickListener(this);
        iniPopup();
        FloatingActionButton fabtn = view.findViewById(R.id.fab);
        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.show();
            }
        });
    }

    /**
     * Initialize the popup to add questions to the discussion forum.
     * Save the asked question in the Questionanswer collection of the firebase firestore.
     */
    private void iniPopup() {
        popup = new Dialog(getActivity());
        popup.setContentView(R.layout.popup);
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popup.getWindow().getAttributes().gravity = Gravity.TOP;

        key = popup.findViewById(R.id.keyword);
        ques = popup.findViewById(R.id.question);
        addbtn = popup.findViewById(R.id.fab2);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addbtn.setVisibility((View.INVISIBLE));
                if (!(key.getText().toString().equals("")) && !(ques.getText().toString().equals(""))) {
                    Toast.makeText(getActivity(), "Posting", Toast.LENGTH_LONG).show();

                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Questionanswer").document();
                    Map<String, Object> qna = new HashMap<>();
                    qna.put("userID", userID);
                    qna.put("Question", ques.getText().toString());
                    qna.put("Key", key.getText().toString().toUpperCase());
                    qna.put("Time", FieldValue.serverTimestamp());
                    qna.put("Answer", "");
                    qna.put("Doctor", "");
                    qna.put("PostID", documentReference.getId());

                    documentReference.set(qna).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Post added!", Toast.LENGTH_LONG).show();
                            addbtn.setVisibility((View.VISIBLE));
                            key.setText("");
                            ques.setText("");
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Please fill the keyword and write your question!", Toast.LENGTH_LONG).show();
                    addbtn.setVisibility((View.VISIBLE));
                }
            }
        });
    }

    /**
     * When the View Questions button is clicked, go to DiscussionForum.class to view the questions.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qbtn:
                SharedPrefManager.getInstance(getActivity()).clear();
                Intent intent = new Intent(getActivity(), DiscussionForum.class);
                startActivity(intent);
                break;
        }
    }
}
