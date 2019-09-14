package com.example.android.labakm.rest;

import com.example.android.labakm.entity.viewmodel.OrderViewModel;

import java.util.List;

public interface InquiryRest {
    int saveInquiry(OrderViewModel orderVM);
    List<OrderViewModel> getAllInquiries(int idToko);
    List<OrderViewModel> getAllInquiries();
}
