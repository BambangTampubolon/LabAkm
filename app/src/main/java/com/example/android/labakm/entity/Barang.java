package com.example.android.labakm.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.android.labakm.setting.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "barang")
@TypeConverters(DateTypeConverter.class)
public class Barang {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "tipe_barang_id")
    private int tipe_barang_id;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "harga")
    private int harga;

    @ColumnInfo(name = "createdby")
    private String createdby;

    @ColumnInfo(name = "createddate")
    private Date createddate;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
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

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @Override
    public String toString() {
        return "Barang{" +
                "id=" + id +
                ", tipe_barang_id=" + tipe_barang_id +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                ", createdby='" + createdby + '\'' +
                ", createddate=" + createddate +
                '}';
    }
}
