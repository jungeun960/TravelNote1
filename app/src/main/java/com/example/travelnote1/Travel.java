package com.example.travelnote1;

public class Travel {

    private String imageUrl;
    private String travel_name;
    private String travel_date;
    private String id;

    public Travel() {
    }

    public Travel(String imageUrl, String travel_name, String travel_date, String id) {
        this.imageUrl = imageUrl;
        this.travel_name = travel_name;
        this.travel_date = travel_date;
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTravel_name() {
        return travel_name;
    }

    public void setTravel_name(String travel_name) {
        this.travel_name = travel_name;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
