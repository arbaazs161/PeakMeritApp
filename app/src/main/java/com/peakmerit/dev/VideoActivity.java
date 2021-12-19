package com.peakmerit.dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.peakmerit.dev.adapter.ContentAdapter;
import com.peakmerit.dev.model.ContentDataClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    TextView textViewTitle, textViewDesc;
    ProgressBar progressBar;
    ImageView ivDown;
    LinearLayout hiddenView;
    CardView cardView;
    RecyclerView recView;
    int priority;
    ArrayList<ContentDataClass> contents;
    SharedPreferences sharedPreferences;
    private static final String MYPREFERENCES = "MyPrefs";
    String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        courseId = sharedPreferences.getString("videoCourseId", "");

        videoView = findViewById(R.id.contentVideo);
        textViewDesc = findViewById(R.id.textVideoDescription);
        textViewTitle = findViewById(R.id.textVideoName);

        progressBar = findViewById(R.id.progressBar);
        ivDown = findViewById(R.id.btnDropDown);
        hiddenView = findViewById(R.id.hiddenLayout);
        recView = findViewById(R.id.recyclerVideoView);

        String videoPath = getIntent().getStringExtra("contentPath");

        String videoDesc = getIntent().getStringExtra("videoDesc");

        String videoName = getIntent().getStringExtra("videoName");

        String pr = getIntent().getStringExtra("priority");

        if(pr!=null){
            priority = Integer.parseInt(pr);
        }
        else{
            priority = 1;
        }

        Call<List<ContentDataClass>> call = RetrofitClient.getInstance().getApi().getContentForVideoView(courseId, priority);

        call.enqueue(new Callback<List<ContentDataClass>>() {
            @Override
            public void onResponse(Call<List<ContentDataClass>> call, Response<List<ContentDataClass>> response) {
                contents = (ArrayList<ContentDataClass>) response.body();

                if(contents.size() > 0){
                    recView.setAdapter(new ContentAdapter(contents));
                }
            }

            @Override
            public void onFailure(Call<List<ContentDataClass>> call, Throwable t) {

            }
        });

        textViewDesc.setText(videoDesc);
        textViewTitle.setText(videoName);

        Uri vidUri = Uri.parse(videoPath);
        MediaController videoController = new MediaController(this);


        videoView.setMediaController(videoController);
        videoController.setAnchorView(videoView);

        videoView.setVideoURI(vidUri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                videoView.start();
            }
        });

        ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hiddenView.getVisibility() == View.VISIBLE){
                    //TransitionManager.beginDelayedTransition(cardView,
                      //      new AutoTransition());
                    hiddenView.setVisibility(View.GONE);
                }
                else{
                    //TransitionManager.beginDelayedTransition(cardView,
                      //      new AutoTransition());
                    hiddenView.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}