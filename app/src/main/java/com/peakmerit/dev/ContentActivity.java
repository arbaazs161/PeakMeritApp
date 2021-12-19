package com.peakmerit.dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.peakmerit.dev.adapter.ContentAdapter;
import com.peakmerit.dev.model.ContentDataClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tv;
    ArrayList<ContentDataClass> contents;

    SharedPreferences sharedPreferences;
    private static final String MYPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        String courseId = getIntent().getStringExtra("courseId");
        recyclerView = findViewById(R.id.recyclerContent);
        tv = findViewById(R.id.textContentEmpty);
        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("videoCourseId", courseId);
        editor.commit();

        tv.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contents = new ArrayList<>();

        Call<List<ContentDataClass>> call = RetrofitClient.getInstance().getApi().getContents(courseId);

        call.enqueue(new Callback<List<ContentDataClass>>() {
            @Override
            public void onResponse(Call<List<ContentDataClass>> call, Response<List<ContentDataClass>> response) {

                contents = (ArrayList<ContentDataClass>) response.body();

                if(contents.size() > 0){
                    recyclerView.setAdapter(new ContentAdapter(contents));
                }
                else {
                    tv.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<List<ContentDataClass>> call, Throwable t) {

            }
        });
    }
}