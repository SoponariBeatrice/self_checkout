package com.example.selfcheckout;

import com.example.selfcheckout.data.BillData;
import com.example.selfcheckout.data.ProductData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillService {
    List<BillData> bills = new ArrayList<>();
    public void addBill(BillData billData)
    {
        JsonPlaceHolderApi api = RetrofitUser.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        Call<BillData> call = api.addBill(billData);
        Response<BillData> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BillData> getAllBills(Long idUser)
    {
        JsonPlaceHolderApi api = RetrofitUser.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        Call<List<BillData>> call = api.getAllBills(idUser);
        Response<List<BillData>> response = null;
        try {
            response = call.execute();
            bills = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        call.enqueue(new Callback<List<BillData>>() {
//            @Override
//            public void onResponse(Call<List<BillData>> call, Response<List<BillData>> response) {
//                bills = response.body();
//                System.out.println("Bill from service = " + bills.get(0).getProductNames());
//            }
//
//            @Override
//            public void onFailure(Call<List<BillData>> call, Throwable t) {
//                System.out.println("FAILURE!!!!");
//            }
//        });
        return bills;
    }
}
