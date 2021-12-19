package com.peakmerit.dev.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.peakmerit.dev.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataClass {
    String name, email, password;


    ArrayList<String> tags;

    public ArrayList<String> userCourses;

    @SerializedName("_id")
    String userId;

    public UserDataClass(){

    }

    public UserDataClass(String name, String email, String password, String userId, ArrayList<String> tags) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getUserCourses(String userId){

        userCourses = new ArrayList<>();

        Call<List<String>> call = RetrofitClient.getInstance().getApi().getCourseList(userId);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                userCourses = (ArrayList<String>) response.body();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("", "");

            }
        });

        return userCourses;
    }
}
