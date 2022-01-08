package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

public class BookTickets extends AppCompatActivity {

    private TextInputLayout et_userName,et_sic,et_phoneNumber,et_userName2,et_sic2,et_phoneNumber2;
    private SessionManager manager;
    private RadioGroup radio_group;
    private RadioButton rb_selected,rb1,rb2;
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


        manager = new SessionManager(getApplicationContext());
        et_phoneNumber.getEditText().setText(manager.getPhone());
        et_userName.getEditText().setText(manager.getName());
        et_sic.getEditText().setText(manager.getSIC());
        radio_group = findViewById(R.id.radio_group);
        et_phoneNumber2.setVisibility(View.GONE);
        et_userName2.setVisibility(View.GONE);
        et_sic2.setVisibility(View.GONE);



        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == rb2.getId()) {
                    et_phoneNumber2.setVisibility(View.VISIBLE);
                    et_userName2.setVisibility(View.VISIBLE);
                    et_sic2.setVisibility(View.VISIBLE);
                }
                else
                {
                    et_phoneNumber2.setVisibility(View.GONE);
                    et_userName2.setVisibility(View.GONE);
                    et_sic2.setVisibility(View.GONE);
                }
            }
        });


    }
}