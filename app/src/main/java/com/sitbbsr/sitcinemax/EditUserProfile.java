package com.sitbbsr.sitcinemax;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class EditUserProfile extends AppCompatActivity {

    ImageView btn_back;
    Button btn_update;
    private TextInputLayout et_name, et_sic;
    private AutoCompleteTextView et_course;
    private AutoCompleteTextView autoCompleteYear;
    private TextView tv_name;
    private ProgressDialog progressDialog;

    private String phoneNumber;
    private DatabaseReference userDb;
    private SessionManager manager;
    final String[] Course = {""};
    final String[] Year = {""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        btn_back = findViewById(R.id.btn_backToCd);
        et_name = findViewById(R.id.et_name);
        et_sic = findViewById(R.id.et_sic);
        autoCompleteYear = findViewById(R.id.autoCompleteYear);

        btn_update = findViewById(R.id.btn_update);
        et_course = findViewById(R.id.autoCompleteCourse);
        tv_name = findViewById(R.id.tv_UserName);

        ArrayList<String> arrayListCourse;
        ArrayAdapter<String> arrayAdapterCourse;
        arrayListCourse = new ArrayList<>();
        arrayListCourse.add("B.Tech");
        arrayListCourse.add("M.Tech");
        arrayListCourse.add("MCA");
        arrayAdapterCourse = new ArrayAdapter<>(getApplicationContext(), R.layout.text_menu, arrayListCourse);
        et_course.setAdapter(arrayAdapterCourse);
        et_course.setOnItemClickListener((adapterView, view, i, l) -> Course[0] = arrayAdapterCourse.getItem(i));

        ArrayList<String> arrayListYear;
        ArrayAdapter<String> arrayAdapterYear;
        arrayListYear = new ArrayList<>();
        arrayListYear.add("1st Year");
        arrayListYear.add("2nd Year");
        arrayListYear.add("3rd Year");
        arrayListYear.add("4th Year");
        arrayAdapterYear = new ArrayAdapter<>(getApplicationContext(), R.layout.text_menu, arrayListYear);
        autoCompleteYear.setAdapter(arrayAdapterYear);
        autoCompleteYear.setOnItemClickListener((adapterView, view, i, l) -> Year[0] = arrayAdapterYear.getItem(i));

        manager = new SessionManager(getApplicationContext());
        phoneNumber = manager.getPhone();
        if (!isConnected(EditUserProfile.this)) {
            showCustomDialog();
        }

        callProgressDialog();

        loadData();

        btn_back.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
            finishAffinity();
        });

        btn_update.setOnClickListener(v -> updateData());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
        super.onBackPressed();
    }

    private void callProgressDialog() {
        //--------------- Initialize ProgressDialog -----------
        progressDialog = new ProgressDialog(EditUserProfile.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    //-------------------------------Load data to EditTexts------------------------------------------
    private void loadData() {

        userDb = FirebaseDatabase.getInstance().getReference("Users").child(phoneNumber).child("Profile");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String _name = snapshot.child("name").getValue(String.class);
                Objects.requireNonNull(et_name.getEditText()).setText(_name);
                tv_name.setText(_name);
                String _sic = snapshot.child("sic").getValue(String.class);
                Objects.requireNonNull(et_sic.getEditText()).setText(_sic);
                String _year = snapshot.child("year").getValue(String.class);
                if (Objects.equals(_year, "1st Year"))
                    autoCompleteYear.setText(autoCompleteYear.getAdapter().getItem(0).toString(), false);
                else if (_year.equals("2nd Year"))
                    autoCompleteYear.setText(autoCompleteYear.getAdapter().getItem(1).toString(), false);
                else if (_year.equals("3rd Year"))
                    autoCompleteYear.setText(autoCompleteYear.getAdapter().getItem(2).toString(), false);
                else if (_year.equals("4th Year"))
                    autoCompleteYear.setText(autoCompleteYear.getAdapter().getItem(3).toString(), false);

                String _course = snapshot.child("course").getValue(String.class);
                if (Objects.equals(_course, "B.Tech"))
                    et_course.setText(et_course.getAdapter().getItem(0).toString(), false);
                else if (_course.equals("M.Tech"))
                    et_course.setText(et_course.getAdapter().getItem(1).toString(), false);
                else if (_course.equals("MCA"))
                    et_course.setText(et_course.getAdapter().getItem(2).toString(), false);


                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void updateData() {

        //EditText Validations
        if (!validateName() | !validateSic() | !validateYear() | !validateCourse()) {

            return;
        }

        callProgressDialog();

        userDb = FirebaseDatabase.getInstance().getReference("Users").child(phoneNumber).child("Profile");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String _name = snapshot.child("name").getValue(String.class);
                String _sic = snapshot.child("sic").getValue(String.class);
                String _course = snapshot.child("course").getValue(String.class);
                String _year = snapshot.child("year").getValue(String.class);


                if (Objects.requireNonNull(_name).equals(Objects.requireNonNull(et_name.getEditText()).getText().toString()) &&
                        Objects.requireNonNull(_sic).equals(Objects.requireNonNull(et_sic.getEditText()).getText().toString()) &&
                        Objects.requireNonNull(_course).equals(et_course.getText().toString()) &&
                        Objects.requireNonNull(_year).equals(autoCompleteYear.getText().toString())) {

                    Toast.makeText(EditUserProfile.this, "Same data no changes", Toast.LENGTH_SHORT).show();

                } else {

                    manager = new SessionManager(getApplicationContext());
                    phoneNumber = manager.getPhone();
                    String _Password = manager.getPassword();
                    manager.setDetails(phoneNumber, et_name.getEditText().getText().toString(), _Password, Objects.requireNonNull(et_sic.getEditText()).getText().toString());

                    userDb.child("name").setValue(et_name.getEditText().getText().toString());
                    tv_name.setText(et_name.getEditText().getText().toString());
                    userDb.child("sic").setValue(et_sic.getEditText().getText().toString());
                    userDb.child("course").setValue(et_course.getText().toString());
                    userDb.child("year").setValue(autoCompleteYear.getText().toString());
                    Logcat logcat = new Logcat(et_name.getEditText().getText().toString(), _name, et_sic.getEditText().getText().toString(), _sic, _year, et_course.getText().toString(), _course, autoCompleteYear.getText().toString(), manager.getPhone(), null);
                    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Logcat");
                    collectionReference.add(logcat).addOnSuccessListener(documentReference -> {
                    }).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditUserProfile.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressDialog.dismiss();

    }

    //--------------- Internet Error Dialog Box -----------
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(EditUserProfile.this);
        builder.setMessage("Please connect to the internet")
                //.setCancelable(false)
                .setPositiveButton("Connect", (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
                    finish();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    //--------------- Check Internet Is Connected -----------
    private boolean isConnected(EditUserProfile editUserProfile) {

        ConnectivityManager connectivityManager = (ConnectivityManager) editUserProfile.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo bluetoothConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected() || (bluetoothConn != null && bluetoothConn.isConnected())); // if true ,  else false

    }

    private boolean validateName() {
        String val = Objects.requireNonNull(et_name.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            et_name.setError("Field can not be empty");
            return false;
        } else if (val.length() > 25) {
            et_name.setError("Name is Too Large");
            return false;
        } else {
            et_name.setError(null);
            return true;
        }
    }

    private boolean validateSic() {
        String val = Objects.requireNonNull(et_sic.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            et_sic.setError("Field can not be empty");
            return false;
        } else {
            et_sic.setError(null);
            return true;
        }

    }

    private boolean validateCourse() {
        String val = et_course.getText().toString().trim();

        if (val.isEmpty()) {
            et_course.setError("Field can not be empty");
            return false;
        } else {
            et_course.setError(null);
            return true;
        }

    }

    private boolean validateYear() {

        String val = autoCompleteYear.getText().toString().trim();

        if (val.isEmpty()) {
            autoCompleteYear.setError("Field can not be empty");
            return false;
        } else {
            autoCompleteYear.setError(null);
            return true;
        }
    }

}