package com.example.android.labakm.Fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.labakm.Adapter.BarangAdapter;
import com.example.android.labakm.Interface.FragmentBarangInterface;
import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.rest.BarangRest;
import com.example.android.labakm.rest.impl.BarangRestImpl;

import java.util.List;

public class FragmentBarang extends DialogFragment implements FragmentBarangInterface{
    private FragmentPauseInterface fragmentPauseInterface;
    private FragmentBarangInterface fragmentBarangInterface;
    private BarangRest barangRest;
    private BarangViewModel selectedItem;

    public void setSelectedItem(BarangViewModel selectedItem) {
        this.selectedItem = selectedItem;
    }

    public BarangViewModel getSelectedItem() {
        return selectedItem;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barang, container, false);
        ListView listItem = view.findViewById(R.id.list_view_barang);
        Button okButton = view.findViewById(R.id.ok_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);

        barangRest = new BarangRestImpl();
        List<BarangViewModel> barangs = barangRest.getAllBarang();
        BarangAdapter adapter = new BarangAdapter(barangs, getContext(), fragmentBarangInterface);
        listItem.setAdapter(adapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentPauseInterface.okButton();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentPauseInterface.cancelButton();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        fragmentPauseInterface = (FragmentPauseInterface) context;
        fragmentBarangInterface = (FragmentBarangInterface) context;
        super.onAttach(context);
    }

    @Override
    public void selectItem(BarangViewModel barang) {
        setSelectedItem(barang);
    }

}
