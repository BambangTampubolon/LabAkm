package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.labakm.R;
import com.example.android.labakm.entity.Corporation;

import java.text.DateFormat;
import java.util.Date;

public class FragmentIdentitas extends Fragment {
    private TextView contentNama, contentAwal, contentAkhir, contentAlamat;
    private Corporation corporationIntent;
    final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private Date awal, akhir;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identitas, container, false);
        contentNama = view.findViewById(R.id.text_name_corp);
        contentAwal = view.findViewById(R.id.text_periode_awal);
        contentAkhir = view.findViewById(R.id.text_periode_akhir);
        contentAlamat = view.findViewById(R.id.text_alamat);

        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            awal = new Date(bundle.getLong("awal", 0));
            akhir = new Date(bundle.getLong("akhir", 0));
        }
        contentNama.setText(corporationIntent.getName());
        contentAlamat.setText(corporationIntent.getAddress());
        contentAwal.setText(dateFormat.format(awal));
        contentAkhir.setText(dateFormat.format(akhir));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
