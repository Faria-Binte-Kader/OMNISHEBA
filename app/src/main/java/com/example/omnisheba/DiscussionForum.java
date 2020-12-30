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

public class DiscussionForum extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseFirestore fStore;
    ArrayList<Post> postArrayList;
    PostViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);
        getSupportActionBar().setTitle("0!");
        postArrayList=new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (postArrayList.size() > 0)
            postArrayList.clear();
        SearchView searchView = findViewById(R.id.searchbykeyword);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (postArrayList.size() > 0)
                    postArrayList.clear();
                fStore.collection("Questionanswer")
                        .whereGreaterThanOrEqualTo("Key",s.toUpperCase())
                        .orderBy("Key").startAt(s.toUpperCase()).endAt(s.toUpperCase()+"\uf8ff")
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
                                adapter = new PostViewAdapter(DiscussionForum.this,postArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DiscussionForum.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
                        adapter = new PostViewAdapter(DiscussionForum.this,postArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DiscussionForum.this,"Problem ---I---",Toast.LENGTH_SHORT).show();
                        Log.v("---I---",e.getMessage());
                    }
                });
    }

    private void setUpFireBase() {
        fStore = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.postRecycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}