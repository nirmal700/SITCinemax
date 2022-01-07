package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation bottom,side;

    private static int SPLASH_TIMER = 3400;

    TextView powered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        powered=findViewById(R.id.powered);

        //Splash Animation
        side= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        powered.setAnimation(bottom);
    }
}