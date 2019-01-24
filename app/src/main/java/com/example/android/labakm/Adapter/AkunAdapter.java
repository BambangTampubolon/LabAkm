package com.example.android.labakm.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.labakm.R;
import com.example.android.labakm.entity.Akun;

import java.util.List;

public class AkunAdapter extends BaseAdapter {
    private List<Akun> akunList;
    private Context context;

    public AkunAdapter(List<Akun> akunList, Context context){
        this.akunList = akunList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return akunList.size();
    }

    @Override
    public Akun getItem(int i) {
        return akunList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return akunList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.list_akun_item, viewGroup, false);
        Log.i("checkdataakunlist", "onCreateView: " + akunList.get(i));
        TextView textKodeAkun = view.findViewById(R.id.list_akun_kode);
        TextView textNamaAkun = view.findViewById(R.id.list_akun_nama);
        textKodeAkun.setText(akunList.get(i).getKode());
        textNamaAkun.setText(akunList.get(i).getName());
        return view;
    }
}
