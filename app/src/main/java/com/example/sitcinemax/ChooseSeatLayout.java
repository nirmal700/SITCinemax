package com.example.sitcinemax;

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

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ChooseSeatLayout extends AppCompatActivity implements View.OnClickListener{
    ViewGroup layout;
    private DatabaseReference userDb;

    String seats = "____UUUUUUUUUUUUUUUUUUUUUUUU00070008000900100011RRRRRRRRRRRRRRRR____////"
            + "____________________________________________________________________////"
            + "UUUUUUUU________010801090110011101120113011401150116________RRRRRRRR////"
            + "UUUUUUUU________UUUUUUUUUUUU020102020203020402050206________02070208////"
            + "03010302________030303040305030603070308030903100311________03120313////"
            + "04010402________RRRRRRRR04050406RRRR04080409RRRR0411________UUUU0413////"
            + "UUUUUUUU________UUUUUUUUUUUU0501____RRRRRRRRRRRRRRRR________05020503////"
            + "06010602________0603060406050606____0607060806090610________RRRRUUUU////"
            + "07010402________RRRRRRRR07050706____07080709RRRR0711________UUUU0713////"
            + "UUUUUUUU________UUUUUUUUUUUU0801____RRRRRRRRRRRRRRRR________08020803////"
            + "09010902________0903090409050906____0907090809090910________RRRRUUUU////"
            +"UUUUUUUU________UUUUUUUUUUUU1001____RRRRRRRRRRRRRRRR________10021003////";

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";

    Button btn_Proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat_layout);

        layout = findViewById(R.id.layoutSeat);
        btn_Proceed = findViewById(R.id.btn_Proceed);

        seats = "////" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        char Par = '_';

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index=index+4) {
            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                count = 0;
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
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
                view.setOnClickListener(this);
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
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(Integer.parseInt(seats.substring(index,index+4)));
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(Par+""+ID + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'R') {
                count++;
                TextView view = new TextView(this);
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
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
        btn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIds.length()>1&&selectedIds.length()<=10)
                {
                    btn_Proceed.setError(null);
                    Toast.makeText(ChooseSeatLayout.this, "Successfull"+ selectedIds, Toast.LENGTH_SHORT).show();
                }
                else
                    btn_Proceed.setError("Check The Seats");
            }
        });
    }

    @Override
    public void onClick(View view) {
        Log.e("Clicked", "onClick: "+view.getId() );
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if(selectedIds.length()<=6) {
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
            else
                Toast.makeText(ChooseSeatLayout.this, "Cannot Choose More Than Two Seats", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }
}
