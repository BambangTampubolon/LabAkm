package com.example.android.labakm.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.labakm.entity.viewmodel.TipeBarangViewModel;
import com.example.android.labakm.rest.TipeBarangRest;
import com.example.android.labakm.rest.impl.TipeBarangRestImpl;

import java.util.List;

public class TipeBarangRepoViewModel extends ViewModel {
    private MutableLiveData<List<TipeBarangViewModel>> listLiveData;
    private TipeBarangRest tipeBarangRest;

    public LiveData<List<TipeBarangViewModel>> getListLiveData() {
        Log.i("fetching", "getListLiveData: ");
        this.tipeBarangRest = new TipeBarangRestImpl();
        if(null == listLiveData){
            listLiveData = new MutableLiveData<>();
        }
        fetchLiveData();
        return listLiveData;
    }

    public void fetchLiveData(){
        Log.i("fetchagain", "fetchLiveData: ");
//        listLiveData.setValue(tipeBarangRest.getAllTipeBarang());
        List<TipeBarangViewModel> listTipeBarangVM = tipeBarangRest.getAllTipeBarang();
        listLiveData.postValue(listTipeBarangVM);
    }
}
