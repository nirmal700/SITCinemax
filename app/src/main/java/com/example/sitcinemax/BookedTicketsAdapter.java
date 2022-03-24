package com.example.sitcinemax;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookedTicketsAdapter extends RecyclerView.Adapter<BookedTicketsAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<Ticket> mTicket;
    private OnItemClickListener mListener;

    public BookedTicketsAdapter(Context context, List<Ticket> tickets) {

        mContext = context;
        mTicket = tickets;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view_tickets, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Ticket currentData = mTicket.get(position);
        holder.tv_MovieName.setText(currentData.getMovieName());
        holder.tv_name1.setText(currentData.getNameUser() + "(" + currentData.getSICUser() + ")");

        holder.seats.setText(currentData.getSeats());
        holder.tv_Details.setText(currentData.getmDetails());
        holder.tv_screenDate.setText(currentData.getmScreenDate());
        if (currentData.getSIC2().equals("N/A") || currentData.getName2().equals("N/A")) {
            holder.tv_no_ticket.setText("1");
            holder.person2.setVisibility(View.GONE);
        } else {
            holder.tv_no_ticket.setText("2");
            holder.tv_name2.setText(currentData.getName2() + "(" + currentData.getSIC2() + ")");
        }


        Glide.with(mContext)
                .load(currentData.getmPoster())
                .placeholder(R.drawable.sand_clock)
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return mTicket.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView poster;
        public TextView tv_MovieName, tv_screenDate, tv_Details, tv_name1, tv_name2, seats, tv_no_ticket;
        public LinearLayout person2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.iv_member);
            tv_MovieName = itemView.findViewById(R.id.tv_MovieName);
            tv_screenDate = itemView.findViewById(R.id.tv_screenDate);
            tv_Details = itemView.findViewById(R.id.tv_Details);
            tv_name1 = itemView.findViewById(R.id.tv_name1);
            tv_name2 = itemView.findViewById(R.id.tv_name2);
            seats = itemView.findViewById(R.id.seats);
            tv_screenDate = itemView.findViewById(R.id.tv_screenDate);
            tv_no_ticket = itemView.findViewById(R.id.tv_no_ticket);
            person2 = itemView.findViewById(R.id.person2);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }

        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}





