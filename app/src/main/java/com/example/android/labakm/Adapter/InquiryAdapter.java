package com.example.android.labakm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.labakm.Interface.InquiryInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;
import com.example.android.labakm.util.StaticVariable;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InquiryAdapter extends BaseAdapter {
    private List<OrderViewModel> orderList;
    private Context context;
    private InquiryInterface inquiryInterface;

    public InquiryAdapter(List<OrderViewModel> orderList, Context context
            , InquiryInterface inquiryInterface){
        this.orderList = orderList;
        this.context = context;
        this.inquiryInterface = inquiryInterface;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return orderList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.inquiry_item_list, viewGroup, false);
        TextView viewOrderId = view.findViewById(R.id.text_id_inquiry);
        TextView viewOrderStatus = view.findViewById(R.id.text_status_inquiry);
        TextView textTanggalPesan = view.findViewById(R.id.text_tanggal_pemesanan);
        TextView textJamPesan = view.findViewById(R.id.text_jam_pemesanan);
        Button buttonDetail = view.findViewById(R.id.button_detail);
        OrderViewModel order = orderList.get(i);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(order.getCreateddate(), formatter);
        textTanggalPesan.setText(String.valueOf(localDateTime.format(formatter2)));
        textJamPesan.setText(String.valueOf(localDateTime.format(formatter3)));
        viewOrderId.setText(String.valueOf(order.getId()));
        switch (order.getStatus()){
            case 0:
                viewOrderStatus.setText("TRANSAKSI BELUM DIPROSES");
                break;
            case 1:
                viewOrderStatus.setText("TRANSAKSI SUDAH DIPROSES");
                break;
            case 2:
                viewOrderStatus.setText("TRANSAKSI SUKSES");
                break;
            case 3:
                viewOrderStatus.setText("TRANSAKSI GAGAL");
                break;
                default:
                    break;
        }

        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inquiryInterface.checkInquiry(orderList.get(i));
            }
        });
        return view;
    }

}
