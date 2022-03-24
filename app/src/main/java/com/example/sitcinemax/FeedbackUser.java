package com.example.sitcinemax;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedbackUser extends AppCompatActivity {

    ImageView emoji;
    TextView titleRate, resultRate;
    Button btn_Submit;
    RatingBar rateStars;
    String answervalue;
    EditText feedback_et;
    SessionManager manager;
    ProgressDialog progressDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_user);
        titleRate = findViewById(R.id.rating_title);
        resultRate = findViewById(R.id.rating_result);
        emoji = findViewById(R.id.emoji);
        btn_Submit = findViewById(R.id.btn_Submit);
        feedback_et = findViewById(R.id.feedback_et);
        rateStars = findViewById(R.id.rateStars);
        manager = new SessionManager(FeedbackUser.this);

        progressDialog = new ProgressDialog(FeedbackUser.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();


        rateStars.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {

            answervalue = String.valueOf((int) (rateStars.getRating()));

            switch (answervalue) {
                case "1":
                    emoji.setImageResource(R.drawable.one);
                    resultRate.setText("Bad");
                    break;
                case "2":
                    emoji.setImageResource(R.drawable.two);

                    resultRate.setText("Not Bad");
                    break;
                case "3":
                    emoji.setImageResource(R.drawable.three);

                    resultRate.setText("Nice!");
                    break;
                case "4":
                    emoji.setImageResource(R.drawable.four);

                    resultRate.setText("Good Job!");
                    break;
                case "5":
                    emoji.setImageResource(R.drawable.five);

                    resultRate.setText("Awesome!");
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "No Point", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        btn_Submit.setOnClickListener(view -> {
            progressDialog.show();
            if (feedback_et.getText().toString().length() <= 5) {
                btn_Submit.setError("Invalid Feedback");
                progressDialog.dismiss();
                return;
            }
            Feedback feedback = new Feedback("" + rateStars.getRating(), feedback_et.getText().toString(), manager.getSIC(), manager.getPhone(), manager.getName(), resultRate.getText().toString(), false, false, null);
            CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Feedback");
            collectionReference.add(feedback).addOnSuccessListener(documentReference -> {
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(FeedbackUser.this, "Feedback Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FeedbackUser.this, UserDashBoard.class);
                    progressDialog.dismiss();
                    startActivity(intent);
                    finish();
                }
            });
        });
    }
}