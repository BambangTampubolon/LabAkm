package com.example.android.labakm.entity.viewmodel;

import com.example.android.labakm.Interface.IListViewModel;
import com.example.android.labakm.entity.UserToken;

import java.util.List;

public class UserTokenListViewModel extends ListViewModel implements IListViewModel {
    private List<UserToken> content;

    public UserTokenListViewModel(List<UserToken> content){
        this.content = content;
    }

    @Override
    public Object getContent() {
        return content;
    }
}
