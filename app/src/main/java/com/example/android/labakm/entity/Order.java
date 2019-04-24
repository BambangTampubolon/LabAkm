package com.example.android.labakm.entity;

import java.util.Date;

public class Order{
    private int id;
    private int id_toko;
    private String nama_toko;
    private int status;
    private int order_id;
    private int total_harga;
    public Date createddate;
    public String createdby;
    public String createdbyname;
    public String createdterminal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_toko() {
        return id_toko;
    }

    public void setId_toko(int id_toko) {
        this.id_toko = id_toko;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
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
        return "Order{" +
                "id=" + id +
                ", id_toko=" + id_toko +
                ", nama_toko='" + nama_toko + '\'' +
                ", status=" + status +
                ", order_id=" + order_id +
                ", total_harga=" + total_harga +
                ", createddate='" + createddate + '\'' +
                ", createdby='" + createdby + '\'' +
                ", createdbyname='" + createdbyname + '\'' +
                ", createdterminal='" + createdterminal + '\'' +
                '}';
    }
}
