package com.example.android.labakm.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.android.labakm.setting.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "jurnal")
@TypeConverters(DateTypeConverter.class)
public class Jurnal {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "created_date")
    private Date created_date;

    @ColumnInfo(name = "total_kredit")
    private int total_kredit;

    @ColumnInfo(name = "total_debit")
    private int total_debit;

    @ColumnInfo(name = "id_corporation")
    private int id_corporation;

    @ColumnInfo(name = "keterangan")
    private String keterangan;

    @ColumnInfo(name = "id_akun")
    private String id_akun;

    @ColumnInfo(name = "nama_akun")
    private String nama_akun;

    @ColumnInfo(name = "saldo_awal")
    private int saldo_awal;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getId_corporation() {
        return id_corporation;
    }

    public void setId_corporation(int id_corporation) {
        this.id_corporation = id_corporation;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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

    public int getTotal_kredit() {
        return total_kredit;
    }

    public void setTotal_kredit(int total_kredit) {
        this.total_kredit = total_kredit;
    }

    public int getTotal_debit() {
        return total_debit;
    }

    public void setTotal_debit(int total_debit) {
        this.total_debit = total_debit;
    }

    public int getSaldo_awal() {
        return saldo_awal;
    }

    public void setSaldo_awal(int saldo_awal) {
        this.saldo_awal = saldo_awal;
    }

    @Override
    public String toString() {
        return "Jurnal{" +
                "id=" + id +
                ", created_date=" + created_date +
                ", total_kredit=" + total_kredit +
                ", total_debit=" + total_debit +
                ", id_corporation=" + id_corporation +
                ", keterangan='" + keterangan + '\'' +
                ", id_akun='" + id_akun + '\'' +
                ", nama_akun='" + nama_akun + '\'' +
                ", saldo_awal=" + saldo_awal +
                '}';
    }
}
