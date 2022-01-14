package com.example.sitcinemax;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URLEncoder;
import java.util.ArrayList;

public class ContactUs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private ArrayList<TeamData> list;
    private TeamAdapter teamAdapter;

    ImageView to_gmail, to_whatsapp, to_instagram, to_gitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        to_gitHub = findViewById(R.id.to_gitHub);
        to_whatsapp = findViewById(R.id.to_whatsapp);
        to_gmail = findViewById(R.id.to_gMail);
        to_instagram = findViewById(R.id.to_instagram);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            mAuth.signInAnonymously().addOnSuccessListener(this, authResult -> Log.e("FireBase Anonymous ", "onSuccess: Anonymous Sign in Success"))
                    .addOnFailureListener(this, exception -> Log.e("FireBase Anonymous ", "signInAnonymously:FAILURE", exception));
        }

        //Initialize ProgressDialog
        progressDialog = new ProgressDialog(ContactUs.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        recyclerView = findViewById(R.id.rv_team);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        to_whatsapp.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);

            try {
                String url = "https://api.whatsapp.com/send?phone=" + "+917008000094" + "&text=" + URLEncoder.encode("Hai,\n", "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(ContactUs.this, "Contact SIT Cinemax via Whatsapp", Toast.LENGTH_SHORT).show();
        });

        to_instagram.setOnClickListener(v -> {

            Uri uri = Uri.parse("https://www.instagram.com/k.nirmalkumar2002/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/k.nirmalkumar2002/")));
            }
            Toast.makeText(ContactUs.this, "Contact SIT Cinemax via Instagram", Toast.LENGTH_SHORT).show();

        });

        to_gmail.setOnClickListener(v -> {

            Intent emailIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + "SIT Cinemax" + "&body=" + "Hai,\n" + "&to=" + "k.nirmalkumar2002@gmail.com");
            emailIntent.setData(data);
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Toast.makeText(ContactUs.this, "Contact SIT Cinemax via E-mail", Toast.LENGTH_SHORT).show();

        });

        to_gitHub.setOnClickListener(v -> {

            String url = "https://github.com/nirmal700";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            Toast.makeText(ContactUs.this, "Contact Nirmal via GitHub", Toast.LENGTH_SHORT).show();

        });

        list = new ArrayList<>();


        load();

    }

    private void load() {

        FirebaseDatabase.getInstance().getReference("TeamMembers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            TeamData teamData = dataSnapshot.getValue(TeamData.class);
                            list.add(teamData);
                            teamAdapter = new TeamAdapter(ContactUs.this, list);
                            recyclerView.setAdapter(teamAdapter);

                        }

                        progressDialog.dismiss();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ContactUs.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}