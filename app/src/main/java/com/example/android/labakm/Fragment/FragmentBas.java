package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.labakm.Adapter.AkunAdapter;
import com.example.android.labakm.Interface.AkunManager;
import com.example.android.labakm.R;
import com.example.android.labakm.dao.AkunDao;
import com.example.android.labakm.entity.Akun;
import com.example.android.labakm.entity.Corporation;
import com.example.android.labakm.manager.AkunManagerImpl;
import com.example.android.labakm.setting.DatabaseSetting;

import java.util.Date;
import java.util.List;

public class FragmentBas extends Fragment {
    private ListView listViewAkun;
    private AkunAdapter akunAdapter;
    private List<Akun> akunList;
    private AkunDao akunDao;
    private Date startDate, endDate;
    private Corporation corporationIntent;
    private AkunManager akunManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bas, container, false);
        listViewAkun = view.findViewById(R.id.list_akun);
        akunDao = DatabaseSetting.getDatabase(getContext()).akunDao();
        akunManager = new AkunManagerImpl(akunDao, getContext());
        Bundle bundle = getArguments();
        if(null != bundle){
            corporationIntent = (Corporation) bundle.getSerializable("idSelected");
            startDate = new Date(bundle.getLong("awal", 0));
            endDate = new Date(bundle.getLong("akhir", 0));
        }
        try {
            akunList = akunManager.getAllAkun(corporationIntent.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        akunAdapter = new AkunAdapter(akunList, getContext());
        listViewAkun.setAdapter(akunAdapter);
        return view;
    }


}
