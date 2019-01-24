package com.example.android.labakm.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.labakm.entity.Corporation;

import java.util.List;

@Dao
public interface CorporationDao {

    @Insert
    void insertCorporation(Corporation corporation);

    @Query("SELECT * FROM corporation")
    List<Corporation> getAllCorporation();

    @Query("UPDATE corporation SET isactive = :isactive, start_date = :startDate" +
            ", end_date = :endDate WHERE ID = :id")
    void changeIsActive(int id, boolean isactive, Long startDate, Long endDate);

    @Query("UPDATE corporation SET isactive = :isactive")
    void changeAllIsActive(boolean isactive);

    @Query("SELECT * FROM corporation where isactive = :isactive")
    Corporation getCorporationActive(boolean isactive);

}
