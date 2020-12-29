package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorDiscussionForum extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseFirestore fStore;
    ArrayList<Post> postArrayList;
    PostViewAdapterdoctor adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_discussion_forum);
        getSupportActionBar().setTitle("0!");
        postArrayList=new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        if(postArrayList.size()>0)
            postArrayList.clear();
        fStore.collection("Questionanswer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot: task.getResult()){
                            Post post = new Post(querySnapshot.getString("Key"),
                                    querySnapshot.getString("Question"),
                                    querySnapshot.getString("userID"),
                                    querySnapshot.getString("Doctor"),
                                    querySnapshot.getString("Answer"),
                                    querySnapshot.getString("PostID")
                            );
                            postArrayList.add(post);
                        }
                        adapter = new PostViewAdapterdoctor(DoctorDiscussionForum.this,postArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DoctorDiscussionForum.this,"Problem ---I---",Toast.LENGTH_SHORT).show();
                        Log.v("---I---",e.getMessage());
                    }
                });
    }

    private void setUpFireBase() {
        fStore = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.postRecycle2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}