package com.example.android.labakm.rest;
import com.example.android.labakm.entity.UserToken;

import java.util.List;

public interface UserTokenRest {
    boolean saveNewUser(UserToken entity);
    boolean isExistUsername(String username);
    List<UserToken> isValidLogin(String username, String password);
}
