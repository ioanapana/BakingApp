package com.example.work_pana.mybakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class StepObject implements Parcelable, Serializable {

    private Integer id;

    private String shortDescription;

    private String description;

    private String videoURL;

    private String thumbnailURL;

    ///// Getters and setters for Step Object //////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    ///// Parcelable requirements //////

    protected StepObject(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<StepObject> CREATOR = new Creator<StepObject>() {
        @Override
        public StepObject createFromParcel(Parcel in) {
            return new StepObject(in);
        }

        @Override
        public StepObject[] newArray(int size) {
            return new StepObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}