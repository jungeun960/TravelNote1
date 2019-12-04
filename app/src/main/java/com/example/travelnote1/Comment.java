package com.example.travelnote1;

public class Comment {
    private String iv_profile; //imageView int 임
    private String tv_name;
    private String tv_comment;
    private String id;

    public Comment() {
    }

    public Comment(String iv_profile, String tv_name, String tv_comment, String id) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_comment = tv_comment;
        this.id = id;
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

    public String getTv_comment() {
        return tv_comment;
    }

    public void setTv_comment(String tv_comment) {
        this.tv_comment = tv_comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
