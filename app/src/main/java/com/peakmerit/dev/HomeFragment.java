package com.peakmerit.dev;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peakmerit.dev.adapter.CourseAdapter;
import com.peakmerit.dev.model.CourseDataClass;
import com.peakmerit.dev.model.UserDataClass;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recView;
    ArrayList<CourseDataClass> courseList;
    ArrayList<String> enrolledCourse;
    SharedPreferences sharedPreferences;
    public static final String MyPrefs = "MyPrefs";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, null);
        Log.d("!st", "Pass");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = this.getActivity().getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        recView = view.findViewById(R.id.recyclerBrowse);
        recView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        courseList = new ArrayList<>();
        //tv1 = view.findViewById(R.id.textViewTest);
        Log.d("Created", "View Created....");
        enrolledCourse = new UserDataClass().getUserCourses(userId);


        Call<List<CourseDataClass>> call = RetrofitClient.getInstance().getApi().getAllCourses();

        call.enqueue(new Callback<List<CourseDataClass>>() {
            @Override
            public void onResponse(Call<List<CourseDataClass>> call, Response<List<CourseDataClass>> response) {
                //List<CourseDataClass> myList = response.body();

                courseList = (ArrayList<CourseDataClass>) response.body();

                Log.d("Success", "response : "+ response.body());

                recView.setAdapter(new CourseAdapter(courseList));

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
}