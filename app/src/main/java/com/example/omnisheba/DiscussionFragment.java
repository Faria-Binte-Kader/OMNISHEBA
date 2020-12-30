package com.example.omnisheba;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscussionFragment extends Fragment implements View.OnClickListener {
    private TextView key, ques;
    private Button qbtn;
    private PostViewAdapter adapter;
    private RecyclerView recycle;
    ArrayList<Post> postArrayList;
    ImageView addbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Dialog popup;
    String userID;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        View view = inflater.inflate(R.layout.fragment_discussion, container, false);
        //recycle = (RecyclerView) view.findViewById(R.id.postRecycle);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        /*postArrayList=new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);
        //loadDataFromFirebase();*/
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.qbtn).setOnClickListener(this);
        //loadDataFromFirebase();
       /* Query query;
        query = fStore.collection("Questionanswer").orderBy("Time");
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class).build();
        adapter= new PostViewAdapter(options) {
            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent,false);
                return new PostViewHolder(view);
            }


            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
             holder.keyword2.setText(model.getKey());
             holder.question2.setText(model.getQuestion());
            }


        };
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
                                    querySnapshot.getString("Time")
                            );
                            postArrayList.add(post);
                        }
                       ////adapter = new PostViewAdapter(getActivity(),postArrayList);
                        recycle.setAdapter(adapter);
                    }
                });*/

        iniPopup();

        FloatingActionButton fabtn = view.findViewById(R.id.fab);
        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.show();
            }


        });

    }

    /*private void loadDataFromFirebase() {
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
                                    querySnapshot.getString("Time")
                                    );
                            postArrayList.add(post);
                        }
                        adapter = new PostViewAdapter(getActivity(),postArrayList);
                       // recycle.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Problem ---I---",Toast.LENGTH_SHORT).show();
                        Log.v("---I---",e.getMessage());
                    }
                });
    }*/

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
                    DocumentReference documentReference =  fStore.collection("Questionanswer").document();
                    Map<String, Object> qna = new HashMap<>();
                    qna.put("userID", userID);
                    qna.put("Question", ques.getText().toString());
                    qna.put("Key", key.getText().toString().toUpperCase());
                    qna.put("Time", FieldValue.serverTimestamp());
                    qna.put("Answer","");
                    qna.put("Doctor","");
                    qna.put("PostID",documentReference.getId());

                    documentReference.set(qna).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Post added!", Toast.LENGTH_LONG).show();
                            addbtn.setVisibility((View.VISIBLE));
                            key.setText("");
                            ques.setText("");
                        }
                    });

                    /*fStore.collection("Questionanswer").add(qna).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getActivity(), "Post added!", Toast.LENGTH_LONG).show();
                            addbtn.setVisibility((View.VISIBLE));
                            key.setText("");
                            ques.setText("");
                        }

                    });*/

                } else {
                    Toast.makeText(getActivity(), "Please fill the keyword and write your question!", Toast.LENGTH_LONG).show();
                    addbtn.setVisibility((View.VISIBLE));
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.qbtn:
                SharedPrefManager.getInstance(getActivity()).clear();
                Intent intent = new Intent(getActivity(), DiscussionForum.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
    }
}
