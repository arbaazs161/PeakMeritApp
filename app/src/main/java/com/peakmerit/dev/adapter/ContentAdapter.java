package com.peakmerit.dev.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peakmerit.dev.ContentActivity;
import com.peakmerit.dev.R;
import com.peakmerit.dev.VideoActivity;
import com.peakmerit.dev.model.ContentDataClass;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder>{

    ArrayList<ContentDataClass> contents;

    public ContentAdapter(ArrayList<ContentDataClass> contents){
        this.contents = contents;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        holder.titleVideo.setText(contents.get(position).getContentName());
        String uriPath = "http://192.168.1.101:3000/content/getVideo?url=" + contents.get(position).getContentPath();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), VideoActivity.class);
                i.putExtra("contentPath", uriPath);
                i.putExtra("videoDesc", contents.get(position).getContentDesc());
                i.putExtra("videoName", contents.get(position).getContentName());
                i.putExtra("priority", contents.get(position).getPriority());

                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }


    class ContentViewHolder extends RecyclerView.ViewHolder{

        TextView titleVideo;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);

            titleVideo = itemView.findViewById(R.id.titleContent);
        }
    }
}
