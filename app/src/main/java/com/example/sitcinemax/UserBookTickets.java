package com.example.sitcinemax;

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

import java.util.ArrayList;
import java.util.Objects;

public class UserBookTickets extends AppCompatActivity implements UserMoviesAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    ImageView btn_back;
    private ArrayList<Movies> list;
    private UserMoviesAdapter userMoviesAdapter;

    private final CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Movies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_book_tickets);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            mAuth.signInAnonymously().addOnSuccessListener(this, authResult -> Log.e("FireBase Anonymous  ", "onSuccess: Anonymous Sign in Success"))
                    .addOnFailureListener(this, exception -> Log.e("FireBase Anonymous ", "signInAnonymously:FAILURE", exception));
        }
        //Initialize ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog(UserBookTickets.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        btn_back = findViewById(R.id.btn_backToCd);

        recyclerView = findViewById(R.id.MoviesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        userMoviesAdapter = new UserMoviesAdapter(UserBookTickets.this, list);

        recyclerView.setAdapter(userMoviesAdapter);
        userMoviesAdapter.setOnItemClickListener(UserBookTickets.this);
        progressDialog.dismiss();

        btn_back.setOnClickListener(v -> onBackPressed());
        LoadRecycler();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadRecycler() {
        Query mMovie = collectionReference.whereEqualTo("IsScreening", true);
        mMovie.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("AddSnapShot", error.getMessage());
                return;
            }
            for (DocumentChange documentChange : Objects.requireNonNull(value).getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    list.add(documentChange.getDocument().toObject(Movies.class));
                    userMoviesAdapter = new UserMoviesAdapter(UserBookTickets.this, list);
                    recyclerView.setAdapter(userMoviesAdapter);
                    userMoviesAdapter.notifyDataSetChanged();
                    userMoviesAdapter.setOnItemClickListener(UserBookTickets.this);
                }
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Movies movies = list.get(position);
        String mMovieName = movies.getMovieName();
        Intent intent = new Intent(UserBookTickets.this, BookTickets.class);
        intent.putExtra("MovieName", mMovieName); // Pass Shop Id value To ShopDetailsSingleView
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
        finish();
    }
}