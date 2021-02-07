package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Class to show and answer the list of questions and answers asked by the Medical Service Seekers to the Doctors.
 * @author
 */
public class DoctorDiscussionForum extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseFirestore fStore;
    ArrayList<Post> postArrayList;
    PostViewAdapterdoctor adapter;

    /**
     * When created, setup Recyclerview and Firebase.
     * Load all posts of questions and answers from firebase.
     * Activate the search option.
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_discussion_forum);
        getSupportActionBar().setTitle("0!");
        postArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
        searchDataInFirebase();
    }

    /**
     * Load all posts from the Questionanswer collection in the Firebase Firestore.
     * Use PostViewAdapterdoctor to adapt the postArraylist to the recyclerview
     */
    private void loadDataFromFirebase() {
        if (postArrayList.size() > 0)
            postArrayList.clear();
        fStore.collection("Questionanswer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Post post = new Post(querySnapshot.getString("Key"),
                                    querySnapshot.getString("Question"),
                                    querySnapshot.getString("userID"),
                                    querySnapshot.getString("Doctor"),
                                    querySnapshot.getString("Answer"),
                                    querySnapshot.getString("PostID")
                            );
                            postArrayList.add(post);
                        }
                        adapter = new PostViewAdapterdoctor(DoctorDiscussionForum.this, postArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DoctorDiscussionForum.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    /**
     * Query on the posts that match with the text in the Searchview in the Questionanswer collection in the Firebase Firestore.
     * Use PostViewAdapterdoctor to adapt the postArraylist to the recyclerview
     */
    private void searchDataInFirebase() {
        if (postArrayList.size() > 0)
            postArrayList.clear();
        SearchView searchView = findViewById(R.id.searchbykeyworddoctor);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (postArrayList.size() > 0)
                    postArrayList.clear();
                fStore.collection("Questionanswer")
                        .whereGreaterThanOrEqualTo("Key", s.toUpperCase())
                        .orderBy("Key").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    Post post = new Post(querySnapshot.getString("Key"),
                                            querySnapshot.getString("Question"),
                                            querySnapshot.getString("userID"),
                                            querySnapshot.getString("Doctor"),
                                            querySnapshot.getString("Answer"),
                                            querySnapshot.getString("PostID")
                                    );
                                    postArrayList.add(post);
                                }
                                adapter = new PostViewAdapterdoctor(DoctorDiscussionForum.this, postArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DoctorDiscussionForum.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                Log.v("---I---", e.getMessage());
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * Set up Firebase Firestore
     */
    private void setUpFireBase() {
        fStore = FirebaseFirestore.getInstance();
    }

    /**
     * Set up Recyclerview
     */
    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.postRecycle2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}