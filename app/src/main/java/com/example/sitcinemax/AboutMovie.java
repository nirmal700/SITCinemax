package com.example.sitcinemax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class AboutMovie extends YouTubeBaseActivity {

    String api_key = "AIzaSyDNqfIOvQasJJsE6uUGwiG7dH9Sg4lQ5fU";
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_movie);
        Intent intent = getIntent();
        Movies mTrailer = (Movies)intent.getSerializableExtra("Movie_Details");

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