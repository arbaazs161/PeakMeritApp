package com.peakmerit.dev;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.peakmerit.dev.adapter.CourseAdapter;
import com.peakmerit.dev.adapter.UserCourseAdapter;
import com.peakmerit.dev.model.CourseDataClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifFragment extends Fragment {

    RecyclerView recView;
    TextView tv;
    ArrayList<CourseDataClass> courseList;

    SharedPreferences sharedPreferences;
    public static final String MyPrefs = "MyPrefs";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotifFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotifFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifFragment newInstance(String param1, String param2) {
        NotifFragment fragment = new NotifFragment();
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
        return inflater.inflate(R.layout.fragment_notif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = this.getActivity().getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        Log.d("USERID", "onViewCreated: UserId" + userId);
        recView = view.findViewById(R.id.recyclerUserCourse);
        tv = view.findViewById(R.id.textEnrollEmpty);
        recView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        tv.setVisibility(View.GONE);

        courseList = new ArrayList<>();

        Call<List<CourseDataClass>> call = RetrofitClient.getInstance().getApi().getUserCourse(userId);

        call.enqueue(new Callback<List<CourseDataClass>>() {
            @Override
            public void onResponse(Call<List<CourseDataClass>> call, Response<List<CourseDataClass>> response) {
                courseList = (ArrayList<CourseDataClass>) response.body();

                Log.d("Success", "response : "+ courseList.size());

                if(courseList.size() > 0){
                    recView.setAdapter(new UserCourseAdapter(courseList));
                }
                else{
                    tv.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onFailure(Call<List<CourseDataClass>> call, Throwable t) {
                Log.d("Failed....", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Some Error Occurred, Please Try Later", Toast.LENGTH_LONG).show();
            }
        });

    }
}

