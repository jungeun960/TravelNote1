package com.example.travelnote1.폴더추가하기;

public class Travel {

    private String imageUrl;
    private String travel_name;
    private String travel_date;

    public Travel(String imageUrl, String travel_name, String travel_date) {
        this.imageUrl = imageUrl;
        this.travel_name = travel_name;
        this.travel_date = travel_date;
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
}
