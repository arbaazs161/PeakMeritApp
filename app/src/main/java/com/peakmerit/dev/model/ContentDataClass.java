package com.peakmerit.dev.model;

import com.google.gson.annotations.SerializedName;

public class ContentDataClass {
    String contentName, contentPath;
    int priority;

    @SerializedName("description")
    String contentDesc;

    public ContentDataClass(String contentName, String contentPath, String contentDesc, int priority) {
        this.contentName = contentName;
        this.contentPath = contentPath;
        this.contentDesc = contentDesc;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }
}
