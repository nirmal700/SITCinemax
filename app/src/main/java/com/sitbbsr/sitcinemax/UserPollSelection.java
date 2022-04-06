package com.sitbbsr.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class UserPollSelection extends AppCompatActivity {

    SeekBar seekBar1, seekBar2, seekBar3, seekBar4;
    TextView tvOption1, tvOption2, tvOption3, tvOption4;
    TextView mTvPercent1, mTvPercent2, mTvPercent3, mTvPercent4;
    boolean flag1 = true, flag2 = true, flag3 = true, flag4 = true;
    double count1, count2, count3, count4;
    String mOptionChoosed;
    Button btn_Submit;
    ImageView btn_backToSd;
    PollData pollData;
    UserPollMovie userPollMovie;
    SessionManager manager;
    ProgressDialog progressDialog;
    int flag = 0;
    private final CollectionReference mCollectionReference = FirebaseFirestore.getInstance().collection("PollData");

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_poll_selection);
        seekBar1 = findViewById(R.id.SeekBar1);
        seekBar2 = findViewById(R.id.SeekBar2);
        seekBar3 = findViewById(R.id.SeekBar3);
        seekBar4 = findViewById(R.id.SeekBar4);
        tvOption1 = findViewById(R.id.tv_Option1);
        tvOption2 = findViewById(R.id.tv_Option2);
        tvOption3 = findViewById(R.id.tv_Option3);
        tvOption4 = findViewById(R.id.tv_Option4);
        mTvPercent1 = findViewById(R.id.tv_Percent1);
        mTvPercent2 = findViewById(R.id.tv_Percent2);
        mTvPercent3 = findViewById(R.id.tv_Percent3);
        mTvPercent4 = findViewById(R.id.tv_Percent4);
        btn_Submit = findViewById(R.id.btn_Submit);
        btn_backToSd = findViewById(R.id.btn_backToSd);
        manager = new SessionManager(getApplicationContext());
        progressDialog = new ProgressDialog(UserPollSelection.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        FirebaseFirestore.getInstance().collection("UserPollData")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                flag = 0;
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    userPollMovie = documentSnapshot.toObject(UserPollMovie.class);
                    if (userPollMovie.getmSicUser().equals(manager.getSIC())) {
                        flag = 1;
                        btn_Submit.setError("Already Casted a Poll Cannot Submit Another Response");
                        Toast.makeText(UserPollSelection.this, "Already Casted a Poll Cannot Submit Another Response", Toast.LENGTH_SHORT).show();
                        btn_Submit.setEnabled(false);
                        seekBar1.setEnabled(false);
                        seekBar2.setEnabled(false);
                        seekBar3.setEnabled(false);
                        seekBar4.setEnabled(false);
                        tvOption1.setEnabled(false);
                        tvOption2.setEnabled(false);
                        tvOption3.setEnabled(false);
                        tvOption4.setEnabled(false);
                        return;
                    }
                }
            }
        });

        if (flag == 0) {
            FirebaseFirestore.getInstance().collection("PollData").document("Poll")
                    .get().addOnSuccessListener(documentSnapshot -> {
                pollData = documentSnapshot.toObject(PollData.class);
                Log.e("Poll Data", "onSuccess: " + Objects.requireNonNull(pollData).getmOption1Votes());
                Log.e("Poll Data", "onSuccess: " + Objects.requireNonNull(pollData).getmOption2Votes());
                Log.e("Poll Data", "onSuccess: " + Objects.requireNonNull(pollData).getmOption3Votes());
                Log.e("Poll Data", "onSuccess: " + Objects.requireNonNull(pollData).getmOption4Votes());
                count1 = pollData.getmOption1Votes();
                count2 = pollData.getmOption2Votes();
                count3 = pollData.getmOption3Votes();
                count4 = pollData.getmOption4Votes();
                tvOption1.setText(pollData.mOption1);
                tvOption2.setText(pollData.mOption2);
                tvOption3.setText(pollData.mOption3);
                tvOption4.setText(pollData.mOption4);
                seekBar1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                tvOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag1) {
                            count1 = count1 + 1;
                            flag1 = false;
                            flag2 = true;
                            flag3 = true;
                            flag4 = true;
                            CalculatePercent();
                        }
                    }
                });
                seekBar2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                tvOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag2) {
                            count2 = count2 + 1;
                            flag1 = true;
                            flag2 = false;
                            flag3 = true;
                            flag4 = true;
                            CalculatePercent();
                        }
                    }
                });
                seekBar3.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                tvOption3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag3) {
                            count3 = count3 + 1;
                            flag1 = true;
                            flag2 = true;
                            flag3 = false;
                            flag4 = true;
                            CalculatePercent();
                        }
                    }
                });
                seekBar4.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                tvOption4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag4) {
                            count4 = count4 + 1;
                            flag1 = true;
                            flag2 = true;
                            flag3 = true;
                            flag4 = false;
                            CalculatePercent();
                        }
                    }
                });
                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag1 == false) {
                            mOptionChoosed = pollData.mOption1;
                        } else if (flag2 == false) {
                            mOptionChoosed = pollData.mOption2;
                        } else if (flag3 == false) {
                            mOptionChoosed = pollData.mOption3;
                        } else if (flag4 == false) {
                            mOptionChoosed = pollData.mOption4;
                        }
                        if(mOptionChoosed.equals(null))
                        {
                            btn_Submit.setError("Can't be Empty");
                            return;
                        }
                        UserPollMovie userPollMovie = new UserPollMovie(mOptionChoosed, manager.getSIC(), manager.getPhone(), null);
                        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("UserPollData");
                        collectionReference.add(userPollMovie).addOnSuccessListener(documentReference -> {
                        }).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                mCollectionReference.document("Poll").update("mOption1Votes", (int) count1);
                                mCollectionReference.document("Poll").update("mOption2Votes", (int) count2);
                                mCollectionReference.document("Poll").update("mOption3Votes", (int) count3);
                                mCollectionReference.document("Poll").update("mOption4Votes", (int) count4);
                                Toast.makeText(UserPollSelection.this, "Poll Submitted Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserPollSelection.this, UserDashBoard.class);
                                progressDialog.dismiss();
                                startActivity(intent);
                                finish();
                            }
                        });

                    }
                });

            });
            btn_backToSd.setOnClickListener(v -> {
                startActivity(new Intent(UserPollSelection.this, UserDashBoard.class));
                finishAffinity();
            });
        }
    }

    private void CalculatePercent() {
        double total = count1 + count2 + count3 + count4;
        double p1 = (count1 / total) * 100;
        double p2 = (count2 / total) * 100;
        double p3 = (count3 / total) * 100;
        double p4 = (count4 / total) * 100;
        mTvPercent1.setText(String.format("%.0f%%", p1));
        seekBar1.setProgress((int) p1);
        mTvPercent2.setText(String.format("%.0f%%", p2));
        seekBar2.setProgress((int) p2);
        mTvPercent3.setText(String.format("%.0f%%", p3));
        seekBar3.setProgress((int) p3);
        mTvPercent4.setText(String.format("%.0f%%", p4));
        seekBar4.setProgress((int) p4);
        Log.e("TAG", "onCreate: " + flag1 + flag2 + flag3 + flag4 + count1 + count2 + count3 + count4);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
        super.onBackPressed();
    }
}