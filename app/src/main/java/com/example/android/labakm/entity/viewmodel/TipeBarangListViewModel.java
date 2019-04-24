package com.example.android.labakm.entity.viewmodel;

import com.example.android.labakm.Interface.IListViewModel;

import java.util.List;

public class TipeBarangListViewModel extends ListViewModel implements IListViewModel {
    public List<TipeBarangViewModel> content;

    public TipeBarangListViewModel(List<TipeBarangViewModel> content){
        this.content = content;
    }

    @Override
    public Object getContent() {
        return content;
    }
}
