package com.example.travelnote1;

public class Person {
    private String et_email;
    private String et_name;
    private String et_pass;
    private String et_pass_ok;

    public Person(String et_email, String et_name, String et_pass, String et_pass_ok) {
        this.et_email = et_email;
        this.et_name = et_name;
        this.et_pass = et_pass;
    }

    public Person() {
    }

    public String getEt_email() {
        return et_email;
    }

    public void setEt_email(String et_email) {
        this.et_email = et_email;
    }

    public String getEt_name() {
        return et_name;
    }

    public void setEt_name(String et_name) {
        this.et_name = et_name;
    }

    public String getEt_pass() {
        return et_pass;
    }

    public void setEt_pass(String et_pass) {
        this.et_pass = et_pass;
    }

    public String getEt_pass_ok() {
        return et_pass_ok;
    }

    public void setEt_pass_ok(String et_pass_ok) {
        this.et_pass_ok = et_pass_ok;
    }
}
