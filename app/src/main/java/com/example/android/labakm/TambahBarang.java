package com.example.android.labakm;

import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.android.labakm.Fragment.FragmentBarang;
import com.example.android.labakm.Interface.FragmentBarangInterface;
import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.ViewModel.TipeBarangRepoViewModel;
import com.example.android.labakm.entity.Barang;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.entity.viewmodel.TipeBarangViewModel;
import com.example.android.labakm.rest.BarangRest;
import com.example.android.labakm.rest.TipeBarangRest;
import com.example.android.labakm.rest.impl.BarangRestImpl;
import com.example.android.labakm.rest.impl.TipeBarangRestImpl;
import java.util.ArrayList;
import java.util.List;

public class TambahBarang extends AppCompatActivity implements FragmentPauseInterface, FragmentBarangInterface{
    Button selectButton, editButton, saveButton;
    Spinner spinnerTipeBarang;
    EditText editNamaBarang, editHargaBarang;
    BarangRest barangRest;
    TipeBarangRest tipeBarangRest;
    FragmentManager fragmentManager;
    FragmentBarang fragmentBarang;
    TipeBarangRepoViewModel tipeBarangRepoViewModel;
    BarangViewModel barangSelected;
    List<TipeBarangViewModel> tipeBarangList;
    ArrayAdapter<String> spinnerAdapter;
    TipeBarangViewModel tipeSelected;
    String[] arraySpinner;

    public void setTipeSelected(TipeBarangViewModel tipeSelected) {
        this.tipeSelected = tipeSelected;
    }

    public TipeBarangViewModel getTipeSelected() {
        return tipeSelected;
    }

    public void setTipeBarangList(List<TipeBarangViewModel> tipeBarangList) {
        this.tipeBarangList = tipeBarangList;
    }

    public List<TipeBarangViewModel> getTipeBarangList() {
        return tipeBarangList;
    }

    public void setBarangSelected(BarangViewModel barangSelected) {
        this.barangSelected = barangSelected;
    }

    public BarangViewModel getBarangSelected() {
        return barangSelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);
        selectButton = findViewById(R.id.button_pilih);
        editButton = findViewById(R.id.button_ubah);
        saveButton = findViewById(R.id.button_simpan);

        spinnerTipeBarang = findViewById(R.id.spinner_tipe_barang);
        editNamaBarang = findViewById(R.id.text_nama_selected);
        editHargaBarang = findViewById(R.id.text_harga_selected);

        barangRest = new BarangRestImpl();
        tipeBarangRest = new TipeBarangRestImpl();

        setTipeSpinnerList();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarangViewModel viewModel = getBarangSelected();
                Barang entity = new Barang();
                entity.setNama(editNamaBarang.getText().toString());
                entity.setId(viewModel.getId());
                entity.setHarga(Integer.valueOf(editHargaBarang.getText().toString()));
                entity.setTipe_barang_id(getTipeSelected().getId());
                entity.setCreatedby(viewModel.getCreatedby());
                barangRest.updateBarang(entity);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Barang entity = new Barang();
                entity.setTipe_barang_id(getTipeSelected().getId());
                entity.setNama(editNamaBarang.getText().toString());
                entity.setCreatedby("BENG");
                entity.setHarga(Integer.valueOf(editHargaBarang.getText().toString()));
                barangRest.saveBarang(entity);
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentBarang();
            }
        });

        spinnerTipeBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    setTipeSelected(tipeBarangList.get(i-1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void showFragmentBarang(){
        fragmentManager = getFragmentManager();
        fragmentBarang = (FragmentBarang) fragmentManager.findFragmentByTag("barang_fragment");
        if(null == fragmentBarang){
            fragmentBarang = new FragmentBarang();
        }
        if(!fragmentBarang.isVisible()){
            fragmentBarang.show(fragmentManager,"barang_fragment");
        }

    }

    @Override
    public void okButton() {
        this.fragmentBarang.dismiss();
    }

    private void setSelectedBarangToUI(BarangViewModel viewModel){
        editNamaBarang.setText(viewModel.getNama());
        editHargaBarang.setText(String.valueOf(viewModel.getHarga()));
        for(int i = 0; i < tipeBarangList.size(); i++){
            if(viewModel.getTipe_barang_id() == tipeBarangList.get(i).getId()){
                spinnerTipeBarang.setSelection(i+1);
            }
        }
    }


    @Override
    public void cancelButton() {
        this.fragmentBarang.dismiss();
    }

    public void setTipeSpinnerList(){
        tipeBarangRepoViewModel = ViewModelProviders.of(this).get(TipeBarangRepoViewModel.class);

        List<String> listSpinnerString = new ArrayList<>();
        arraySpinner = listSpinnerString.toArray(new String[0]);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, arraySpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipeBarang.setAdapter(spinnerAdapter);
        final Observer<List<TipeBarangViewModel>> tipeBarangObserver = new Observer<List<TipeBarangViewModel>>() {
            @Override
            public void onChanged(@Nullable List<TipeBarangViewModel> tipeBarangViewModels) {
                Log.i("checklist", "onChanged: " + tipeBarangViewModels.size());
                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("PILIH TIPE BARANG");
                for (TipeBarangViewModel vm : tipeBarangViewModels){
                    StringBuilder sb = new StringBuilder();
                    sb.append(vm.getId()).append(" - ").append(vm.getNama());
                    listSpinner.add(sb.toString());
                }
                arraySpinner = listSpinner.toArray(new String[0]);
                spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, arraySpinner);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinnerTipeBarang.setAdapter(spinnerAdapter);
                tipeBarangList = tipeBarangViewModels;
            }
        };
        tipeBarangRepoViewModel.getListLiveData().observe(this,tipeBarangObserver);

    }

    @Override
    public void selectItem(BarangViewModel barang) {
        setBarangSelected(barang);
        setSelectedBarangToUI(barang);
    }
}
