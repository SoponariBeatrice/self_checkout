package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Bill;
import com.example.selfcheckout2.data.BillData;
import com.example.selfcheckout2.repository.IBillRepository;
import com.example.selfcheckout2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService{
    @Autowired
    IBillRepository billRepository;

    @Override
    public BillData saveBill(BillData bill) {
        Bill newBill = new Bill(bill.getProductNames(), bill.getPrices(), bill.getQuantities(), bill.getIdUser(), bill.getTotal(), bill.getDate(), bill.getSupermarket());
        billRepository.save(newBill);
        return bill;
    }

    @Override
    public BillData getBillById(Long billId) {
        Bill bill = billRepository.getById(billId);
        return new BillData(bill.getProductNames(), bill.getPrices(), bill.getQuantities(), bill.getIdUser(), bill.getTotal(), bill.getDate(), bill.getSupermarket());
    }

    @Override
    public List<BillData> getAllBills(Long idUser) {
        List<Bill> bills = billRepository.findAll().stream().filter(b -> b.getIdUser().equals(idUser))
         .collect(Collectors.toList());
        List<BillData> billData = new ArrayList<>();
        for (Bill bill: bills
             ) {
            billData.add(new BillData(bill.getProductNames(), bill.getPrices(), bill.getQuantities(), bill.getIdUser(), bill.getTotal(), bill.getDate(), bill.getSupermarket()));
        }
        return billData;
    }
}
