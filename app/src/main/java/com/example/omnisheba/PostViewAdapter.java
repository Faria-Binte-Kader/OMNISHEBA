package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class to Show the questions that medical service seekers asked with
 * the answer along with the doctor's name in the recyclerview
 * @author Faria Binte Kader
 */
public class PostViewAdapter extends RecyclerView.Adapter<PostViewHolderMSS> {

    DiscussionForum discussionForum;
    ArrayList<Post> postArrayList;

    /**
     * Constructor
     * @param discussionForum The type of view to show
     * @param postArrayList The views list to show in the Recyclerview
     */
    public PostViewAdapter(DiscussionForum discussionForum, ArrayList<Post> postArrayList) {
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
    public PostViewHolderMSS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(discussionForum.getBaseContext());
        View view= layoutInflater.inflate(R.layout.row_post2,parent,false);
        return new PostViewHolderMSS(view);
    }

    /**
     * Show the keyword, question, doctorname, and answer if available
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PostViewHolderMSS holder, int position) {
        holder.keyword3.setText(postArrayList.get(position).getKey());
        holder.question3.setText(postArrayList.get(position).getQuestion());
        holder.doctorname3.setText(postArrayList.get(position).getDoctor());
        holder.showanswer3.setText(postArrayList.get(position).getAnswer());

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
