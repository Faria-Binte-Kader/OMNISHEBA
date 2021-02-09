package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class to Show the hotline numbers of test center who do COVID tests in the recyclerview
 * @author
 */
public class HotlineAdapter extends RecyclerView.Adapter<ViewHolderHotline> {

    ContactHotline contactHotline;
    ArrayList<Hotline> hotlineArrayList;

    /**
     * Constructor
     * @param contactHotline The type of view to show
     * @param hotlineArrayList The views list to show in the Recyclerview
     */
    public HotlineAdapter(ContactHotline contactHotline, ArrayList<Hotline> hotlineArrayList) {
        this.contactHotline = contactHotline;
        this.hotlineArrayList = hotlineArrayList;
    }

    /**
     * Viewholder to hold the hotline information of the test center
     * @param parent
     * @param viewType
     * @return the created views
     */
    @NonNull
    @Override
    public ViewHolderHotline onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(contactHotline.getBaseContext());
        View view = layoutInflater.inflate(R.layout.hotline_list,parent,false);
        return new ViewHolderHotline(view);
    }

    /**
     * Show the name,email, hotline number and location of the test centers
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderHotline holder, int position) {
        holder.name.setText(hotlineArrayList.get(position).getName());
        holder.email.setText(hotlineArrayList.get(position).getEmail());
        holder.hotline.setText(hotlineArrayList.get(position).getHotline());
        holder.location.setText(hotlineArrayList.get(position).getLocation());
    }

    /**
     * count the number of items to show in the Recyclerview
     * @return the hotline array size
     */
    @Override
    public int getItemCount() {
        return hotlineArrayList.size();
    }
}
