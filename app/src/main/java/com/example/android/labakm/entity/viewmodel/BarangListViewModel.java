package com.example.android.labakm.entity.viewmodel;

import com.example.android.labakm.Interface.IListViewModel;
import com.example.android.labakm.entity.Barang;

import java.util.List;

public class BarangListViewModel extends ListViewModel implements IListViewModel{

    private List<BarangViewModel> content;

    public BarangListViewModel(List<BarangViewModel> content){
        this.content = content;
    }

    @Override
    public Object getContent() {
        return content;
    }
}
