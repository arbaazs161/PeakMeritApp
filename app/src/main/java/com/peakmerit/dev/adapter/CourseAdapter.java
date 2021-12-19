package com.peakmerit.dev.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peakmerit.dev.CourseDetail;
import com.peakmerit.dev.R;
import com.peakmerit.dev.model.AuthorDataClass;
import com.peakmerit.dev.model.CourseDataClass;
import com.peakmerit.dev.model.UserDataClass;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.courseViewHolder>{

    ArrayList<CourseDataClass> courseData;
    SharedPreferences sharedPreferences;
    private final String MyPrefs = "MyPrefs";

    public CourseAdapter(ArrayList<CourseDataClass> courseData) {
        this.courseData = courseData;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_course_layout, parent, false);
        return new courseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {

        holder.itemView.setTag(courseData.get(position));
        holder.titleText.setText(courseData.get(position).getName());
        holder.authorText.setText(courseData.get(position).getAuthor());
        ArrayList<String> tags= courseData.get(position).getTags();

        for(int i = 0; i<tags.size(); i++){
            holder.tagsText.append(tags.get(i) + " ");
        }
    }

    @Override
    public int getItemCount() {
        return courseData.size();
    }

    class courseViewHolder extends RecyclerView.ViewHolder{

        ArrayList<String> enrolledCourse;

        TextView titleText, authorText, tagsText;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);

            sharedPreferences = itemView.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

            String userId = sharedPreferences.getString("userId", "");

            titleText = itemView.findViewById(R.id.courseTitleText);
            authorText = itemView.findViewById(R.id.courseAuthorText);
            tagsText = itemView.findViewById(R.id.courseTagsText);
            enrolledCourse = new UserDataClass().getUserCourses(userId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), CourseDetail.class);
                    CourseDataClass cd = (CourseDataClass) v.getTag();

                    if(!enrolledCourse.contains(cd.getId())){
                        i.putExtra("isEnrolled", "Not");
                    }
                    else{
                        i.putExtra("isEnrolled", "Enrolled");
                    }

                    i.putExtra("courseId", cd.getId());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
