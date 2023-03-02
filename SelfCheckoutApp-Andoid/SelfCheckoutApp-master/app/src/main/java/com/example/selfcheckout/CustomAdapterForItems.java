package com.example.selfcheckout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfcheckout.activities.ScanProductsActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CustomAdapterForItems extends ArrayAdapter<ChartItem> implements View.OnClickListener {
    private ArrayList<ChartItem> dataSet;
    Context mContext;
    private int price;
    Boolean flag = false;
    public static float[] prices;
    private static class ViewHolder {
        TextView nameTxt;
        TextView quantityTxt;
        TextView priceTxt;
        Button increase;
        Button decrease;
    }
    public CustomAdapterForItems(ArrayList<ChartItem> data, Context context) {
        super(context, R.layout.activity_row_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.prices = new float[dataSet.size()];

    }
    @Override
    public void onClick(View v) {
        Log.e("On click ","clicked");
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ChartItem dataModel=(ChartItem)object;
        System.out.println("Item: " + dataModel.getName());
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChartItem chartItem = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_row_item, parent, false);
            viewHolder.nameTxt = (TextView) convertView.findViewById(R.id.name);
            viewHolder.quantityTxt = (TextView) convertView.findViewById(R.id.quantity);
            viewHolder.priceTxt = (TextView) convertView.findViewById(R.id.price);
            viewHolder.increase = (Button) convertView.findViewById(R.id.increase);
            viewHolder.increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String quantity = viewHolder.quantityTxt.getText().toString();
                    int quantity_int = Integer.parseInt(quantity) + 1;
                    viewHolder.quantityTxt.setText(Integer.toString(quantity_int));

                    String priceStr = viewHolder.priceTxt.getText().toString();

                    if(ScanProductsActivity.initialPrice != null)
                    {
                        price =(int) Float.parseFloat(ScanProductsActivity.initialPrice[position]);
                    }else{
                        price =(int) ScanProductsActivity.chartItem.get(position).getPrice();
                    }
                    int price_int = Integer.parseInt(priceStr) + price;
                    ScanProductsActivity.chartItem.get(position).setPriceForSelectedQuantity(price_int);

                    prices[position] = price_int;
                    ScanProductsActivity.subtotal += price;
                    ScanProductsActivity.setSubtotal();
                    viewHolder.priceTxt.setText(Integer.toString((int)ScanProductsActivity.chartItem.get(position).getPriceForSelectedQuantity()) );
                }
            });
            viewHolder.decrease = (Button) convertView.findViewById(R.id.decrease);
            viewHolder.decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String quantity = viewHolder.quantityTxt.getText().toString();
                    int quantity_int = Integer.parseInt(quantity) - 1;
                    if(quantity_int >= 0)
                    {
                        viewHolder.quantityTxt.setText(Integer.toString(quantity_int));

                        String priceStr = viewHolder.priceTxt.getText().toString();

                        if(ScanProductsActivity.initialPrice != null)
                        {
                            price =(int) Float.parseFloat(ScanProductsActivity.initialPrice[position]);
                        }else{
                            price =(int) ScanProductsActivity.chartItem.get(position).getPrice();
                        }
                        int price_int = Integer.parseInt(priceStr) - price;
                        ScanProductsActivity.chartItem.get(position).setPriceForSelectedQuantity(price_int);
                        ScanProductsActivity.subtotal -= price;
                        ScanProductsActivity.setSubtotal();
                        prices[position] = price_int;
                        viewHolder.priceTxt.setText(Integer.toString((int)ScanProductsActivity.chartItem.get(position).getPriceForSelectedQuantity()));
                    }

                }
            });

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.nameTxt.setText(chartItem.getName());
        viewHolder.quantityTxt.setText(Integer.toString((int)chartItem.getQuantity()));
        viewHolder.priceTxt.setText(Integer.toString((int)chartItem.getPrice()));
        return convertView;
    }

}
