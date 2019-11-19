package com.example.travelnote1.공유하기;

public class Comment {
    private int iv_profile; //imageView int 임
    private String tv_name;
    private String tv_comment;

    public Comment(int iv_profile, String tv_name, String tv_comment) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_comment = tv_comment;
    }

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

    public String getTv_comment() {
        return tv_comment;
    }

    public void setTv_comment(String tv_comment) {
        this.tv_comment = tv_comment;
    }
}
