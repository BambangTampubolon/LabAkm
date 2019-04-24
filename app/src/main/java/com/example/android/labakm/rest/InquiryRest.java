package com.example.android.labakm.rest;

import com.example.android.labakm.entity.ErrorDetail;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;

public interface InquiryRest {
    ErrorDetail saveInquiry(OrderViewModel orderVM);
}
