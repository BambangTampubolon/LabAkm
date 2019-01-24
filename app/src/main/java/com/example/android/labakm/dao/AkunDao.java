package com.example.android.labakm.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.labakm.entity.Akun;

import java.util.List;

@Dao
public interface AkunDao {

    @Insert
    void insert(Akun akun);

    @Query("SELECT * FROM akun where id_corporation = :idCorporation")
    List<Akun> getAllAkun(int idCorporation);

    @Query("SELECT * FROM akun where id = :id")
    List<Akun> getAllAkunById(int id);

}
