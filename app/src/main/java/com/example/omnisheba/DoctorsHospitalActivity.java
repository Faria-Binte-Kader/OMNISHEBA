package com.example.omnisheba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorsHospitalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    String[] s = {"No Specialty", "Cardiology","ENT","General","Medicine","Nephrology","Neurology","OB/GYN",
                    "Oncology","Opthalmology", "Physiology","Psychology", "Urology",};

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_hospital);
        getSupportActionBar().setTitle("0!");

        Spinner specialty_type3 = (Spinner) findViewById(R.id.specialty3_type);
        specialty_type3.setOnItemSelectedListener(this);

        Button btn = (Button) findViewById(R.id.addbtn);

        Button d1vbtn = (Button) findViewById(R.id.d1viewbtn);
        Button d1ubtn = (Button) findViewById(R.id.d1updatebtn);

        Button d2vbtn = (Button) findViewById(R.id.d2viewbtn);
        Button d2ubtn = (Button) findViewById(R.id.d2updatebtn);

        Button d3vbtn = (Button) findViewById(R.id.d3viewbtn);
        Button d3ubtn = (Button) findViewById(R.id.d3updatebtn);

        Button d4vbtn = (Button) findViewById(R.id.d4viewbtn);
        Button d4ubtn = (Button) findViewById(R.id.d4updatebtn);

        Button d5vbtn = (Button) findViewById(R.id.d5viewbtn);
        Button d5ubtn = (Button) findViewById(R.id.d5updatebtn);

        Button d6vbtn = (Button) findViewById(R.id.d6viewbtn);
        Button d6ubtn = (Button) findViewById(R.id.d6updatebtn);

        Button d7vbtn = (Button) findViewById(R.id.d7viewbtn);
        Button d7ubtn = (Button) findViewById(R.id.d7updatebtn);

        Button d8vbtn = (Button) findViewById(R.id.d8viewbtn);
        Button d8ubtn = (Button) findViewById(R.id.d8updatebtn);

        Button d9vbtn = (Button) findViewById(R.id.d9viewbtn);
        Button d9ubtn = (Button) findViewById(R.id.d9updatebtn);

        Button d10vbtn = (Button) findViewById(R.id.d10viewbtn);
        Button d10ubtn = (Button) findViewById(R.id.d10updatebtn);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add();
            }
        });

        d1vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d1ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d2vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d2ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d3vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d3ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d4vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d4ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d5vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d5ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d6vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d6ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d7vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d7ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d8vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d8ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d9vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d9ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });

        d10vbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view();
            }
        });
        d10ubtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update();
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

    private void add()
    {
        //SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this,signup_doctor.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void view()
    {
        //SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(DoctorsHospitalActivity.this,ProfileDoctorActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void update()
    {
        //SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(DoctorsHospitalActivity.this,UpdateDoctorActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /*public void OnClickListener(View v)
    {
        switch(v.getId())
        {
            case R.id.addbtn:
                add();
                break;
            case R.id.d1viewbtn:
                view();
                break;
            case R.id.d1updatebtn:
                update();
                break;
            case R.id.d2viewbtn:
                view();
                break;
            case R.id.d2updatebtn:
                update();
                break;
            case R.id.d3viewbtn:
                view();
                break;
            case R.id.d3updatebtn:
                update();
                break;
            case R.id.d4viewbtn:
                view();
                break;
            case R.id.d4updatebtn:
                update();
                break;
            case R.id.d5viewbtn:
                view();
                break;
            case R.id.d5updatebtn:
                update();
                break;
            case R.id.d6viewbtn:
                view();
                break;
            case R.id.d6updatebtn:
                update();
                break;
            case R.id.d7viewbtn:
                view();
                break;
            case R.id.d7updatebtn:
                update();
                break;

        }
    }*/
}