package com.example.android.labakm.Interface;

import com.example.android.labakm.entity.Akun;

import java.util.List;

public interface AkunManager {

    public long insertAkun(Akun akun) throws Exception;

    public List<Akun> getAllAkun(int idCorporation) throws Exception;

}
