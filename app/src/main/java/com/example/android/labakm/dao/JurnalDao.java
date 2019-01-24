package com.example.android.labakm.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.labakm.entity.Jurnal;

import java.util.List;

@Dao
public interface JurnalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJurnal(Jurnal jurnal);

    @Query("SELECT * FROM jurnal where id_corporation = :idCorp ORDER BY created_date")
    List<Jurnal> getAllJurnal(int idCorp);

    @Query("SELECT * FROM jurnal WHERE created_date <= :endDate AND created_date >= :startDate " +
            "AND id_corporation = :idCorp ORDER BY created_date")
    List<Jurnal> getAllJurnalForReport(Long startDate, Long endDate, int idCorp);

    @Query("SELECT * FROM jurnal WHERE created_date <= :endDate AND created_date >= :startDate " +
            "AND id_corporation = :idCorp ORDER BY id_akun")
    List<Jurnal> getAllJurnalForReportLedger(Long startDate, Long endDate, int idCorp);

    @Query("SELECT SUM(total_kredit) as total_kredit, SUM(total_debit) as total_debit,keterangan ,id_akun, nama_akun, created_date, saldo_awal,id,id_corporation FROM jurnal where " +
            " created_date <= :endDate AND created_date >= :startDate AND id_corporation = :idCorp " +
            "AND id_akun LIKE :akun ORDER BY id_akun")
    List<Jurnal> getAllJurnalByKodeAkun(Long startDate, Long endDate, String akun, int idCorp);

    @Query("DELETE FROM jurnal where id = :idJurnal")
    void deleteJurnal(int idJurnal);

    @Update
    void updateJurnal(Jurnal jurnal);


}
