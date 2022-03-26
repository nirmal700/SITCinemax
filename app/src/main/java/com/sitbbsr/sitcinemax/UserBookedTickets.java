package com.sitbbsr.sitcinemax;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


public class UserBookedTickets extends AppCompatActivity implements BookedTicketsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;

    ImageView btn_backToSd;
    SessionManager manager;
    BookedTicketsAdapter bookedTicketsAdapter;
    private final CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tickets");
    private ArrayList<Ticket> list;

    @SuppressLint({"NotifyDataSetChanged", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booked_tickets);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            mAuth.signInAnonymously().addOnSuccessListener(this, authResult -> Log.e("FireBase Anonymous Login ", "onSuccess: Anonymous Sign in Success"))
                    .addOnFailureListener(this, exception -> Log.e("FireBase Anonymous Login", "signInAnonymously:FAILURE", exception));
        }

        btn_backToSd = findViewById(R.id.btn_backToSd);

        //Initialize ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog(UserBookedTickets.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        manager = new SessionManager(getApplicationContext());


        recyclerView = findViewById(R.id.rv_BookedTicket);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();

        bookedTicketsAdapter = new BookedTicketsAdapter(UserBookedTickets.this, list);

        recyclerView.setAdapter(bookedTicketsAdapter);
        bookedTicketsAdapter.setOnItemClickListener(UserBookedTickets.this);
        progressDialog.dismiss();

        btn_backToSd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
            finish();
        });
        loadRecycler();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadRecycler() {
        Query getTickets = collectionReference.orderBy("mBookedTime", Query.Direction.DESCENDING);
        getTickets.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("AddSnapShot", error.getMessage());
                return;
            }
            for (DocumentChange documentChange : Objects.requireNonNull(value).getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    if (documentChange.getDocument().toObject(Ticket.class).getSICUser().equals(manager.getSIC()) | documentChange.getDocument().toObject(Ticket.class).getSIC2().equals(manager.getSIC())) {
                        list.add(documentChange.getDocument().toObject(Ticket.class));
                        bookedTicketsAdapter = new BookedTicketsAdapter(UserBookedTickets.this, list);
                        recyclerView.setAdapter(bookedTicketsAdapter);
                        bookedTicketsAdapter.notifyDataSetChanged();
                        bookedTicketsAdapter.setOnItemClickListener(UserBookedTickets.this);
                    }
                }
            }
        });

    }


    @Override
    public void onItemClick(int position) {
        Ticket ticket = list.get(position);
        Intent intent = new Intent(UserBookedTickets.this, SingleTicket_QR.class);
        intent.putExtra("Booked_Tickets", (Serializable) ticket); // Pass Shop Id value To ShopDetailsSingleView
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
        finish();
    }

}