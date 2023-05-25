package com.example.selfcheckout2.controller;

import com.example.selfcheckout2.data.BillData;
import com.example.selfcheckout2.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
@RestController
public class BillController {
    @Autowired
    BillService billService;

    @PostMapping("/bill")
    public void addBill(@RequestBody BillData billData)
    {
        billService.saveBill(billData);
    }

    @GetMapping("/get-all-bills/{idUser}")
    public List<BillData> getAllBillsByUser(@PathVariable("idUser") Long idUser)
    {
        return billService.getAllBillsByUserId(idUser);
    }

    @GetMapping("/bills")
    public List<BillData> getAllBills()
    {
       return billService.getAllBills();
    }
}
