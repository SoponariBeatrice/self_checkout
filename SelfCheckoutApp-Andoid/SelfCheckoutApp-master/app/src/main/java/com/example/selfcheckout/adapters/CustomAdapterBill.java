package com.example.selfcheckout.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.selfcheckout.ChartItem;
import com.example.selfcheckout.CustomAdapterForItems;
import com.example.selfcheckout.PaymentConfig.CheckoutActivity;
import com.example.selfcheckout.R;
import com.example.selfcheckout.activities.ScanProductsActivity;
import com.example.selfcheckout.data.BillData;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterBill  extends ArrayAdapter<BillData> implements View.OnClickListener{
   public static BillData bill;
    private static class ViewHolder {
        TextView supermarketName;
        TextView dateOfPurchase;
        TextView totalPaid;
    }
    public CustomAdapterBill(@NonNull Context context, List<BillData> bills) {
        super(context, R.layout.row_for_bill, bills);
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BillData bill = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_for_bill, parent, false);
            viewHolder.supermarketName = (TextView) convertView.findViewById(R.id.superMarketName);
            viewHolder.dateOfPurchase = (TextView) convertView.findViewById(R.id.dateOfPurchase);
            viewHolder.totalPaid = (TextView) convertView.findViewById(R.id.totalPaid);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.supermarketName.setText(bill.getSupermarket());
        viewHolder.totalPaid.setText(bill.getTotal() + " Lei");
        viewHolder.dateOfPurchase.setText(bill.getDate().split("AD")[0]);
        return convertView;
    }
}
