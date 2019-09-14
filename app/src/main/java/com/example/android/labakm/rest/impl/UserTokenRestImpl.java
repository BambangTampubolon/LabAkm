package com.example.android.labakm.rest.impl;

import android.os.AsyncTask;
import com.example.android.labakm.entity.ErrorDetail;
import com.example.android.labakm.entity.UserToken;
import com.example.android.labakm.entity.viewmodel.ErrorDetailListViewModel;
import com.example.android.labakm.entity.viewmodel.UserTokenListViewModel;
import com.example.android.labakm.rest.UserTokenRest;
import com.example.android.labakm.util.StaticVariable;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class UserTokenRestImpl implements UserTokenRest{

    @Override
    public boolean saveNewUser(UserToken entity) {
        boolean info = false;
        try {
            info = new saveUserAsync(entity).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return info;
    }

    @Override
    public boolean isExistUsername(String username) {
        Boolean isExist = false;
        try {
            isExist = new isExistUsernameAsync(username).execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExist;
    }

    @Override
    public List<UserToken> isValidLogin(String username, String password) {
        List<UserToken> listData = null;
        try {
            listData = new isValidLoginAsync(username,password).execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private static class isValidLoginAsync extends AsyncTask<Void,Void,List<UserToken>>{
        private String userName;
        private String password;

        public isValidLoginAsync(String userName, String password){
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected List<UserToken> doInBackground(Void... voids) {
            try {
                String url = StaticVariable.IS_VALID_LOGIN + "?username=" + userName + "&password=" +password;
                URL apiURL = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
                conn.setRequestProperty("Accept", "*/*");
                conn.setRequestProperty("username", userName);
                conn.setRequestProperty("password", password);
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    InputStream responseBody = conn.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    UserTokenListViewModel viewModel = gson.fromJson(responseBodyReader, UserTokenListViewModel.class);
                    List<UserToken> listData = (List<UserToken>) viewModel.getContent();
                    conn.disconnect();
                    responseBodyReader.close();
                    responseBody.close();
                    return listData;
                }
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_NO_CONTENT){
                    conn.disconnect();
                    return null;
                }

            }catch (Exception e){
                return null;
            }
            return null;
        }
    }

    private static class isExistUsernameAsync extends AsyncTask<Void,Void,Boolean>{
        private String userName;

        public isExistUsernameAsync(String userName){
            this.userName = userName;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String url = StaticVariable.IS_EXIST_USERNAME + "?username=" + userName;
                URL apiURL = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
                conn.setRequestProperty("Accept", "*/*");
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    conn.disconnect();
                    return true;
                }
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_NO_CONTENT){
                    conn.disconnect();
                    return false;
                }

            }catch (Exception e){
                return false;
            }
            return false;
        }
    }


    private static class saveUserAsync extends AsyncTask<Void,Void,Boolean> {
        private UserToken entity;

        public saveUserAsync(UserToken entity){
            this.entity = entity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL apiURL = new URL(StaticVariable.SAVE_USER);
                HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                String input = "{"
                        + "\"createdby\":\"" + entity.getCreatedby() + "\""
                        + ",\"createdbyname\":\"" + entity.getCreatedby() + "\""
                        + ",\"createdterminal\":\"" + entity.getCreatedby() + "\""
                        + ",\"email\":\"" + entity.getEmail() + "\""
                        + ",\"firebase_token\":\"" + entity.getFirebase_token() + "\""
                        + ",\"password\":" + entity.getPassword() + ""
                        + ",\"id\":" + entity.getId() + ""
                        + ",\"user_name\":\"" + entity.getUser_name() + "\""
                        + ",\"user_level\":" + entity.getUser_level() + ""
                        + "}";
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

                ErrorDetail info = new ErrorDetail();
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED){
//                    InputStream responseBody = conn.getInputStream();
//                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
//                    Gson gson = new Gson();
//                    ErrorDetailListViewModel response = gson.fromJson(responseBodyReader, ErrorDetailListViewModel.class);
                    conn.disconnect();
                    return true;
                }
                conn.disconnect();
                return false;
            }catch (Exception e){
                return false;
            }
        }
    }
}
