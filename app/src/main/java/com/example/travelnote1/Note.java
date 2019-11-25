package com.example.travelnote1;

public class Note {
    private String tv_day;
    private String tv_title;
    private String tv_location;
    private String imageView6;
    private String tv_note;

    public Note(String tv_day, String tv_title, String tv_location, String imageView6, String tv_note) {
        this.tv_day = tv_day;
        this.tv_title = tv_title;
        this.tv_location = tv_location;
        this.imageView6 = imageView6;
        this.tv_note = tv_note;
    }

    public String getTv_day() {
        return tv_day;
    }

    public void setTv_day(String tv_day) {
        this.tv_day = tv_day;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String tv_title) {
        this.tv_title = tv_title;
    }

    public String getTv_location() {
        return tv_location;
    }

    public void setTv_location(String tv_location) {
        this.tv_location = tv_location;
    }

    public String getImageView6() {
        return imageView6;
    }

    public void setImageView6(String imageView6) {
        this.imageView6 = imageView6;
    }

    public String getTv_note() {
        return tv_note;
    }

    public void setTv_note(String tv_note) {
        this.tv_note = tv_note;
    }
}
