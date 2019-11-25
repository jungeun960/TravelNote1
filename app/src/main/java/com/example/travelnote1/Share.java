package com.example.travelnote1;

public class Share {
    private String iv_profile;
    private String tv_name;
    private String tv_cotent;
    private String tv_title;

    public Share(String iv_profile, String tv_name, String tv_cotent, String tv_title) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_cotent = tv_cotent;
        this.tv_title = tv_title;
    }

    public Share() {
    }

    public String getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(String iv_profile) {
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
}
