package com.example.omnisheba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Class to log out doctor type users from the system
 * @author Nafisa Hossain Nujat
 */
public class LogoutFragment extends Fragment implements View.OnClickListener {

    /**
     * To attach the layout to the fragment when created and set the title manually
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    /**
     * to set onCliclk Listener to the buttons
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.yes_btnmss).setOnClickListener(this);
        view.findViewById(R.id.no_btnmss).setOnClickListener(this);
    }

    /**
     * Method to log out medical service seekers from the system
     * and take then to login activity again
     * Clear tasks so the users cannot go back to their profile when back button pressed
     */
    private void logout() {
        if( FirebaseAuth.getInstance()!=null)
        {
        FirebaseAuth.getInstance().signOut();
        Log.d("TAG","Loggedout successfully");
        Intent intent = new Intent(getActivity(), login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().onBackPressed();
    }}

    /**
     * Method to logout when yes button is clicked
     * or to take the user to the search fragment from the main menu if no button is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.yes_btnmss:
                logout();
                break;
            case R.id.no_btnmss:
                fragment = new SearchFragment();
                replaceFragment(fragment);
                break;
        }
    }

    /**
     * Method to replace the current fragment with another fragment
     * @param someFragment
     */
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}