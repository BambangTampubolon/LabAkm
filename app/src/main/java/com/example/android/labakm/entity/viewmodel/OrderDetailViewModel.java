package com.example.android.labakm.entity.viewmodel;

public class OrderDetailViewModel {
    private int id;
    private int id_order;
    private int id_barang;
    private String nama_barang;
    private int total_barang;
    private int harga;
    private int total_harga;
    public String createdby;
    public String createdbyname;
    public String createdterminal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public int getTotal_barang() {
        return total_barang;
    }

    public void setTotal_barang(int total_barang) {
        this.total_barang = total_barang;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
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
        return "OrderDetailViewModel{" +
                "id=" + id +
                ", id_order=" + id_order +
                ", id_barang=" + id_barang +
                ", nama_barang='" + nama_barang + '\'' +
                ", total_barang=" + total_barang +
                ", harga=" + harga +
                ", total_harga=" + total_harga +
                '}';
    }
}
