package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class BookTickets extends AppCompatActivity {

    private TextInputLayout et_userName,et_sic,et_phoneNumber,et_userName2,et_sic2,et_phoneNumber2,et_MovieName;
    private SessionManager manager;
    private RadioGroup radio_group;
    private RadioButton rb_selected,rb1,rb2;
    private Button btn_Proceed;
    String sic,name,phone,mMovieName;
    int b=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tickets);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        et_userName = findViewById(R.id.et_userName);
        et_sic = findViewById(R.id.et_sic);
        et_phoneNumber2 = findViewById(R.id.et_phoneNumber2);
        et_userName2 = findViewById(R.id.et_userName2);
        et_sic2 = findViewById(R.id.et_sic2);
        rb1 = findViewById(R.id.single);
        rb2 = findViewById(R.id.duo);
        et_MovieName = findViewById(R.id.et_MovieName);
        btn_Proceed = findViewById(R.id.btn_Proceed);

        manager = new SessionManager(getApplicationContext());
        et_phoneNumber.getEditText().setText(manager.getPhone());
        et_userName.getEditText().setText(manager.getName());
        et_sic.getEditText().setText(manager.getSIC());
        radio_group = findViewById(R.id.radio_group);
        et_phoneNumber2.setVisibility(View.GONE);
        et_userName2.setVisibility(View.GONE);
        et_sic2.setVisibility(View.GONE);
        mMovieName = getIntent().getStringExtra("MovieName");
        et_MovieName.getEditText().setText(mMovieName);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == rb2.getId()) {
                    et_phoneNumber2.setVisibility(View.VISIBLE);
                    et_userName2.setVisibility(View.VISIBLE);
                    et_sic2.setVisibility(View.VISIBLE);
                    b=2;
                    Log.e("No Of Persons", "onCheckedChanged: "+b );
                }
                else
                {
                    et_phoneNumber2.setVisibility(View.GONE);
                    et_userName2.setVisibility(View.GONE);
                    et_sic2.setVisibility(View.GONE);
                    b=1;
                    Log.e("No Of Persons", "onCheckedChanged: "+b );
                }
            }
        });
        btn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sic = Objects.requireNonNull(et_sic2.getEditText()).getText().toString();
                name = Objects.requireNonNull(et_userName2.getEditText()).getText().toString();
                phone = Objects.requireNonNull(et_phoneNumber2.getEditText()).getText().toString();
                Log.e("Valid", "onClick: "+b+sic+name+phone );
                if(b==2)
                {
                    if(sic.length()>4) {
                        et_sic2.setError(null);
                    }
                    else
                    {
                        et_sic2.setError("Enter Valid SIC");
                        return;
                    }
                    if(name.length()>4) {
                        et_userName2.setError(null);
                    }
                    else
                    {
                        et_userName2.setError("Enter Valid Name");
                        return;
                    }
                    if(phone.length()==10) {
                        et_phoneNumber2.setError(null);
                    }
                    else
                    {
                        et_phoneNumber2.setError("Enter Valid PhoneNumber");
                        return;
                    }
                }
                else if(b==-1)
                {
                    btn_Proceed.setError("Choose No Of Persons");
                    return;
                }
                Intent intent = new Intent(BookTickets.this, ChooseSeatLayout.class);
                intent.putExtra("NAME_2",name);
                intent.putExtra("SIC_2",sic);
                intent.putExtra("PHONE_NUMBER_2",phone);
                intent.putExtra("Movie_Name",mMovieName);
                intent.putExtra("NoOfPersons", ""+b);
                Log.e("Intent Put", "onClick: "+name+phone+sic );
                startActivity(intent);
            }
        });

    }
}