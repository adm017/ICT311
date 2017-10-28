package au.edu.usc.adm017.myactivities.activity;

import android.graphics.Bitmap;

public class AppActivity {

    private int id;
    private String title;
    private String date;
    private ActivityType type;
    private String place;
    private String comments;
    private Bitmap picture;
    private String coordinates;

    private double latitude;
    private double longitude;

    public AppActivity(int id, String title, String date, ActivityType type, String place, String comments, Bitmap picture, String coordinates) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.type = type;
        this.place = place;
        this.comments = comments;
        this.picture = picture;
        this.coordinates = coordinates;

        if (this.coordinates.length() > 0) {
            String[] coordinateData = this.coordinates.split(",");
            this.latitude = Double.parseDouble(coordinateData[0]);
            this.longitude = Double.parseDouble(coordinateData[1]);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
