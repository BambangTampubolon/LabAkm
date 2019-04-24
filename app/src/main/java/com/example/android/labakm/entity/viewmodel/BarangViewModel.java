package com.example.android.labakm.entity.viewmodel;

public class BarangViewModel {
    private int id;
    private int tipe_barang_id;
    private String nama;
    private int harga;
    private int jumlah;
    private String createdby;
    private String createddate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipe_barang_id() {
        return tipe_barang_id;
    }

    public void setTipe_barang_id(int tipe_barang_id) {
        this.tipe_barang_id = tipe_barang_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    @Override
    public String toString() {
        return "BarangViewModel{" +
                "id=" + id +
                ", tipe_barang_id=" + tipe_barang_id +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                ", createdby='" + createdby + '\'' +
                ", createddate='" + createddate + '\'' +
                '}';
    }
}
