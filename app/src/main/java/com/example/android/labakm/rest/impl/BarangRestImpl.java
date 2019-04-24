package com.example.android.labakm.rest.impl;

import android.os.AsyncTask;
import android.util.Log;
import com.example.android.labakm.entity.Barang;
import com.example.android.labakm.entity.ErrorDetail;
import com.example.android.labakm.entity.viewmodel.BarangListViewModel;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.entity.viewmodel.ErrorDetailListViewModel;
import com.example.android.labakm.rest.BarangRest;
import com.example.android.labakm.util.StaticVariable;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.net.ssl.HttpsURLConnection;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class BarangRestImpl implements BarangRest{
    @Override
    public ErrorDetail saveBarang(final Barang barang) {
        ErrorDetail info = new ErrorDetail();
        try {
            info = new saveBarang(barang).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return info;
    }

    @Override
    public ErrorDetail updateBarang(Barang barang) {
        return null;
    }

    @Override
    public List<BarangViewModel> getAllBarang() {
        List<BarangViewModel> listReturnBarangs;
        try {
            listReturnBarangs = new getAllBarang().execute().get();
        } catch (InterruptedException e) {
            listReturnBarangs = new ArrayList<>();
            e.printStackTrace();
        } catch (ExecutionException e) {
            listReturnBarangs = new ArrayList<>();
            e.printStackTrace();
        }
        return listReturnBarangs;
    }

    private static class getAllBarang extends AsyncTask<Void,Void,List<BarangViewModel>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<BarangViewModel> barangs) {
            super.onPostExecute(barangs);
        }

        @Override
        protected List<BarangViewModel> doInBackground(Void... voids) {
            try {
                URL apiURL = new URL(StaticVariable.GET_ALL_BARANG);
                HttpURLConnection myConnection = (HttpURLConnection) apiURL.openConnection();
                myConnection.setRequestProperty("Accept", "*/*");
                myConnection.setRequestProperty("page", String.valueOf(1));
                myConnection.setRequestProperty("size", String.valueOf(10));
                myConnection.setRequestProperty("orderby","id");
                myConnection.setRequestProperty("query", "id;greater;0");
                if(myConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    Log.i("check", "doInBackground1: ");
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    BarangListViewModel listData = gson.fromJson(responseBodyReader, BarangListViewModel.class);
                    Log.i("check", "doInBackground2: ");
                    List<BarangViewModel> listBarang = (List<BarangViewModel>) listData.getContent();
                    Log.i("check", "doInBackground3: ");
                    return listBarang;
                }else {
                    Log.i("check", "doInBackground4: ");
                    return null;
                }
            }catch (Exception e){
                Log.i("check", "doInBackground5: " + e.toString());
                return  null;
            }
        }
    }

    private static class saveBarang extends AsyncTask<Void,Void,ErrorDetail>{
        private Barang barang;

        public saveBarang(Barang barang){
            this.barang = barang;
        }

        @Override
        protected void onPostExecute(ErrorDetail errorDetail) {
            super.onPostExecute(errorDetail);
        }

        @Override
        protected ErrorDetail doInBackground(Void... voids) {
            try {
                URL apiURL = new URL(StaticVariable.SAVE_BARANG);
                HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                String input = "{"
                        + "\"createdby\":\"" + barang.getCreatedby() + "\""
                        + ",\"createdbyname\":\"" + barang.getCreatedby() + "\""
                        + ",\"createddate\":\"" + barang.getCreateddate() + "\""
                        + ",\"createdterminal\":\"" + barang.getCreatedby() + "\""
                        + ",\"harga\": " + barang.getHarga() + ""
                        + ",\"id\":" + barang.getId() + ""
                        + ",\"nama\":\"" + barang.getNama() + "\""
                        + ",\"tipe_barang_id\":" + barang.getTipe_barang_id() + ""
                        + "}";
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

                ErrorDetail info = new ErrorDetail();
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED){
                    InputStream responseBody = conn.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    ErrorDetailListViewModel response = gson.fromJson(responseBodyReader, ErrorDetailListViewModel.class);
                    info = response.getContent();
                }
                conn.disconnect();
                return info;
            }catch (Exception e){
                return null;
            }
        }
    }

    private static class updateBarang extends AsyncTask<Void,Void,ErrorDetail>{
        private Barang barang;

        public updateBarang(Barang barang){
            this.barang = barang;
        }

        @Override
        protected void onPostExecute(ErrorDetail errorDetail) {
            super.onPostExecute(errorDetail);
        }

        @Override
        protected ErrorDetail doInBackground(Void... voids) {
            try {
                URL apiURL = new URL(StaticVariable.UPDATE_BARANG);
                HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                String input = "{"
                        + "\"createdby\":\"" + barang.getCreatedby() + "\""
                        + ",\"createdbyname\":\"" + barang.getCreatedby() + "\""
                        + ",\"createddate\":\"" + barang.getCreateddate() + "\""
                        + ",\"createdterminal\":\"" + barang.getCreatedby() + "\""
                        + ",\"harga\": " + barang.getHarga() + ""
                        + ",\"id\":" + barang.getId() + ""
                        + ",\"nama\":\"" + barang.getNama() + "\""
                        + ",\"tipe_barang_id\":" + barang.getTipe_barang_id() + ""
                        + "}";
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

                ErrorDetail info = new ErrorDetail();
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED){
                    InputStream responseBody = conn.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    Gson gson = new Gson();
                    ErrorDetailListViewModel response = gson.fromJson(responseBodyReader, ErrorDetailListViewModel.class);
                    info = response.getContent();
                }
                conn.disconnect();
                return info;
            }catch (Exception e){
                return null;
            }
        }
    }
}
