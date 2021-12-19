package com.peakmerit.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peakmerit.dev.adapter.CourseAdapter;
import com.peakmerit.dev.model.CourseDataClass;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MyPrefs = "MyPrefs";
    ArrayList<CourseDataClass> courseList;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userId = sharedPreferences.getString("userId", "");

        Gson gson = new Gson();


        Type type = new TypeToken<ArrayList<CourseDataClass>>(){}.getType();

        if(userId.isEmpty()){
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else{
            Call<List<CourseDataClass>> call = RetrofitClient.getInstance().getApi().getAllCourseId(userId);
            call.enqueue(new Callback<List<CourseDataClass>>() {
                @Override
                public void onResponse(Call<List<CourseDataClass>> call, Response<List<CourseDataClass>> response) {
                    //List<CourseDataClass> myList = response.body();

                    courseList = (ArrayList<CourseDataClass>) response.body();

                    Log.d("Success", "response : "+ response.body());

                    json = gson.toJson(courseList);

                    editor.putString("courses", json);
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);



                    //recView.setAdapter(new CourseAdapter(courseList));

                /*for(int i = 0; i < myList.size(); i++){
                    //tv1.append("Name + " + myList.get(i).getName() + "\n Tags : " + myList.get(i).getTags() + "\nAuthor : " + myList.get(i).getAuthor() + "\n\n");
                }*/
                }

                @Override
                public void onFailure(Call<List<CourseDataClass>> call, Throwable t) {
                    Log.d("Failed....", "onFailure: " + t.getMessage());
                }
            });
        }






        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                }
                catch(Exception ex)
                {

                }
                finally{
                    //startActivity(new Intent(MainActivity.this, Register.class));

                        if(sharedPreferences.getString("logged","").equals("logged")){
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //startActivity(intent);
                        }

                        else {
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //startActivity(intent);
                        }
                }
            }
        });

        //t1.start();
    }
}