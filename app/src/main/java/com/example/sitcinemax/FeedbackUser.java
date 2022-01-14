package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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


        rateStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                answervalue = String.valueOf((int) (rateStars.getRating()));

                if (answervalue.equals("1")){
                    emoji.setImageResource(R.drawable.one);
                    resultRate.setText("Bad");
                }
                else if (answervalue.equals("2")){
                    emoji.setImageResource(R.drawable.two);

                    resultRate.setText("Not Bad");
                }
                else if (answervalue.equals("3")){
                    emoji.setImageResource(R.drawable.three);

                    resultRate.setText("Nice!");
                }
                else if (answervalue.equals("4")){
                    emoji.setImageResource(R.drawable.four);

                    resultRate.setText("Good Job!");
                }
                else if (answervalue.equals("5")){
                    emoji.setImageResource(R.drawable.five);

                    resultRate.setText("Awesome!");
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Point", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Feedback feedback = new Feedback(""+rateStars.getRating(),feedback_et.getText().toString(),manager.getSIC(), manager.getPhone(),manager.getName(), resultRate.getText().toString(),false,false);
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Feedback");
                collectionReference.add(feedback).addOnSuccessListener(documentReference -> {
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(FeedbackUser.this, "Feedback Submitted Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FeedbackUser.this, UserDashBoard.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}