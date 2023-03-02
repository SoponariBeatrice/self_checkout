package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.BillData;
import com.example.selfcheckout2.data.ProductData;

import java.util.List;

public interface IBillService {
    BillData saveBill(BillData bill);
    BillData getBillById(final Long billId);
    List<BillData> getAllBills(Long idUser);
}
