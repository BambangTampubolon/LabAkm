package com.example.android.labakm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.android.labakm.Adapter.BarangAdapter;
import com.example.android.labakm.Adapter.ProdukAdapter;
import com.example.android.labakm.Adapter.TrolleyAdapter;
import com.example.android.labakm.Interface.ProdukInquiryInterface;
import com.example.android.labakm.Interface.TrolleyItemInterface;
import com.example.android.labakm.entity.ErrorDetail;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.entity.viewmodel.OrderDetailViewModel;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;
import com.example.android.labakm.rest.BarangRest;
import com.example.android.labakm.rest.InquiryRest;
import com.example.android.labakm.rest.impl.BarangRestImpl;
import com.example.android.labakm.rest.impl.InquiryRestImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InquiryActivity extends AppCompatActivity implements ProdukInquiryInterface, TrolleyItemInterface{
    private ListView listViewBarang, listViewOrder;
    private ProdukAdapter barangAdapter;
    private TrolleyAdapter trolleyAdapter;
    private Button saveButton;
    private BarangRest barangRest;
    private InquiryRest inquiryRest;
    List<BarangViewModel> listBarangDB, listBarangOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        listViewBarang = findViewById(R.id.barang_list_view);
        listViewOrder = findViewById(R.id.order_list_view);
        saveButton = findViewById(R.id.save_button);
        barangRest = new BarangRestImpl();
        inquiryRest = new InquiryRestImpl();
        listBarangOrder = new ArrayList<>();
        listBarangDB = barangRest.getAllBarang();
        barangAdapter = new ProdukAdapter(listBarangDB, this, this);
        trolleyAdapter = new TrolleyAdapter(listBarangOrder, this, this);
        listViewBarang.setAdapter(barangAdapter);
        listViewOrder.setAdapter(trolleyAdapter);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inquiryRest.saveInquiry(setOrderJson(listBarangOrder));
            }
        });
    }

    private OrderViewModel setOrderJson(List<BarangViewModel> orderViewModel){
        OrderViewModel orderVM = new OrderViewModel();
        orderVM.setId(0);
        orderVM.setCreatedby("bengbeng");
        orderVM.setCreatedbyname("bengbeng");
        orderVM.setCreatedterminal("bengbeng");
        orderVM.setId_toko(1);
        orderVM.setNama_toko("bengbeng's");
        orderVM.setStatus(0);
        orderVM.setTotal_harga(0);
        orderVM.setItems(setOrderDetailJSON(orderViewModel));
        return orderVM;
    }

    private List<OrderDetailViewModel> setOrderDetailJSON(List<BarangViewModel> barangsViewModel){
        List<OrderDetailViewModel> listOrderDetailVM = new ArrayList<>();
        for (BarangViewModel barangVM : barangsViewModel){
            OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
            orderDetailViewModel.setId_barang(barangVM.getId());
            orderDetailViewModel.setNama_barang(barangVM.getNama());
            orderDetailViewModel.setId(0);
            orderDetailViewModel.setId_order(0);
            orderDetailViewModel.setCreatedby("bengbeng");
            orderDetailViewModel.setCreatedbyname("bengbeng");
            orderDetailViewModel.setCreatedterminal("bengbeng");
            orderDetailViewModel.setTotal_barang(barangVM.getJumlah());
            listOrderDetailVM.add(orderDetailViewModel);
        }
        return listOrderDetailVM;
    }
    @Override
    public void saveBarangtoProduk(BarangViewModel barangViewModel) {
        listBarangOrder.add(barangViewModel);
        trolleyAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItem(BarangViewModel barangViewModel) {
        listBarangOrder.remove(barangViewModel);
        trolleyAdapter.notifyDataSetChanged();
    }
}
