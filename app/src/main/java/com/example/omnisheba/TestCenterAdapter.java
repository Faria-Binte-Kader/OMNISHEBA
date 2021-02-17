package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class to Show the Test center information in the recyclerview
 * @author Nafisa Hossain Nujat
 */
public class TestCenterAdapter extends RecyclerView.Adapter<ViewHolderTestCenter> {

    FindTestCenter findTestCenter;
    ArrayList<TestCenter> testCenterArrayList;

    /**
     * Constructor
     * @param findTestCenter The type of view to show
     * @param testCenterArrayList The views list to show in the Recyclerview
     */
    public TestCenterAdapter(FindTestCenter findTestCenter, ArrayList<TestCenter> testCenterArrayList) {
        this.findTestCenter = findTestCenter;
        this.testCenterArrayList = testCenterArrayList;
    }

    /**
     * Viewholder to hold the information of the Test centers
     * @param parent
     * @param viewType
     * @return the created views
     */
    @NonNull
    @Override
    public ViewHolderTestCenter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findTestCenter.getBaseContext());
        View view = layoutInflater.inflate(R.layout.test_center_list,parent,false);
        return new ViewHolderTestCenter(view);
    }

    /**
     * Show the name, type, description, location, hotline, email, foundation year of the test centers
     * @param holder
     * @param position
     */
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

    /**
     * count the number of items to show in the Recyclerview
     * @return the testcenter array size
     */
    @Override
    public int getItemCount() {
        return testCenterArrayList.size();
    }
}
