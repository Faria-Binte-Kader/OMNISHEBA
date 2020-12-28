package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class PostViewAdapter extends RecyclerView.Adapter<PostViewHolder> {

    DiscussionForum discussionForum;
    ArrayList<Post> postArrayList;

    public PostViewAdapter(DiscussionForum discussionForum, ArrayList<Post> postArrayList) {
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
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.keyword2.setText(postArrayList.get(position).getKey());
        holder.question2.setText(postArrayList.get(position).getQuestion());

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }
}
