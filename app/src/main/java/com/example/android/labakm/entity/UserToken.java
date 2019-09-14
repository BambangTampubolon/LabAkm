package com.example.android.labakm.entity;

public class UserToken {
    private int id;
    private String user_name;
    private int password;
    private String firebase_token;
    private int user_level;
    public String createdby;
    public String createdbyname;
    public String createdterminal;
    private int status;
    private String email;
    private String alamat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreatedbyname() {
        return createdbyname;
    }

    public void setCreatedbyname(String createdbyname) {
        this.createdbyname = createdbyname;
    }

    public String getCreatedterminal() {
        return createdterminal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setCreatedterminal(String createdterminal) {
        this.createdterminal = createdterminal;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", firebase_token='" + firebase_token + '\'' +
                ", user_level=" + user_level +
                ", createdby='" + createdby + '\'' +
                ", createdbyname='" + createdbyname + '\'' +
                ", createdterminal='" + createdterminal + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", alamat='" + alamat + '\'' +
                '}';
    }
}
