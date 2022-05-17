package com.sitbbsr.sitcinemax;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.datatransport.cct.internal.LogEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ChooseSeatLayout extends AppCompatActivity implements View.OnClickListener {
    ViewGroup layout;

    String Name2, SIC2, PhoneNumber2, MovieName;
    Movies mMovie;
    String seats, seatno1, seatno2;
    int flag = 0;
    char Par = '_';
    SessionManager manager;
    Boolean mErrorCaused = false;

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    int max;
    String selectedIds = "";
    String NoOfPerson;
    String SeatNo;
    String mSubSeats;

    Button btn_Proceed;
    private final CollectionReference mCollectionReference = FirebaseFirestore.getInstance().collection("Movies");
    LinearLayout layoutSeat;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat_layout);

        ProgressDialog progressDialog = new ProgressDialog(ChooseSeatLayout.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        layout = findViewById(R.id.layoutSeat);
        btn_Proceed = findViewById(R.id.btn_Proceed);
        manager = new SessionManager(getApplicationContext());
        String uName = manager.getName();
        String uPhone = manager.getPhone();
        String uSIC = manager.getSIC();

        Name2 = getIntent().getStringExtra("NAME_2");
        SIC2 = getIntent().getStringExtra("SIC_2");
        PhoneNumber2 = getIntent().getStringExtra("PHONE_NUMBER_2");
        MovieName = getIntent().getStringExtra("Movie_Name");
        NoOfPerson = getIntent().getStringExtra("NoOfPersons");

        if (NoOfPerson.equals("1")) {
            Name2 = "N/A";
            SIC2 = "N/A";
            PhoneNumber2 = "N/A";
            max = 2;
        } else
            max = 6;
        FirebaseFirestore.getInstance()
                .collection("Movies")
                .document(MovieName)
                .addSnapshotListener((DocumentSnapshot value, FirebaseFirestoreException error) -> {
                    if (error != null) {
                        Log.e("Firebase Error", "onEvent: ", error);
                        progressDialog.dismiss();
                        return;
                    }
                    if (value != null) {
                        if (mMovie == null) {
                            Log.e("mMovie called", "onEvent: ");
                            layoutSeat = new LinearLayout(ChooseSeatLayout.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutSeat.setOrientation(LinearLayout.VERTICAL);
                            layoutSeat.setLayoutParams(params);
                            layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
                            layout.addView(layoutSeat);
                        } else {
                            layout.removeAllViews();
                            layoutSeat.removeAllViews();
                            layoutSeat = new LinearLayout(ChooseSeatLayout.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutSeat.setOrientation(LinearLayout.VERTICAL);
                            layoutSeat.setLayoutParams(params);
                            layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
                            layout.addView(layoutSeat);
                        }
                        mMovie = value.toObject(Movies.class);
                        progressDialog.dismiss();

                    }
                    LinearLayout layout = null;
                    seats = Objects.requireNonNull(mMovie).getSeatLayout();
                    if (seats.startsWith("////")) {
                        for (int index = 0; index < seats.length(); index = index + 4) {
                            mSubSeats = seats.substring(index, index + 4);
                            if (mSubSeats.equals("////")) {
                                layout = new LinearLayout(ChooseSeatLayout.this);
                                layout.setOrientation(LinearLayout.HORIZONTAL);
                                layoutSeat.addView(layout);
                            } else if (mSubSeats.equals("UUUU")) {
                                TextView view = new TextView(ChooseSeatLayout.this);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                                view.setLayoutParams(layoutParams);
                                view.setPadding(0, 0, 0, 2 * seatGaping);
                                view.setId(((int) Calendar.getInstance().getTimeInMillis()));
                                view.setGravity(Gravity.CENTER);
                                view.setBackgroundResource(R.drawable.ic_seats_booked);
                                view.setTextColor(Color.WHITE);
                                view.setTag(STATUS_BOOKED);
                                view.setText("U");
                                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                                Objects.requireNonNull(layout).addView(view);
                                seatViewList.add(view);
                                view.setOnClickListener(ChooseSeatLayout.this);
                            } else if (mSubSeats.startsWith("10") | mSubSeats.startsWith("11") | mSubSeats.startsWith("12") | mSubSeats.startsWith("13") | mSubSeats.startsWith("14") | mSubSeats.startsWith("15") | mSubSeats.startsWith("16") | mSubSeats.startsWith("17") | mSubSeats.startsWith("18") | mSubSeats.startsWith("19") | mSubSeats.startsWith("20") | mSubSeats.startsWith("21") | mSubSeats.startsWith("22") | mSubSeats.startsWith("23") | mSubSeats.startsWith("24")) {
                                Log.e("LOG12", "onCreate: " + index + "\t" + mSubSeats);
                                Par = ConvertStringToParityBit(mSubSeats);
                                String ID = mSubSeats.substring(2, 4);
                                //Log.e("ID", "onCreate: " + "Index\t" + seats.substring(index + 4) + "Seat No\t" + ID + "Parity Bit" + Par);
                                TextView view = new TextView(ChooseSeatLayout.this);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                                view.setLayoutParams(layoutParams);
                                view.setPadding(0, 0, 0, 2 * seatGaping);
                                view.setId(Integer.parseInt(mSubSeats));
                                view.setGravity(Gravity.CENTER);
                                view.setBackgroundResource(R.drawable.ic_seats_book);
                                view.setText(Par + "" + ID + "");
                                view.setHint(Par + "" + ID);
                                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                                view.setTextColor(Color.BLACK);
                                view.setTag(STATUS_AVAILABLE);
                                Objects.requireNonNull(layout).addView(view);
                                seatViewList.add(view);
                                view.setOnClickListener(ChooseSeatLayout.this);
                            } else if (mSubSeats.equals("RRRR")) {
                                TextView view = new TextView(ChooseSeatLayout.this);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                                view.setLayoutParams(layoutParams);
                                view.setPadding(0, 0, 0, 2 * seatGaping);
                                view.setId(((int) Calendar.getInstance().getTimeInMillis()));
                                view.setGravity(Gravity.CENTER);
                                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                                view.setText("R");
                                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                                view.setTextColor(Color.WHITE);
                                view.setTag(STATUS_RESERVED);
                                Objects.requireNonNull(layout).addView(view);
                                seatViewList.add(view);
                                view.setOnClickListener(ChooseSeatLayout.this);
                            } else if (mSubSeats.equals("____")) {
                                TextView view = new TextView(ChooseSeatLayout.this);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                                view.setLayoutParams(layoutParams);
                                view.setBackgroundColor(Color.TRANSPARENT);
                                view.setText("");
                                Objects.requireNonNull(layout).addView(view);
                            }
                        }
                    } else
                        Toast.makeText(ChooseSeatLayout.this, "Invalid Seat Layout!! Contact Developer", Toast.LENGTH_SHORT).show();
                });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn_Proceed.setOnClickListener(view -> {
                    FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Void>() {
                        @Override
                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                            Log.e("Button Prooced", "onCreate: " + "Clicked");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.show();
                                }
                            });
                            Log.e("Selected Id", "onCreate: " + selectedIds);
                            DocumentReference documentReference = mCollectionReference.document(MovieName);
                            DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                            seats = documentSnapshot.getString("SeatLayout");


                            if (NoOfPerson.equals("1") && selectedIds.length() <= 5 && selectedIds.length() > 0) {
                                if (Replace(selectedIds) && flag != 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("Button Prooced", "onCreate: " + "Clicked 1");
                                            //btn_Proceed.setError(null);
                                            Log.e("TAG", "apply: " + seats);
                                        }
                                    });
                                    getSeats();
                                    transaction.update(documentReference, "SeatLayout", seats);
                                    selectedIds = "";
                                } else
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChooseSeatLayout.this, "Some Error Please Retry!!", Toast.LENGTH_SHORT).show();
                                            mErrorCaused = true;
                                        }
                                    });
                                }

                                return null;
                            } else if (NoOfPerson.equals("2") && selectedIds.length() <= 10 && selectedIds.length() > 5) {
                                if (Replace(selectedIds) && flag != 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //btn_Proceed.setError(null);
                                            Log.e("TAG", "apply: " + seats);
                                        }
                                    });
//                                progressDialog.show();
                                    getSeats();
                                    transaction.update(documentReference, "SeatLayout", seats);
                                    selectedIds = "";
                                } else
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChooseSeatLayout.this, "Some Error Please Retry!!", Toast.LENGTH_SHORT).show();
                                            mErrorCaused = true;
                                        }
                                    });
                                }
                                return null;
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(ChooseSeatLayout.this, "Some Error Caused !", Toast.LENGTH_SHORT).show();
                                        mErrorCaused = true;
                                        startActivity(new Intent(ChooseSeatLayout.this, UserBookTickets.class));
                                        finishAffinity();
                                        //btn_Proceed.setError("Can't Book The Seats");
                                    }
                                });

                                return null;
                            }
                        }

                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void s) {
                            //Log.e("TAG", "onSuccess: ");
                            if(!mErrorCaused) {
                                Toast.makeText(ChooseSeatLayout.this, "Booking Success!!!!", Toast.LENGTH_SHORT).show();
                                Ticket ticket = new Ticket(uName, uSIC, uPhone, MovieName, SeatNo, SIC2, PhoneNumber2, Name2, null, mMovie.getDetails(), mMovie.getScreenDate(), mMovie.getPosterUrl(), "");
                                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tickets");
                                collectionReference.add(ticket).addOnSuccessListener((DocumentReference documentReference) -> {
                                    String ID = documentReference.getId();
                                    collectionReference.document(ID).update("mDocId", ID);
                                }).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChooseSeatLayout.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ChooseSeatLayout.this, BookingConfirmation.class);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.e("TAG", "onFailure: " + e.toString());
                            Toast.makeText(ChooseSeatLayout.this, "Hey Failure! " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                });
            }
        });

    }

    public char ConvertStringToParityBit(String mSubSeat) {
        char _Par = '_';
        if (mSubSeat.startsWith("10")) {
            _Par = 'A';
        } else if (mSubSeat.startsWith("11")) {
            _Par = 'B';
        } else if (mSubSeat.startsWith("12")) {
            _Par = 'C';
        } else if (mSubSeat.startsWith("13")) {
            _Par = 'D';
        } else if (mSubSeat.startsWith("14")) {
            _Par = 'E';
        } else if (mSubSeat.startsWith("15")) {
            _Par = 'F';
        } else if (mSubSeat.startsWith("16")) {
            _Par = 'G';
        } else if (mSubSeat.startsWith("17")) {
            _Par = 'H';
        } else if (mSubSeat.startsWith("18")) {
            _Par = 'I';
        } else if (mSubSeat.startsWith("19")) {
            _Par = 'J';
        } else if (mSubSeat.startsWith("20")) {
            _Par = 'K';
        } else if (mSubSeat.startsWith("21")) {
            _Par = 'L';
        } else if (mSubSeat.startsWith("22")) {
            _Par = 'M';
        }
        return _Par;

    }

    private char convert(String no) {
        char Par = '_';
        if (no.equals("10")) {
            Par = 'A';
            return Par;
        } else if (no.equals("11")) {
            Par = 'B';
            return Par;
        } else if (no.equals("12")) {
            Par = 'C';
            return Par;
        } else if (no.equals("13")) {
            Par = 'D';
            return Par;
        }
        if (no.equals("14")) {
            Par = 'E';
            return Par;
        } else if (no.equals("15")) {
            Par = 'F';
            return Par;
        }
        if (no.equals("16")) {
            Par = 'G';
            return Par;
        } else if (no.equals("17")) {
            Par = 'H';
            return Par;
        } else if (no.equals("18")) {
            Par = 'I';
            return Par;
        } else if (no.equals("19")) {
            Par = 'J';
            return Par;
        } else if (no.equals("20")) {
            Par = 'K';
            return Par;
        } else if (no.equals("21")) {
            Par = 'L';
            return Par;
        } else if (no.equals("22")) {
            Par = 'M';
            return Par;
        }
        return Par;

    }

    private boolean Replace(String selectedIds) {
        if (selectedIds.length() == 5) {
            String seat1 = selectedIds.substring(2, 4);
            String pa1 = selectedIds.substring(0, 2);
            char p1 = convert(pa1);
            seatno1 = selectedIds.substring(0, 4);
            SeatNo = "" + p1 + "" + seat1;
            return Replace_For(seatno1, null);
        } else if (selectedIds.length() == 10) {
            String seat1 = selectedIds.substring(2, 4);
            String seat2 = selectedIds.substring(7, 9);
            String pa1 = selectedIds.substring(0, 2);
            String pa2 = selectedIds.substring(5, 7);
            char p1 = convert(pa1);
            char p2 = convert(pa2);
            seatno1 = selectedIds.substring(0, 4);
            seatno2 = selectedIds.substring(5, 9);
            SeatNo = "" + p1 + seat1 + ", " + p2 + seat2;
            Log.e("TAG", "SeatNo : " + SeatNo + "Flag:" + flag);
            return Replace_For(seatno1, seatno2);
        } else
            return false;

    }

    private boolean Replace_For(String ReplaceString, String ReplaceString2) {
        flag = 0;
        for (int a = 0; a < seats.length(); a = a + 4) {
            if (seats.substring(a, a + 4).equals(ReplaceString) || seats.substring(a, a + 4).equals(ReplaceString2)) {
                seats = seats.substring(0, a) + "UUUU" + seats.substring(a + 4);
                flag++;
            }
        }
        Log.e("TAG", "Flag: " + flag);
        if (flag == 0) {
//            btn_Proceed.setError("Can't Book The Seats");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Toast toast = Toast.makeText(ChooseSeatLayout.this, "Can't Book Seats Retry!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            return false;
        } else {
            return true;
        }
    }

    private void getSeats() {
        if (selectedIds.length() == 10) {
            String seat1 = selectedIds.substring(2, 4);
            String seat2 = selectedIds.substring(7, 9);
            String pa1 = selectedIds.substring(0, 2);
            String pa2 = selectedIds.substring(5, 7);
            char p1 = convert(pa1);
            char p2 = convert(pa2);
            seatno1 = selectedIds.substring(0, 4);
            seatno2 = selectedIds.substring(5, 9);

            SeatNo = "" + p1 + seat1 + "  ,  " + p2 + seat2;

        } else if (selectedIds.length() == 5) {
            String seat1 = selectedIds.substring(2, 4);
            String pa1 = selectedIds.substring(0, 2);
            char p1 = convert(pa1);
            seatno1 = selectedIds.substring(0, 4);
            SeatNo = "" + p1 + "" + seat1;
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChooseSeatLayout.this, UserBookTickets.class));
        finishAffinity();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        //Log.e("Clicked", "onClick: " + view.getId());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ((int) view.getTag() == STATUS_AVAILABLE) {
                    if (selectedIds.length() <= max) {
                        if (selectedIds.contains(view.getId() + ",")) {
                            selectedIds = selectedIds.replace(+view.getId() + ",", "");
                            Log.e("TAG", "onClick: Selected" + selectedIds);
                            view.setBackgroundResource(R.drawable.ic_seats_book);
                        } else {
                            selectedIds = selectedIds + view.getId() + ",";
                            view.setBackgroundResource(R.drawable.ic_seats_selected);
                            Log.e("TAG", "onClick: Selected" + selectedIds);
                        }
                    } else if (selectedIds.contains(view.getId() + ",")) {
                        selectedIds = selectedIds.replace(+view.getId() + ",", "");
                        Log.e("TAG", "onClick: Selected" + selectedIds);
                        view.setBackgroundResource(R.drawable.ic_seats_book);
                    } else if (NoOfPerson.equals("2") && selectedIds.length() <= 6) {
                        Toast.makeText(ChooseSeatLayout.this, "Cannot  More Than Two Seats", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ChooseSeatLayout.this, "Cannot Choose More Than One Seats", Toast.LENGTH_SHORT).show();
                } else if ((int) view.getTag() == STATUS_BOOKED) {
                    Toast.makeText(ChooseSeatLayout.this, "This Seat is Booked", Toast.LENGTH_SHORT).show();
                } else if ((int) view.getTag() == STATUS_RESERVED) {
                    Toast.makeText(ChooseSeatLayout.this, "This Seat is Reserved", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


////2201220222032204220522062207____220822092210____________________________RRRRRRRRRRRRRRRR____RRRRRRRRRRRRRRRRRRRRRRRRRRRR////2101210221032104210521062107____21082109211021112112211321142115211621172118211921202121____2122212321242125212621272128////2001200220032004200520062007____20082009201020112012201320142015201620172018201920202021____2022202320242025202620272028////1901190219031904190519061907____19081909191019111912191319141915191619171918191919201921____1922192319241925192619271928////1801180218031804180518061807____18081809181018111812181318141815181618171818181918201821____1822182318241825182618271828////1701170217031704170517061707____17081709171017111712171317141715171617171718171917201721____1722172317241725172617271728////1601160216031604160516061607____16081609161016111612161316141615161616171618161916201621____1622162316241625162616271628////________________________________15011502150315041505150615071508150915101511151215131514________________________________////1401140214031404140514061407____14081409141014111412141314141415141614171418141914201421____1422142314241425142614271428////1301130213031304130513061307____13081309131013111312131313141315131613171318131913201321____1322132313241325132613271328////1201120212031204120512061207____12081209121012111212121312141215121612171218121912201221____1222122312241225122612271228////1101110211031104110511061107____11081109111011111112111311141115111611171118111911201121____1122112311241125112611271128////1001100210031004100510061007____10081009101010111012101310141015101610171018101910201021____1022102310241025102610271028

