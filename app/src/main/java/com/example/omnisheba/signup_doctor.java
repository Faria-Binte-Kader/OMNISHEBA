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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class signup_doctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button alreadyHaveAccount;

    Button specialtyBtn,workdaysBtn,shiftsBtn;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    TextView mItemSelected2;
    String[] listItems2;
    boolean[] checkedItems2;
    ArrayList<Integer> mUserItems2 = new ArrayList<>();

    TextView mItemSelected3;
    String[] listItems3;
    boolean[] checkedItems3;
    ArrayList<Integer> mUserItems3 = new ArrayList<>();
    CheckBox sat,sun,mon,tues,wed,thurs,fri,satmon,sateve,sunmon,suneve,monmon,moneve,tuesmon,tueseve,wedmon,wedeve,
            thursmon, thurseve,frimon,frieve;


    private EditText inputName, inputEmail, inputPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor);
        getSupportActionBar().setTitle("0!");

        //Spinner specialty_type = findViewById(R.id.specialty_type);
        //specialty_type.setOnItemSelectedListener( this);

        Spinner location_type = findViewById(R.id.location_type);
        location_type.setOnItemSelectedListener(this);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountDoctor);

        specialtyBtn = findViewById(R.id.btnSpecialty);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.specialty_list);
        checkedItems = new boolean[listItems.length];

        //workdaysBtn = findViewById(R.id.btnWorkDays);
        mItemSelected2 = (TextView) findViewById(R.id.tvItemSelected2);
        listItems2 = getResources().getStringArray(R.array.workday_list);
        checkedItems2 = new boolean[listItems2.length];

        //shiftsBtn = findViewById(R.id.btnShifts);

        mItemSelected3 = (TextView) findViewById(R.id.tvItemSelected3);
        listItems3 = getResources().getStringArray(R.array.shift_list);
        checkedItems3 = new boolean[listItems3.length];

        inputName = findViewById(R.id.inputNameDoctor);
        inputEmail = findViewById(R.id.inputEmailDoctor);
        inputPassword = findViewById(R.id.inputPasswordDoctor);
        confirmPassword = findViewById(R.id.confirmPasswordDoctor);

        sat = findViewById(R.id.Saturday);
        sun = findViewById(R.id.Sunday);
        mon = findViewById(R.id.Monday);
        tues = findViewById(R.id.Tuesday);
        wed = findViewById(R.id.Wednesday);
        thurs = findViewById(R.id.Thursday);
        fri = findViewById(R.id.Friday);

        satmon = findViewById(R.id.SaturdayMorning);
        sunmon = findViewById(R.id.SundayMorning);
        monmon = findViewById(R.id.MondayMorning);
        tuesmon = findViewById(R.id.TuesdayMorning);
        wedmon = findViewById(R.id.WednesdayMorning);
        thursmon = findViewById(R.id.ThursdayMorning);
        frimon = findViewById(R.id.FridayMorning);

        sateve = findViewById(R.id.SaturdayEvening);
        suneve = findViewById(R.id.SundayEvening);
        moneve = findViewById(R.id.MondayEvening);
        tueseve = findViewById(R.id.TuesdayEvening);
        wedeve = findViewById(R.id.WednesdayEvening);
        thurseve = findViewById(R.id.ThursdayEvening);
        frieve = findViewById(R.id.FridayEvening);



        specialtyBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_doctor.this);
                mBuilder.setTitle(R.string.dialog_title);
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

        /*workdaysBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_doctor.this);
                mBuilder.setTitle(R.string.dialog_title2);
                mBuilder.setMultiChoiceItems(listItems2, checkedItems2, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked)
                    {
                        if(isChecked)
                        {
                            mUserItems2.add(position);
                        }
                        else
                        {
                            mUserItems2.remove((Integer.valueOf(position)));
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
                        for (int i = 0; i < mUserItems2.size(); i++)
                        {
                            item = item + listItems2[mUserItems2.get(i)];
                            item2[i] = listItems2[mUserItems2.get(i)];
                            if (i != mUserItems2.size() - 1) {
                                item = item + ", ";

                            }
                        }
                        //shiftsBtn.setText(item2[0]);
                        mItemSelected2.setText(item);
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
                        for (int i = 0; i < checkedItems2.length; i++)
                        {
                            checkedItems2[i] = false;
                            mUserItems2.clear();
                            mItemSelected2.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        shiftsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_doctor.this);
                mBuilder.setTitle("Shifts");
                mBuilder.setMultiChoiceItems(listItems3, checkedItems3, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked)
                    {
                        if(isChecked)
                        {
                            mUserItems3.add(position);
                        }
                        else
                        {
                            mUserItems3.remove((Integer.valueOf(position)));
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

                        for (int i = 0; i < mUserItems3.size(); i++)
                        {
                            item = item + listItems3[mUserItems3.get(i)];

                            if (i != mUserItems3.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected3.setText(item);
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
                        for (int i = 0; i < checkedItems3.length; i++)
                        {
                            checkedItems3[i] = false;
                            mUserItems3.clear();
                            mItemSelected3.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        }); */

    }

   @Override
    public void  onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {     ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void signupbutton(View view) {
        checkCredentials();
    }
    public void gotoLoginPage(View view) {
        Intent intent = new Intent(signup_doctor.this,login.class);
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
