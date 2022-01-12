package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AboutSITCinemax extends AppCompatActivity {

    ImageView source_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_sitcinemax);
        source_code = findViewById(R.id.source_code);

        source_code.setOnClickListener(v -> {


            String url = "https://github.com/nirmal700/SITCinemax";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            Toast.makeText(AboutSITCinemax.this, "SIT Cinemax Source code", Toast.LENGTH_SHORT).show();

        });
    }
}