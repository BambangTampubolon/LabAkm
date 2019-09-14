package com.example.android.labakm.entity.viewmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderViewModel implements Serializable{
    private int id;
    private int id_toko;
    private String nama_toko;
    private int status;
    private int total_harga;
    public String createddate;
    public String createdby;
    public String createdbyname;
    public String createdterminal;
    private List<OrderDetailViewModel> items;

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

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
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

    public List<OrderDetailViewModel> getItems() {
        return items;
    }

    public void setItems(List<OrderDetailViewModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderViewModel{" +
                "id=" + id +
                ", id_toko=" + id_toko +
                ", nama_toko='" + nama_toko + '\'' +
                ", status=" + status +
                ", total_harga=" + total_harga +
                ", createddate=" + createddate +
                ", createdby='" + createdby + '\'' +
                ", createdbyname='" + createdbyname + '\'' +
                ", createdterminal='" + createdterminal + '\'' +
                ", items=" + items +
                '}';
    }
}
