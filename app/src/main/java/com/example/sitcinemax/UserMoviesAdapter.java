package com.example.sitcinemax;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class UserMoviesAdapter extends RecyclerView.Adapter<UserMoviesAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<Movies> mMovies;
    private OnItemClickListener mListener;
    private final List<Movies> copyList;

    public UserMoviesAdapter(Context context, List<Movies> movies) {

        mContext = context;
        mMovies = movies;
        this.copyList = new ArrayList<>();
        copyList.addAll(movies);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view_movie, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Movies currentData = mMovies.get(position);
        holder.tv_title.setText(currentData.MovieName);
        holder.tv_description.setText(currentData.MovieDescription);
        Glide.with(mContext)
                .load(currentData.getPosterUrl())
                .placeholder(R.drawable.sand_clock)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView tv_title,tv_description;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_poster);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);

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





