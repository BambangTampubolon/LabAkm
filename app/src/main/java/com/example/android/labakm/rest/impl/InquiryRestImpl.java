package com.example.android.labakm.rest.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.labakm.entity.ErrorDetail;
import com.example.android.labakm.entity.viewmodel.ErrorDetailListViewModel;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;
import com.example.android.labakm.rest.InquiryRest;
import com.example.android.labakm.util.StaticVariable;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class InquiryRestImpl implements InquiryRest {

    @Override
    public ErrorDetail saveInquiry(OrderViewModel orderVM) {
        ErrorDetail info = new ErrorDetail();
        try {
            info = new saveInquiryAsync(orderVM).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return info;
    }

    private static class saveInquiryAsync extends AsyncTask<Void,Void,ErrorDetail>{
        private OrderViewModel listItem;

        public saveInquiryAsync(OrderViewModel listItem){
            this.listItem = listItem;
        }

        @Override
        protected ErrorDetail doInBackground(Void... voids) {
            try {
                URL apiUrl = new URL(StaticVariable.SAVE_INQUIRY);
                HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                Gson gson = new Gson();
                String json = gson.toJson(listItem);
                Log.i("checkVM", "doInBackground: " + listItem.toString());
                Log.i("checkjson", "doInBackground: " +  json);
                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                ErrorDetail info = new ErrorDetail();
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED){
                    InputStream responseBody = conn.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    ErrorDetailListViewModel response = gson.fromJson(responseBodyReader, ErrorDetailListViewModel.class);
                    info = response.getContent();
                }
                conn.disconnect();
                return info;
            } catch (Exception e) {
                return null;
            }

        }
    }

}
