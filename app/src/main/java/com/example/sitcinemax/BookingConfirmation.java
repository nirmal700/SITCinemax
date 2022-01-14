package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class BookingConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        Dialog dialog = new Dialog(BookingConfirmation.this);
        dialog.setContentView(R.layout.booking_successfull);
        dialog.setCancelable(false);
        Button btOk = dialog.findViewById(R.id.bt_ok);

        btOk.setOnClickListener(v -> {

            startActivity(new Intent(BookingConfirmation.this, UserDashBoard.class));
            dialog.dismiss();
        });

        dialog.show();
    }
}