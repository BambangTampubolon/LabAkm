package com.example.android.labakm.rest;

import com.example.android.labakm.entity.Barang;
import com.example.android.labakm.entity.ErrorDetail;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;

import java.util.List;
import java.util.Map;

public interface BarangRest {
    List<BarangViewModel> getAllBarang();
    ErrorDetail saveBarang(Barang barang);
    ErrorDetail updateBarang(Barang barang);
}
