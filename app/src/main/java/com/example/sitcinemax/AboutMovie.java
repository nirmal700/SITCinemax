package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class AboutMovie extends YouTubeBaseActivity {

    String api_key = "AIzaSyDNqfIOvQasJJsE6uUGwiG7dH9Sg4lQ5fU";
    YouTubePlayerView youTubePlayerView;
    TextView tv_MovieName, tv_description,tv_date,tv_Details;
    RatingBar rbStars;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_movie);
        tv_MovieName = findViewById(R.id.tv_MovieName);
        tv_date = findViewById(R.id.tv_date);
        tv_description = findViewById(R.id.tv_description);
        tv_Details = findViewById(R.id.tv_Details);
        rbStars = findViewById(R.id.rbStars);
        Intent intent = getIntent();
        Movies mTrailer = (Movies)intent.getSerializableExtra("Movie_Details");
        tv_MovieName.setText(mTrailer.getMovieName());
        tv_description.setText("\t\t\t"+mTrailer.getMovieDescription());
        rbStars.setRating(Float.parseFloat(mTrailer.getMovieRating()));
        tv_date.setText(mTrailer.getScreenDate());
        tv_Details.setText(mTrailer.getDetails());



        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.YoutubePlayer);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                 youTubePlayer.loadVideo(mTrailer.getTrailerURL());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("TAG", "onInitializationFailure: "+youTubeInitializationResult.toString() +provider.toString() );
            }
        };
        youTubePlayerView.initialize(api_key,onInitializedListener);
    }
}