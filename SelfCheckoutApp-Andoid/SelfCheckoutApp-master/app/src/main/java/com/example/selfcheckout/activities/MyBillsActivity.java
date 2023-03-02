package com.example.selfcheckout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.selfcheckout.BillService;
import com.example.selfcheckout.PaymentConfig.CheckoutActivity;
import com.example.selfcheckout.R;
import com.example.selfcheckout.adapters.CustomAdapterBill;
import com.example.selfcheckout.data.BillData;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MyBillsActivity extends AppCompatActivity {
    List<BillData> bills;
    BillService billService = new BillService();
    private static CustomAdapterBill adapter;
    ListView listView;
    public static BillData currentBill = new BillData();
    Button sortButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        listView = (ListView) findViewById(R.id.listItems);
        sortButton = findViewById(R.id.sortButton);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            getBills();
        }

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.reverse(bills);
                adapter = new CustomAdapterBill(getApplicationContext(), bills);
                listView.setAdapter(adapter);

            }
        });
        adapter = new CustomAdapterBill(getApplicationContext(), bills);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyBillsActivity.this, FullBillActivity.class);
                currentBill = bills.get(i);
                startActivity(intent);
            }
        });

    }
    public void getBills(){
        bills = billService.getAllBills(LoginActivity.idUser);
        for (BillData b:bills
        ) {
            System.out.println("Bill = " + b.getProductNames());
            System.out.println(b.getPrices());
            System.out.println(b.getQuantities());
            System.out.println(b.getSupermarket());
            System.out.println(b.getTotal());
            System.out.println(b.getDate());
        }
    }
}
