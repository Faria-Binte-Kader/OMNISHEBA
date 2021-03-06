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

/**
 * Adapter class to Show the questions that medical service seekers asked with
 * an edittext to post the answer along with the doctor's name in the recyclerview
 * @author Faria Binte Kader
 */
public class PostViewAdapterdoctor extends RecyclerView.Adapter<PostViewHolder> {

        DoctorDiscussionForum discussionForum;
        ArrayList<Post> postArrayList;

    /**
     * Constructor
     * @param discussionForum The type of view to show
     * @param postArrayList The views list to show in the Recyclerview
     */
    public PostViewAdapterdoctor(DoctorDiscussionForum discussionForum, ArrayList<Post> postArrayList) {
        this.discussionForum= discussionForum;
        this.postArrayList = postArrayList;
        }

    /**
     * Viewholder to hold the questions and answers
     * @param parent
     * @param viewType
     * @return the created views
     */
    @NonNull
@Override
public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(discussionForum.getBaseContext());
        View view= layoutInflater.inflate(R.layout.row_post,parent,false);
        return new PostViewHolder(view);
        }

    /**
     * Show the keyword, question, doctorname, and answer
     * @param holder
     * @param position
     */
@Override
public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
        holder.keyword2.setText(postArrayList.get(position).getKey());
        holder.question2.setText(postArrayList.get(position).getQuestion());
        holder.doctorname.setText(postArrayList.get(position).getDoctor());
        holder.showanswer.setText(postArrayList.get(position).getAnswer());
        final String id= postArrayList.get(position).getPostID();


    holder.postanswer.setOnClickListener(new View.OnClickListener() {
        /**
         * when clicked save the answer to the question in the firebase
         * @param v
         */
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

    /**
     * count the number of items to show in the Recyclerview
     * @return the post array size
     */
@Override
public int getItemCount() {
        return postArrayList.size();
        }
}
