package com.example.travelnote1.공유하기;

public class Share {
    private int iv_profile; //imageView int 임
    private String tv_name;
    private String tv_cotent;
    private String tv_title;

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_cotent() {
        return tv_cotent;
    }

    public void setTv_cotent(String tv_cotent) {
        this.tv_cotent = tv_cotent;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String tv_title) {
        this.tv_title = tv_title;
    }

    public Share(int iv_profile, String tv_name, String tv_cotent, String tv_title) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_cotent = tv_cotent;
        this.tv_title = tv_title;
    }
}
