package com.sitbbsr.sitcinemax;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;
import java.util.Objects;

public class BookTickets extends AppCompatActivity {

    private TextInputLayout et_userName2;
    private TextInputLayout et_sic2;
    private TextInputLayout et_phoneNumber2;
    private RadioButton rb2;
    private Button btn_Proceed;
    ImageView btn_back;
    Ticket mTicket;
    String sic, name, phone, mMovieName;
    int b = -1, flag = 0;
    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tickets);
        TextInputLayout et_phoneNumber = findViewById(R.id.et_phoneNumber);
        TextInputLayout et_userName = findViewById(R.id.et_userName);
        TextInputLayout et_sic = findViewById(R.id.et_sic);
        et_phoneNumber2 = findViewById(R.id.et_phoneNumber2);
        et_userName2 = findViewById(R.id.et_userName2);
        et_sic2 = findViewById(R.id.et_sic2);
        rb2 = findViewById(R.id.duo);
        TextInputLayout et_MovieName = findViewById(R.id.et_MovieName);
        btn_back = findViewById(R.id.btn_backToSd);
        btn_Proceed = findViewById(R.id.btn_Proceed);

        manager = new SessionManager(getApplicationContext());
        Objects.requireNonNull(et_phoneNumber.getEditText()).setText(manager.getPhone());
        Objects.requireNonNull(et_userName.getEditText()).setText(manager.getName());
        Objects.requireNonNull(et_sic.getEditText()).setText(manager.getSIC());
        RadioGroup radio_group = findViewById(R.id.radio_group);
        et_phoneNumber2.setVisibility(View.GONE);
        et_userName2.setVisibility(View.GONE);
        et_sic2.setVisibility(View.GONE);
        mMovieName = getIntent().getStringExtra("MovieName");
        Objects.requireNonNull(et_MovieName.getEditText()).setText(mMovieName);

        radio_group.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == rb2.getId()) {
                et_phoneNumber2.setVisibility(View.VISIBLE);
                et_userName2.setVisibility(View.VISIBLE);
                et_sic2.setVisibility(View.VISIBLE);
                b = 2;
            } else {
                et_phoneNumber2.setVisibility(View.GONE);
                et_userName2.setVisibility(View.GONE);
                et_sic2.setVisibility(View.GONE);
                b = 1;
            }
            Log.e("No Of Persons", "onCheckedChanged: " + b);
        });
        btn_Proceed.setOnClickListener(view -> {
            sic = Objects.requireNonNull(et_sic2.getEditText()).getText().toString();
            name = Objects.requireNonNull(et_userName2.getEditText()).getText().toString();
            phone = Objects.requireNonNull(et_phoneNumber2.getEditText()).getText().toString();
            Log.e("Valid", "onClick: " + b + sic + name + phone);
            if (b == 2) {
                if (sic.length() > 4) {
                    et_sic2.setError(null);
                } else {
                    et_sic2.setError("Enter Valid SIC");
                    return;
                }
                if (name.length() > 4) {
                    et_userName2.setError(null);
                } else {
                    et_userName2.setError("Enter Valid Name");
                    return;
                }
                if (phone.length() == 10) {
                    et_phoneNumber2.setError(null);
                } else {
                    et_phoneNumber2.setError("Enter Valid PhoneNumber");
                    return;
                }
            } else if (b == -1) {
                btn_Proceed.setError("Choose No Of Persons");
                return;
            }
            sic = sic.toUpperCase(Locale.ROOT);
            FirebaseFirestore.getInstance().collection("Tickets")
                    .whereEqualTo("movieName", mMovieName)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            flag = 0;
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                //Log.e("Firestore", "onSuccess: " + queryDocumentSnapshots.getDocuments().toString());
                                mTicket = documentSnapshot.toObject(Ticket.class);
                                if (Objects.requireNonNull(mTicket).getSICUser().equals(manager.getSIC()) || mTicket.getSIC2().equals(manager.getSIC()))
                                {
                                    Log.e("FireStore Data", "onSuccess: " + mTicket.getmDocId());
                                    btn_Proceed.setError("Already Booked a Ticket Cannot Book Another Ticket");
                                    Toast.makeText(BookTickets.this, "Already Booked a Ticket Cannot Book Another Ticket", Toast.LENGTH_SHORT).show();
                                    flag = 1;
                                    return;
                                }
                                else if(b==2) {
                                    if (mTicket.getSIC2().equals(sic) || mTicket.getSICUser().equals(sic)) {
                                        Log.e("2 nd FireStore Data", "onSuccess: " + mTicket.getmDocId());
                                        btn_Proceed.setError("Already Booked a Ticket Cannot Book Another Ticket");
                                        Toast.makeText(BookTickets.this, "Already Booked a Ticket Cannot Book Another Ticket", Toast.LENGTH_SHORT).show();
                                        flag = 1;
                                        return;
                                    }
                                }
                                Log.e("FLAG", "onSuccess: " + flag);
                            }
                            if (flag == 0) {
                                Intent intent = new Intent(BookTickets.this, ChooseSeatLayout.class);
                                intent.putExtra("NAME_2", name);
                                intent.putExtra("SIC_2", sic);
                                intent.putExtra("PHONE_NUMBER_2", phone);
                                intent.putExtra("Movie_Name", mMovieName);
                                intent.putExtra("NoOfPersons", "" + b);
                                Log.e("Intent Put", "onClick: " + name + phone + sic);
                                startActivity(intent);
                            }

                        }
                    });


        });
        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(BookTickets.this, UserDashBoard.class));
            finishAffinity();
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), UserBookTickets.class));
        super.onBackPressed();
    }
}