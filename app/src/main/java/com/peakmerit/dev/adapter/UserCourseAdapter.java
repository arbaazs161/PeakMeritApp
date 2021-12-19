package com.peakmerit.dev.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peakmerit.dev.ContentActivity;
import com.peakmerit.dev.MainActivity;
import com.peakmerit.dev.R;
import com.peakmerit.dev.model.CourseDataClass;

import java.util.ArrayList;

public class UserCourseAdapter extends RecyclerView.Adapter<UserCourseAdapter.UserCourseViewHolder> {

    ArrayList<CourseDataClass> courseData;

    public UserCourseAdapter(ArrayList<CourseDataClass> courseData){
        this.courseData = courseData;
    }

    @NonNull
    @Override
    public UserCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_course_layout, parent, false);
        return new UserCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCourseViewHolder holder, int position) {

        holder.titleText.setText(courseData.get(position).getName());
        holder.authorText.setText(courseData.get(position).getAuthor());
        ArrayList<String> tags= courseData.get(position).getTags();

        for(int i = 0; i<tags.size(); i++){
            holder.tagsText.append(tags.get(i) + " ");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ContentActivity.class);
                i.putExtra("courseId", courseData.get(position).getId());
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseData.size();
    }

    class UserCourseViewHolder extends RecyclerView.ViewHolder {

        TextView titleText, authorText, tagsText;

        public UserCourseViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.courseTitleText);
            authorText = itemView.findViewById(R.id.courseAuthorText);
            tagsText = itemView.findViewById(R.id.courseTagsText);

        }
    }
}
