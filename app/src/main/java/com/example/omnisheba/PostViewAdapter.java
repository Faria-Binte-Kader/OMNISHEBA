package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class PostViewAdapter extends RecyclerView.Adapter<PostViewHolderMSS> implements PostViewAdapterMSS {

    DiscussionForum discussionForum;
    ArrayList<Post> postArrayList;

    public PostViewAdapter(DiscussionForum discussionForum, ArrayList<Post> postArrayList) {
        this.discussionForum= discussionForum;
        this.postArrayList = postArrayList;
    }


    @NonNull
    @Override
    public PostViewHolderMSS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(discussionForum.getBaseContext());
        View view= layoutInflater.inflate(R.layout.row_post2,parent,false);
        return new PostViewHolderMSS(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolderMSS holder, int position) {
        holder.keyword3.setText(postArrayList.get(position).getKey());
        holder.question3.setText(postArrayList.get(position).getQuestion());
        holder.doctorname3.setText(postArrayList.get(position).getDoctor());
        holder.showanswer3.setText(postArrayList.get(position).getAnswer());

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }
}
