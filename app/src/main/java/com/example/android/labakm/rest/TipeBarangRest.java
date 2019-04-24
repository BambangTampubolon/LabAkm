package com.example.android.labakm.rest;

import android.arch.lifecycle.MutableLiveData;

import com.example.android.labakm.entity.viewmodel.TipeBarangViewModel;

import java.util.List;

public interface TipeBarangRest {
    List<TipeBarangViewModel> getAllTipeBarang();
//    MutableLiveData<List<TipeBarangViewModel>> getAllTipeBarangLive();
}
