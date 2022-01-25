package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    Animation bottom, side;

    private static final int SPLASH_TIMER = 3400;

    TextView powered;

    SessionManager manager;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        powered = findViewById(R.id.powered);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //Splash Animation
        side = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        powered.setAnimation(bottom);

        new Handler().postDelayed(() -> {

            manager = new SessionManager(getApplicationContext());
            if (manager.getUserLogin()) {
                startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
            } else {
                startActivity(new Intent(getApplicationContext(), UserSignUp.class));
            }
            finish();


        }, SPLASH_TIMER);

    }
}