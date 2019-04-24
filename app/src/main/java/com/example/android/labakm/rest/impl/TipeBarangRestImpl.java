package com.example.android.labakm.rest.impl;

import android.os.AsyncTask;

import com.example.android.labakm.entity.viewmodel.TipeBarangListViewModel;
import com.example.android.labakm.entity.viewmodel.TipeBarangViewModel;
import com.example.android.labakm.rest.TipeBarangRest;
import com.example.android.labakm.util.StaticVariable;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class TipeBarangRestImpl implements TipeBarangRest {
    @Override
    public List<TipeBarangViewModel> getAllTipeBarang() {
        try {
            return new getAllTipeBarang().execute().get();
        } catch (InterruptedException e) {
            return null;
        } catch (ExecutionException e) {
            return null;
        }
    }

//    @Override
//    public MutableLiveData<List<TipeBarangViewModel>> getAllTipeBarangLive() {
//        try {
//            return new getAllTipeBarang().execute().get();
//        } catch (InterruptedException e) {
//            return null;
//        } catch (ExecutionException e) {
//            return null;
//        }
//    }

//    public static class getAllTipeBarangLive extends AsyncTask<Void, Void, MutableLiveData<List<TipeBarangViewModel>>>{
//        @Override
//        protected MutableLiveData<List<TipeBarangViewModel>> doInBackground(Void... voids) {
//            try {
//                URL apiURL = new URL("http://192.168.1.3:8081/basar.api/v1/TipeBarangs");
//                HttpURLConnection myConnection = (HttpURLConnection) apiURL.openConnection();
//                myConnection.setRequestProperty("Accept", "*/*");
//                myConnection.setRequestProperty("page","1");
//                myConnection.setRequestProperty("size","10");
//                myConnection.setRequestProperty("orderby","id");
//                myConnection.setRequestProperty("query","id;greater;0");
//                if(myConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
//                    InputStream responseBody = myConnection.getInputStream();
//                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
//                    Gson gson = new Gson();
//                    TipeBarangListViewModel listData = gson.fromJson(responseBodyReader, TipeBarangListViewModel.class);
//                    List<TipeBarangViewModel> listBarang = (List<TipeBarangViewModel>) listData.getContent();
//                    return listBarang;
//                }else {
//                    return null;
//                }
//            }catch (Exception e){
//                return  null;
//            }
//        }
//    }

    public static class getAllTipeBarang extends AsyncTask<Void,Void,List<TipeBarangViewModel>>{

        @Override
        protected List<TipeBarangViewModel> doInBackground(Void... voids) {
            try {
                URL apiURL = new URL(StaticVariable.GET_ALL_TIPE_BARANG);
                HttpURLConnection myConnection = (HttpURLConnection) apiURL.openConnection();
                myConnection.setRequestProperty("Accept", "*/*");
                myConnection.setRequestProperty("page","1");
                myConnection.setRequestProperty("size","10");
                myConnection.setRequestProperty("orderby","id");
                myConnection.setRequestProperty("query","id;greater;0");
                if(myConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    TipeBarangListViewModel listData = gson.fromJson(responseBodyReader, TipeBarangListViewModel.class);
                    List<TipeBarangViewModel> listBarang = (List<TipeBarangViewModel>) listData.getContent();
                    return listBarang;
                }else {
                    return null;
                }
            }catch (Exception e){
                return  null;
            }
        }

        @Override
        protected void onPostExecute(List<TipeBarangViewModel> tipeBarangViewModels) {
            super.onPostExecute(tipeBarangViewModels);
        }
    }
}
