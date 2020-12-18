package com.example.omnisheba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class signup_testcenter extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button alreadyHaveAccount;
    private EditText inputName, inputEmail, inputPassword, confirmPassword;

    Button testsBtn;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_testcenter);
        getSupportActionBar().setTitle("0!");
        Spinner hospital_types = findViewById(R.id.hospital_types);
        hospital_types.setOnItemSelectedListener( this);
        Spinner location_type = findViewById(R.id.location_type);
        location_type.setOnItemSelectedListener(this);

        testsBtn = findViewById(R.id.btnTests);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.test_list);
        checkedItems = new boolean[listItems.length];

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountTC);
        inputName = findViewById(R.id.inputNameTC);
        inputEmail = findViewById(R.id.inputEmailTC);
        inputPassword = findViewById(R.id.inputPasswordTC);
        confirmPassword = findViewById(R.id.confirmPasswordTC);

        testsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_testcenter.this);
                mBuilder.setTitle(R.string.dialog_title5);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked)
                    {
                        if(isChecked)
                        {
                            mUserItems.add(position);
                        }
                        else
                        {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++)
                        {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        for (int i = 0; i < checkedItems.length; i++)
                        {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    @Override
    public void  onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {     ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void signupbutton(View view) {
        checkCredentials();
    }

    public void gotoLoginPage(View view) {
        Intent intent = new Intent(signup_testcenter.this,login.class);
        startActivity(intent);
    }
    private void checkCredentials() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String conPassword = confirmPassword.getText().toString();

        if(name.isEmpty() || name.length() < 7)
            showError(inputName, "Your Name is not valid");
        else if(email.isEmpty() || !email.contains("@"))
            showError(inputEmail, "Email is not Valid");
        else if(password.isEmpty() || password.length()<7)
            showError(inputPassword, "Password must be at least 7 characters");
        else if(conPassword.isEmpty() || !conPassword.equals(password))
            showError(confirmPassword, "Password does not match");
        else
            Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}