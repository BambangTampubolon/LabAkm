package com.example.android.labakm.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "akun")
public class Akun implements Serializable{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_corporation")
    private  int id_corporation;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "kode")
    private String kode;

    @ColumnInfo(name = "saldo_awal")
    private int saldo_awal;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getId_corporation() {
        return id_corporation;
    }

    public void setId_corporation(int id_corporation) {
        this.id_corporation = id_corporation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public int getSaldo_awal() {
        return saldo_awal;
    }

    public void setSaldo_awal(int saldo_awal) {
        this.saldo_awal = saldo_awal;
    }

    @Override
    public String toString() {
        return "Akun{" +
                "id=" + id +
                ", id_corporation=" + id_corporation +
                ", name='" + name + '\'' +
                ", kode='" + kode + '\'' +
                ", saldo_awal=" + saldo_awal +
                '}';
    }
}
