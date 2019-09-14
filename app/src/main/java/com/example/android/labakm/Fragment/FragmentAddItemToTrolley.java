package com.example.android.labakm.Fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.labakm.Interface.FragmentAddItemtoTrolleyInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;

public class FragmentAddItemToTrolley extends DialogFragment {
    private TextView textItemName;
    private TextInputEditText editItemCount;
    private Button buttonAdd, buttonCancel;
    private BarangViewModel barang;
    private FragmentAddItemtoTrolleyInterface fragmentAddItemtoTrolleyInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_trolley, container, false);
        Bundle bundle = getArguments();
        barang = (BarangViewModel) bundle.getSerializable("item_selected");
        textItemName = view.findViewById(R.id.view_item_name);
        editItemCount = view.findViewById(R.id.edit_number_of_item);
        buttonAdd = view.findViewById(R.id.button_add);
        buttonCancel = view.findViewById(R.id.button_cancel);
        textItemName.setText(barang.getNama());


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barang.setJumlah(Integer.valueOf(editItemCount.getText().toString()));
                fragmentAddItemtoTrolleyInterface.addItemToCart(barang);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentAddItemtoTrolleyInterface.cancel();
            }
        });
        return  view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentAddItemtoTrolleyInterface = (FragmentAddItemtoTrolleyInterface) context;
    }
}
