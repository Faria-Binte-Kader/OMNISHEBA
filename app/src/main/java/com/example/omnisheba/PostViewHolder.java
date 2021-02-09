package com.example.omnisheba;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * View holder class to hold the view of the posts to show the doctors in the recyclerview
 */
public class PostViewHolder extends  RecyclerView.ViewHolder{
    public TextView keyword2;
    public TextView question2;
    public TextView doctorname,showanswer;
    public EditText answer;
    public Button postanswer;
    public FirebaseAuth fAuthpost;
    public FirebaseFirestore fStorepost;
    public String userIDpost,namepost;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        keyword2= itemView.findViewById(R.id.keywordlist);
        question2= itemView.findViewById(R.id.questionlist);
        doctorname= itemView.findViewById(R.id.doctornameqna);
        showanswer= itemView.findViewById(R.id.Showanswer);
        answer= itemView.findViewById(R.id.Addanswer);
        postanswer= itemView.findViewById(R.id.postButtondoctor);



    }
}
