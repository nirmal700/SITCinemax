package com.example.sitcinemax;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChooseSeatLayout extends AppCompatActivity implements View.OnClickListener{
    ViewGroup layout;

    private DatabaseReference userDb;
    String Name2,SIC2,PhoneNumber2,MovieName;
    Movies mMovie;
    String seats;
    SessionManager manager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

//    String seats = "____UUUUUUUUUUUUUUUUUUUUUUUU00070008000900100011RRRRRRRRRRRRRRRR____////"
//            + "____________________________________________________________________////"
//            + "UUUUUUUU________010801090110011101120113011401150116________RRRRRRRR////"
//            + "UUUUUUUU________UUUUUUUUUUUU020102020203020402050206________02070208////"
//            + "03010302________030303040305030603070308030903100311________03120313////"
//            + "04010402________RRRRRRRR04050406RRRR04080409RRRR0411________UUUU0413////"
//            + "UUUUUUUU________UUUUUUUUUUUU0501____RRRRRRRRRRRRRRRR________05020503////"
//            + "06010602________0603060406050606____0607060806090610________RRRRUUUU////"
//            + "07010402________RRRRRRRR07050706____07080709RRRR0711________UUUU0713////"
//            + "UUUUUUUU________UUUUUUUUUUUU0801____RRRRRRRRRRRRRRRR________08020803////"
//            + "09010902________0903090409050906____0907090809090910________RRRRUUUU////"
//            +"UUUUUUUU________UUUUUUUUUUUU1001____RRRRRRRRRRRRRRRR________10021003////";

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

    Button btn_Proceed;
    private final CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Movies");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat_layout);

        layout = findViewById(R.id.layoutSeat);
        btn_Proceed = findViewById(R.id.btn_Proceed);
        manager = new SessionManager(getApplicationContext());
        String uName= manager.getName();
        String uPhone = manager.getPhone();
        String uSIC  = manager.getSIC();

             Name2 = getIntent().getStringExtra("NAME_2");
             SIC2 = getIntent().getStringExtra("SIC_2");
             PhoneNumber2 = getIntent().getStringExtra("PHONE_NUMBER_2");
             MovieName = getIntent().getStringExtra("Movie_Name");
             NoOfPerson = getIntent().getStringExtra("NoOfPersons");

            if (NoOfPerson.equals("1"))
            {
                Name2="N/A";
                SIC2 = "N/A";
                PhoneNumber2 = "N/A";
                max=2;
            }
            else
                max=6;



//        Log.e("Seats Below", "onCreate: "+mMovie.getSeatLayout() );
        LinearLayout layoutSeat = new LinearLayout(ChooseSeatLayout.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        Query Seats = collectionReference.whereEqualTo("MovieName",MovieName);
        Seats.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot querySnapshot : Objects.requireNonNull(value)) {
                    mMovie = querySnapshot.toObject(Movies.class);
                }
                Log.e("mMovie", "onEvent: "+mMovie.getSeatLayout() );

                seats = mMovie.getSeatLayout();
                seats = "////" + seats;
                char Par = '_';

                LinearLayout layout = null;

                int count = 0;

                for (int index = 0; index < seats.length(); index=index+4) {
                    if (seats.charAt(index) == '/') {
                        layout = new LinearLayout(ChooseSeatLayout.this);
                        count = 0;
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        layoutSeat.addView(layout);
                    } else if (seats.charAt(index) == 'U') {
                        count++;
                        TextView view = new TextView(ChooseSeatLayout.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                        view.setLayoutParams(layoutParams);
                        view.setPadding(0, 0, 0, 2 * seatGaping);
                        view.setId(1000+count);
                        view.setGravity(Gravity.CENTER);
                        view.setBackgroundResource(R.drawable.ic_seats_booked);
                        view.setTextColor(Color.WHITE);
                        view.setTag(STATUS_BOOKED);
                        view.setText( "U");
                        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                        layout.addView(view);
                        seatViewList.add(view);
                        view.setOnClickListener(ChooseSeatLayout.this);
                    } else if (seats.startsWith("00", index) | seats.startsWith("01", index) | seats.startsWith("02", index) | seats.startsWith("03", index) | seats.startsWith("04", index) | seats.startsWith("05", index) | seats.startsWith("06", index) | seats.startsWith("07", index) | seats.startsWith("08", index) | seats.startsWith("09", index) | seats.startsWith("10", index) | seats.startsWith("11", index) | seats.startsWith("12", index) | seats.startsWith("13", index) | seats.startsWith("14", index)) {
                        count++;
                        Log.e("LOG12", "onCreate: "+index +"\t" + seats.substring(index,index+4) );
                        if(seats.startsWith("00", index))
                        {
                            Par = 'A';
                        }
                        else if(seats.startsWith("01", index))
                        {
                            Par = 'B';
                        }
                        else if(seats.startsWith("02", index))
                        {
                            Par = 'C';
                        }
                        else if(seats.startsWith("03", index))
                        {
                            Par = 'D';
                        }
                        if(seats.startsWith("04", index))
                        {
                            Par = 'E';
                        }
                        else if(seats.startsWith("05", index))
                        {
                            Par = 'F';
                        }
                        if(seats.startsWith("06", index))
                        {
                            Par = 'G';
                        }
                        else if(seats.startsWith("07", index))
                        {
                            Par = 'H';
                        }
                        else if(seats.startsWith("08", index))
                        {
                            Par = 'I';
                        }
                        else if(seats.startsWith("09", index))
                        {
                            Par = 'J';
                        }else if(seats.startsWith("10", index))
                        {
                            Par = 'K';
                        }

                        String ID = seats.substring(index+2,index+4);
                        Log.e("ID", "onCreate: "+"Index\t"+seats.substring(index,index+4)+"Seat No\t"+ID+"Parity Bit"+Par );
                        TextView view = new TextView(ChooseSeatLayout.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                        view.setLayoutParams(layoutParams);
                        view.setPadding(0, 0, 0, 2 * seatGaping);
                        view.setId(Integer.parseInt(seats.substring(index,index+4)));
                        view.setGravity(Gravity.CENTER);
                        view.setBackgroundResource(R.drawable.ic_seats_book);
                        view.setText(Par+""+ID + "");
                        view.setHint(Par+""+ID);
                        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                        view.setTextColor(Color.BLACK);
                        view.setTag(STATUS_AVAILABLE);
                        layout.addView(view);
                        seatViewList.add(view);
                        view.setOnClickListener(ChooseSeatLayout.this);
                    } else if (seats.charAt(index) == 'R') {
                        count++;
                        TextView view = new TextView(ChooseSeatLayout.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                        view.setLayoutParams(layoutParams);
                        view.setPadding(0, 0, 0, 2 * seatGaping);
                        view.setId(2000+count);
                        view.setGravity(Gravity.CENTER);
                        view.setBackgroundResource(R.drawable.ic_seats_reserved);
                        view.setText( "R");
                        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                        view.setTextColor(Color.WHITE);
                        view.setTag(STATUS_RESERVED);
                        layout.addView(view);
                        seatViewList.add(view);
                        view.setOnClickListener(ChooseSeatLayout.this);
                    } else if (seats.charAt(index) == '_') {
                        TextView view = new TextView(ChooseSeatLayout.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                        view.setLayoutParams(layoutParams);
                        view.setBackgroundColor(Color.TRANSPARENT);
                        view.setText("");
                        layout.addView(view);
                    }
                }

            }
        });

        btn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NoOfPerson.equals("1") && selectedIds.length() <= 5  ) {

                        btn_Proceed.setError(null);
                         getSeats();
                        Toast.makeText(ChooseSeatLayout.this, "Successfull" + selectedIds, Toast.LENGTH_SHORT).show();
                        Ticket ticket = new Ticket(uName, uSIC, uPhone, MovieName, SeatNo, SIC2, PhoneNumber2, Name2);
                        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tickets");
                        collectionReference.add(ticket).addOnSuccessListener(documentReference -> Toast.makeText(ChooseSeatLayout.this, "FireStore Updated", Toast.LENGTH_SHORT).show()).addOnCompleteListener(task -> {

                            //Intent intent = new Intent(ChooseSeatLayout.this, UserDashBoard.class);
                        });
                    } else if(NoOfPerson.equals("2") && selectedIds.length() <=10) {
                    btn_Proceed.setError(null);
                    getSeats();
                    Toast.makeText(ChooseSeatLayout.this, "Successfull" + selectedIds, Toast.LENGTH_SHORT).show();
                    Ticket ticket = new Ticket(uName, uSIC, uPhone, MovieName, SeatNo, SIC2, PhoneNumber2, Name2);
                    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tickets");
                    collectionReference.add(ticket).addOnSuccessListener(documentReference -> Toast.makeText(ChooseSeatLayout.this, "FireStore Updated", Toast.LENGTH_SHORT).show()).addOnCompleteListener(task -> {
                    });
                    }
                    else
                        btn_Proceed.setError("Check the Seats");

            }
        });
    }

    private void getSeats() {
        if(selectedIds.length()==10)
        {
            String seat1 = selectedIds.substring(2,4);
            String seat2 = selectedIds.substring(7,9);
            String pa1 = selectedIds.substring(0,2);
            String pa2 = selectedIds.substring(5,7);
            char p1 =convert(pa1);
            char p2 = convert(pa2);
            seatno1 = selectedIds.substring(0,4);
            seatno2 = selectedIds.substring(5,9);

             SeatNo = ""+p1+seat1+"  ,  "+p2+seat2;
            Log.e("Seats", "getSeats: "+seat1+seat2+pa1+pa2 +"\t"+SeatNo);

        }
        else if(selectedIds.length()==8)
        {
            String seat1 = selectedIds.substring(1,3);
            String seat2 = selectedIds.substring(5,7);
            String pa1 = selectedIds.substring(0,1);
            String pa2 = selectedIds.substring(4,5);
            char p1 =convert(pa1);
            char p2 = convert(pa2);
            seatno1 = selectedIds.substring(0,3);
            seatno2 = selectedIds.substring(4,7);
             SeatNo = ""+p1+seat1+"  ,  "+p2+seat2;
            Log.e("Seats", "getSeats: "+seat1+seat2+pa1+pa2 +"\t"+SeatNo);
        }
        else if(selectedIds.length()==5)
        {
            String seat1 = selectedIds.substring(2,4);
            String pa1 = selectedIds.substring(0,2);
            char p1 =convert(pa1);
            seatno1 = selectedIds.substring(0,4);
             SeatNo =""+ p1+""+seat1;
            Log.e("Seats", "getSeats: "+seat1+"\t"+SeatNo);
        }
        else if(selectedIds.length()==4)
        {
            String seat1 = selectedIds.substring(1,3);
            String pa1 = selectedIds.substring(0,1);
            seatno1 = selectedIds.substring(0,3);
            char p1 =convert(pa1);
             SeatNo =""+ p1+""+seat1;
            Log.e("Seats", "getSeats: "+seat1+"\t"+SeatNo);
        }

    }

    private char convert(String no) {
        char Par='Z';
        if(no.equals("00")| no.equals("0"))
        {
            Par = 'A';
            return Par;
        }
        else if(no.equals("01")|no.equals("1"))
        {
            Par = 'B';
            return Par;
        }
        else if(no.equals("02")|no.equals("2"))
        {
            Par = 'C';
            return Par;
        }
        else if(no.equals("03")|no.equals("3"))
        {
            Par = 'D';
            return Par;
        }
        if(no.equals("04")|no.equals("4"))
        {
            Par = 'E';
            return Par;
        }
        else if(no.equals("05")|no.equals("5"))
        {
            Par = 'F';
            return Par;
        }
        if(no.equals("06")|no.equals("6"))
        {
            Par = 'G';
            return Par;
        }
        else if(no.equals("07")|no.equals("7"))
        {
            Par = 'H';
            return Par;
        }
        else if(no.equals("08")|no.equals("8"))
        {
            Par = 'I';
            return Par;
        }
        else if(no.equals("09")|no.equals("9"))
        {
            Par = 'J';
            return Par;
        }else if(no.equals("10"))
        {
            Par = 'K';
            return Par;
        }
        return Par;

    }

    @Override
    public void onClick(View view) {
        Log.e("Clicked", "onClick: "+view.getId() );
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if(selectedIds.length()<=max) {
                if (selectedIds.contains(view.getId() + ",")) {
                    selectedIds = selectedIds.replace(+view.getId() + ",", "");
                    Log.e("IF1", "onClick: Selected" + selectedIds);
                    view.setBackgroundResource(R.drawable.ic_seats_book);
                } else {
                    selectedIds = selectedIds + view.getId() + ",";
                    view.setBackgroundResource(R.drawable.ic_seats_selected);
                    Log.e("IF2", "onClick: Selected" + selectedIds);
                }
            }
            else if(selectedIds.contains(view.getId() + ","))
            {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                Log.e("IF1", "onClick: Selected" + selectedIds);
                view.setBackgroundResource(R.drawable.ic_seats_book);
            }
            else if(NoOfPerson.equals("2")&&selectedIds.length()<=6)
            {
                Toast.makeText(ChooseSeatLayout.this, "Cannot  More Than one Seats", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(ChooseSeatLayout.this, "Cannot Choose More Than Two Seats", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }
}
