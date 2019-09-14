package com.example.android.labakm.entity.viewmodel;

import com.example.android.labakm.Interface.IListViewModel;

import java.util.List;

public class OrderListViewModel extends ListViewModel implements IListViewModel {
    public List<OrderViewModel> content;

    public OrderListViewModel(List<OrderViewModel> content){
        this.content = content;
    }

    @Override
    public Object getContent() {
        return content;
    }
}
