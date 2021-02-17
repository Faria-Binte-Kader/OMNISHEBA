package com.example.omnisheba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Class to show the HELP fragment which includes the general instructions
 * on how to use the application, to the medical service seekers
 * @author Nafisa Hossain Nujat
 */
public class HelpFragment extends Fragment {
    @Nullable
    @Override

    /**
     * Method to attach the fxml layout and set the title manually when created
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        return inflater.inflate(R.layout.fragment_help, container, false);
    }
}
