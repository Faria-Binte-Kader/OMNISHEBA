package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestCenterAdapter extends RecyclerView.Adapter<ViewHolderTestCenter> {

    FindTestCenter findTestCenter;
    ArrayList<TestCenter> testCenterArrayList;

    public TestCenterAdapter(FindTestCenter findTestCenter, ArrayList<TestCenter> testCenterArrayList) {
        this.findTestCenter = findTestCenter;
        this.testCenterArrayList = testCenterArrayList;
    }

    @NonNull
    @Override
    public ViewHolderTestCenter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findTestCenter.getBaseContext());
        View view = layoutInflater.inflate(R.layout.test_center_list,parent,false);
        return new ViewHolderTestCenter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTestCenter holder, int position) {
        holder.name.setText(testCenterArrayList.get(position).getName());
        holder.type.setText(testCenterArrayList.get(position).getType());
        holder.description.setText(testCenterArrayList.get(position).getDescription());
        holder.location.setText(testCenterArrayList.get(position).getLocation());
        holder.hotline.setText(testCenterArrayList.get(position).getHotline());
        holder.email.setText(testCenterArrayList.get(position).getEmail());
        holder.foundationYear.setText(testCenterArrayList.get(position).getFoundation());
    }

    @Override
    public int getItemCount() {
        return testCenterArrayList.size();
    }
}
