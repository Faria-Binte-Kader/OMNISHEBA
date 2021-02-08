package com.example.omnisheba;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostViewAdapterdoctor extends RecyclerView.Adapter<PostViewHolder> {

        DoctorDiscussionForum discussionForum;
        ArrayList<Post> postArrayList;

public PostViewAdapterdoctor(DoctorDiscussionForum discussionForum, ArrayList<Post> postArrayList) {
        this.discussionForum= discussionForum;
        this.postArrayList = postArrayList;
        }


@NonNull
@Override
public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(discussionForum.getBaseContext());
        View view= layoutInflater.inflate(R.layout.row_post,parent,false);
        return new PostViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
        holder.keyword2.setText(postArrayList.get(position).getKey());
        holder.question2.setText(postArrayList.get(position).getQuestion());
        holder.doctorname.setText(postArrayList.get(position).getDoctor());
        holder.showanswer.setText(postArrayList.get(position).getAnswer());
        final String id= postArrayList.get(position).getPostID();
        holder.postanswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        FirebaseAuth fAuthpost;
                         final FirebaseFirestore fStorepost;
                         String userIDpost;

                        fAuthpost = FirebaseAuth.getInstance();
                        fStorepost = FirebaseFirestore.getInstance();
                        userIDpost = fAuthpost.getCurrentUser().getUid();
                        DocumentReference documentReference = fStorepost.collection("Doctor").document(userIDpost);
                        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value!=null)
                                    {String namepost=value.getString("Name");
                                        fStorepost.collection("Questionanswer").document(id)
                                                .update("Doctor",namepost)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                                Log.d("TAG","Success");
                                                        }
                                                });

                                }}
                        });
                        Editable an=holder.answer.getText();

                        fStorepost.collection("Questionanswer").document(id)
                                .update("Answer",an.toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                                Log.d("TAG","Success");
                                        }
                                });

                }
        });

        }

@Override
public int getItemCount() {
        return postArrayList.size();
        }
}
