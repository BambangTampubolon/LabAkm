package com.example.android.labakm.ViewModel;

import java.util.Date;

public class JurnalLedger {
    private int id;
    private Date created_date;
    private int total_kredit;
    private int total_debit;
    private int id_corporation;
    private String keterangan;
    private String id_akun;
    private String nama_akun;
    private int saldo_awal;
    private int saldo_akhir;
    private int total_modal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
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

    public int getSaldo_awal() {
        return saldo_awal;
    }

    public void setSaldo_awal(int saldo_awal) {
        this.saldo_awal = saldo_awal;
    }

    public int getSaldo_akhir() {
        return saldo_akhir;
    }

    public void setSaldo_akhir(int saldo_akhir) {
        this.saldo_akhir = saldo_akhir;
    }

    public int getTotal_modal() {
        return total_modal;
    }

    public void setTotal_modal(int total_modal) {
        this.total_modal = total_modal;
    }

    @Override
    public String toString() {
        return "JurnalLedger{" +
                "id=" + id +
                ", created_date=" + created_date +
                ", total_kredit=" + total_kredit +
                ", total_debit=" + total_debit +
                ", id_corporation=" + id_corporation +
                ", keterangan='" + keterangan + '\'' +
                ", id_akun='" + id_akun + '\'' +
                ", nama_akun='" + nama_akun + '\'' +
                ", saldo_awal=" + saldo_awal +
                ", saldo_akhir=" + saldo_akhir +
                '}';
    }
}
