package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.labakm.Adapter.ProdukAdapter;
import com.example.android.labakm.Interface.ProdukInquiryInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.rest.BarangRest;
import com.example.android.labakm.rest.impl.BarangRestImpl;

import java.util.List;

public class FragmentItemByTypeLayout extends Fragment{
    private ListView listViewItem;
    private List<BarangViewModel> listItem;
    private BarangRest barangRest;
    private ProdukAdapter produkAdapter;
    private ProdukInquiryInterface produkInquiryInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_by_type_layout, container, false);
        listViewItem = view.findViewById(R.id.list_item_by_type);
        produkAdapter = new ProdukAdapter(listItem, getContext(), produkInquiryInterface);
        barangRest = new BarangRestImpl();
        listItem = barangRest.getAllBarang();
        listViewItem.setAdapter(produkAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.produkInquiryInterface = (ProdukInquiryInterface) context;
    }
}
