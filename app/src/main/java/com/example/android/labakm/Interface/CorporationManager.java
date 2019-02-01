package com.example.android.labakm.Interface;

import com.example.android.labakm.entity.Corporation;

import java.util.List;

public interface CorporationManager {

    public long insertCorporation(Corporation corporation);

    List<Corporation> getAllCorporation();

    void changeIsActive(int id, boolean isactive, Long startDate, Long endDate);

    void changeAllIsActive(boolean isactive);

    Corporation getCorporationActive(boolean isactive);

}
