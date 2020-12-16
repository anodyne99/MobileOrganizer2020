package com.example.eventplanner;

public class Model {

    public String videoID, title, url;

    //constructor created here
    public Model(String videoID, String title, String url) {
        this.videoID = videoID;
        this.title = title;
        this.url = url;
    }

    //default constructor
    public Model(){
    }

    //getters and setters generated using generate option
    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
