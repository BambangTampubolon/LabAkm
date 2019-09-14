package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.labakm.Adapter.InquiryAdapter;
import com.example.android.labakm.Interface.InquiryDetailInterface;
import com.example.android.labakm.Interface.InquiryInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;
import com.example.android.labakm.rest.InquiryRest;
import com.example.android.labakm.rest.impl.InquiryRestImpl;

import java.util.List;

public class FragmentHistoryInquiry extends Fragment{
    private ListView listHistory;
    private InquiryAdapter inquiryAdapter;
    private List<OrderViewModel> listOrder = null;
    private InquiryRest inquiryRest;
    private InquiryInterface inquiryInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_inquiry, container, false);
        listHistory = view.findViewById(R.id.list_history);
        inquiryRest = new InquiryRestImpl();
        int idToko = 0;
        Bundle bundle = getArguments();
        if(null != bundle){
            idToko = bundle.getInt("id_toko");
        }
        listOrder = inquiryRest.getAllInquiries(idToko);
        inquiryAdapter = new InquiryAdapter(listOrder, getContext(), inquiryInterface);
        listHistory.setAdapter(inquiryAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inquiryInterface = (InquiryInterface) context;
    }
}
