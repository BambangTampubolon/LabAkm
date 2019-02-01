package com.example.android.labakm.Interface;

import com.example.android.labakm.entity.Jurnal;

import java.util.List;

public interface JurnalManager {
    long insertJurnal(Jurnal jurnal);
    List<Jurnal> getAllJurnal(int idCorp);
    List<Jurnal> getAllJurnalForReport(Long startDate, Long endDate, int idCorp);
    List<Jurnal> getAllJurnalForReportLedger(Long startDate, Long endDate, int idCorp);
    List<Jurnal> getAllJurnalByKodeAkun(Long startDate, Long endDate, String akun, int idCorp);
    int deleteJurnal(int idJurnal);
    int updateJurnal(Jurnal jurnal);
}
