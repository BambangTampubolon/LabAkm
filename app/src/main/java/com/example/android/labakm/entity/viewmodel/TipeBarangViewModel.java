package com.example.android.labakm.entity.viewmodel;

public class TipeBarangViewModel {
    private int id;
    private String nama;
    private String createddate;
    private String createdby;
    private String createdbyname;
    private String createdterminal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
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

    public void setCreatedterminal(String createdterminal) {
        this.createdterminal = createdterminal;
    }

    @Override
    public String toString() {
        return "TipeBarangViewModel{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", createddate='" + createddate + '\'' +
                ", createdby='" + createdby + '\'' +
                ", createdbyname='" + createdbyname + '\'' +
                ", createdterminal='" + createdterminal + '\'' +
                '}';
    }
}
