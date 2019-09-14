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
import android.widget.TextView;

import com.example.android.labakm.Adapter.InquiryDetailAdapter;
import com.example.android.labakm.Interface.InquiryDetailInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;

public class FragmentDetailInquiry extends DialogFragment{
    private TextView textOrderId, textOrderStatus, textOrderTanggal;
    private ListView listDetail;
    private InquiryDetailAdapter inquiryDetailAdapter;
    private OrderViewModel orderViewModel;
    private Button buttonClose;
    private InquiryDetailInterface inquiryDetailInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_inquiry, container, false);
        textOrderId = view.findViewById(R.id.text_order_id);
        textOrderStatus = view.findViewById(R.id.text_order_status);
        textOrderTanggal = view.findViewById(R.id.text_order_tanggal);
        buttonClose = view.findViewById(R.id.button_close);
        listDetail = view.findViewById(R.id.list_detail_item);

        Bundle bundle = getArguments();
        orderViewModel = new OrderViewModel();
        if(null != bundle){
            orderViewModel = (OrderViewModel) bundle.getSerializable("order_selected");
        }

        textOrderId.setText(String.valueOf(orderViewModel.getId()));
        textOrderStatus.setText(String.valueOf(orderViewModel.getStatus()));
        textOrderTanggal.setText(orderViewModel.getCreateddate());
        inquiryDetailAdapter = new InquiryDetailAdapter(orderViewModel.getItems(), getContext());
        listDetail.setAdapter(inquiryDetailAdapter);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inquiryDetailInterface.closeDialog();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inquiryDetailInterface = (InquiryDetailInterface) context;
    }
}
