package com.example.sitcinemax;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SingleTicket_QR extends AppCompatActivity {

    String AES = "AES";
    String keyPass = "SITCinemax@Silicon";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ticket_qr);
        ImageView iv_qr;
        TextView tv_MovieName, tv_Details, tv_bookedBy, tv_screenDate, SeatNo, docId;
        Intent i = getIntent();
        Ticket mBticket = (Ticket) i.getSerializableExtra("Booked_Tickets");
        iv_qr = findViewById(R.id.iv_qr);
        tv_MovieName = findViewById(R.id.tv_MovieName);
        tv_Details = findViewById(R.id.tv_Details);
        tv_bookedBy = findViewById(R.id.tv_bookedBy);
        tv_screenDate = findViewById(R.id.tv_screenDate);
        docId = findViewById(R.id.docId);
        SeatNo = findViewById(R.id.SeatNo);
        SeatNo.setText(mBticket.getSeats());
        tv_bookedBy.setText(String.format("%s(%s)", mBticket.getNameUser(), mBticket.getSICUser()));
        tv_Details.setText(mBticket.getmDetails());
        tv_MovieName.setText(mBticket.getMovieName());
        tv_screenDate.setText(mBticket.getmScreenDate());
        docId.setText(mBticket.getmDocId());
        //--------------- Initialize ProgressDialog -----------
        progressDialog = new ProgressDialog(SingleTicket_QR.this);
        progressDialog.show();
        //progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        String appName = "SITCinemax";
        String SICUser = mBticket.getSICUser();
        String NameUser = mBticket.getNameUser();
        String SIC2 = mBticket.getSIC2();
        String Name2 = mBticket.getName2();
        String MovieName = mBticket.getMovieName();
        String Details = mBticket.getmDetails();
        String Poster = mBticket.getmPoster();
        String ScreenDate = mBticket.getmScreenDate();
        String Seats = mBticket.getSeats();
        String BookedTime = mBticket.getmBookedTime().toString();
        String Docid = mBticket.mDocId;

        if (!isConnected(SingleTicket_QR.this))
            showCustomDialog();
        //--------------- Encoding Data -----------
        try {
            // assert phoneNumber != null;
            String mEncode = SICUser + ":" + NameUser + ":" + SIC2 + ":" + Name2 + ":" + MovieName + ":" + Details + ":" + Poster + ":" + ScreenDate + ":" + Seats + ":" + BookedTime + ":" + Docid;
            String encodedData = encrypt(mEncode);
            Log.e("TAG", "onCreate: " + mEncode);
            MultiFormatWriter writer = new MultiFormatWriter();

            //--------------- Create QR code -----------
            try {
                BitMatrix matrix = writer.encode(appName + ":" + "BookedTickets" + ":" + encodedData, BarcodeFormat.QR_CODE, 350, 350);

                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                iv_qr.setImageBitmap(bitmap);

                progressDialog.dismiss();


            } catch (WriterException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //--------------- Internet Error Dialog Box -----------
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SingleTicket_QR.this);
        builder.setMessage("Please connect to the internet")
                //   .setCancelable(false)
                .setPositiveButton("Connect", (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
                    finish();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    //--------------- Check Internet Is Connected -----------
    private boolean isConnected(SingleTicket_QR singleTicket_qr) {

        ConnectivityManager connectivityManager = (ConnectivityManager) singleTicket_qr.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo bluetoothConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected() || (bluetoothConn != null && bluetoothConn.isConnected())); // if true ,  else false

    }

    //--------------- Encode Data -----------
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String encrypt(String forEncode) throws Exception {
        SecretKeySpec key = generateKey(keyPass);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(forEncode.getBytes());
        return Base64.encodeToString(encVal, Base64.DEFAULT);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private SecretKeySpec generateKey(String keyPass) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = keyPass.getBytes(StandardCharsets.UTF_8); //"UTF-8"
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        return new SecretKeySpec(key, "AES"); //SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");

    }
}