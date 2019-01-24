package com.example.android.labakm.ViewModel;

public class JurnalNeraca {
    private int total_jumlah;
    private int id;
    private String id_akun;
    private String nama_akun;

    public int getTotal_jumlah() {
        return total_jumlah;
    }

    public void setTotal_jumlah(int total_jumlah) {
        this.total_jumlah = total_jumlah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_akun() {
        return id_akun;
    }

    public void setId_akun(String id_akun) {
        this.id_akun = id_akun;
    }

    public String getNama_akun() {
        return nama_akun;
    }

    public void setNama_akun(String nama_akun) {
        this.nama_akun = nama_akun;
    }

    @Override
    public String toString() {
        return "JurnalNeraca{" +
                "total_jumlah=" + total_jumlah +
                ", id=" + id +
                ", id_akun=" + id_akun +
                ", nama_akun='" + nama_akun + '\'' +
                '}';
    }
}
