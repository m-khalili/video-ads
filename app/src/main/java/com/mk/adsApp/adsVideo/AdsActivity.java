package com.mk.adsApp.adsVideo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mk.adsApp.R;

public class AdsActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private ImaAdsLoader adsLoader;
    ProgressBar progressBar;
    ImageView btnFullScreen;
    String TAG = "tag";
    private Handler handler;
    boolean flag = false;
    boolean isAdsPlaying = false;
    String urlVideo= "https://hw20.cdn.asset.aparat.com/aparat-video/278d0cfb030e7d6facb0f78a05ab687522946720-720p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImM3MWMxNmRlZjc2ZjA0ZDJiMmE1ZmQ4NzY3MDg1MjQyIiwiZXhwIjoxNjA5NjEwMzQ5LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.1ey4Xja31Uffshf2Ac2-w1ZOGgt2PaH1jzP5sBZbbSw";
    String url = "https://aspb27.cdn.asset.aparat.com/aparat-video/3bf2c80ca13d7b3f4c1d0a90734dd21428345516-144p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjE5Mjg5YWM5YWUzZjZhMjdjMzQyMWE2YzNmMDY2MmQyIiwiZXhwIjoxNjA5NTg3NTU1LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.1-YuIG938qC6cW-QTjXtBNKeWWtQFWiMta_txtmm5GE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_ads);
        bind();


        adsLoader = new ImaAdsLoader(this, Uri.parse(getString(R.string.ad_tag_vast)));


        initializePlayer();
    }

    private void initializePlayer() {
// Create a SimpleExoPlayer and set it as the player for content and ads.
            player = new SimpleExoPlayer.Builder(this).build();
            playerView.setPlayer(player);
            adsLoader.setPlayer(player);

            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));

            ProgressiveMediaSource.Factory mediaSourceFactory =
                    new ProgressiveMediaSource.Factory(dataSourceFactory);

            // Create the MediaSource for the content you wish to play.

            MediaSource mediaSource =
                    mediaSourceFactory.createMediaSource(Uri.parse(urlVideo));

            // Create the AdsMediaSource using the AdsLoader and the MediaSource.

            AdsMediaSource adsMediaSource =
                    new AdsMediaSource(mediaSource, dataSourceFactory, adsLoader, playerView);


            // Prepare the content and ad to be played with the SimpleExoPlayer.
            player.addListener(new Player.EventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                    if (playbackState == Player.STATE_BUFFERING){
                        progressBar.setVisibility(View.VISIBLE);
                    }else if (playbackState == Player.STATE_READY){
                        progressBar.setVisibility(View.GONE);

                    }
                }



            });

        player.prepare(adsMediaSource);

        // Set PlayWhenReady. If true, content and ads autoplay.

        player.setPlayWhenReady(true);



    }

    private void releasePlayer() {
        adsLoader.setPlayer(null);
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    private void bind() {
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        btnFullScreen = findViewById(R.id.bt_fullscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    @Override
    protected void onStart() {
        super.onStart();
        playerView.setUseController(true);
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        player.getPlaybackState();

        Log.d(TAG, "onPause: ");
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        player.setPlayWhenReady(true);

        player.getPlaybackState();
    }



}