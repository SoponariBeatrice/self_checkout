package com.example.selfcheckout.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.selfcheckout.R;
import com.example.selfcheckout.adapters.CustomAdapterBill;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class FullBillActivity extends AppCompatActivity {

    TextView total;
    ImageView qrForBill;
    String qrText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        TableLayout table = (TableLayout)findViewById(R.id.table);
        total = findViewById(R.id.totalFromBill);
        TableRow row = (TableRow)findViewById(R.id.row);
        qrForBill = findViewById(R.id.qrForBill);
        qrText = "Total paid: " + total;
        generateQRCode(qrText);

        int nIndex = table.indexOfChild(row);
        table.removeView(row);
        table.addView(row, nIndex);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
         int index = 0;
        for (String s: MyBillsActivity.currentBill.getProductNames()
        ) {
           appendRow(table, s, MyBillsActivity.currentBill.getPrices().get(index), MyBillsActivity.currentBill.getQuantities().get(index) );
            index++;
        }
        total.setText("TOTAL: " + MyBillsActivity.currentBill.getTotal());


    }
    public void generateQRCode(String text)
    {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
            for (int x = 0; x<200; x++){
                for (int y=0; y<200; y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }
            qrForBill.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void appendRow(TableLayout table, String name, String price, String quantity) {
        TableRow row = new TableRow(this);

        TextView hLabel = new TextView(this);
        hLabel.setText(name);
        hLabel.setPadding(10, 10, 10, 10);

        TextView hNextLabel = new TextView(this);
        hNextLabel.setText(quantity);
        hNextLabel.setPadding(10, 10, 10, 10);
        hNextLabel.setGravity(Gravity.RIGHT | Gravity.TOP);

        TextView hNextLabel2 = new TextView(this);
        hNextLabel2.setText(price);
        hNextLabel2.setPadding(10, 10, 10, 10);
        hNextLabel2.setGravity(Gravity.RIGHT | Gravity.TOP);

        row.addView(hLabel, new TableRow.LayoutParams(1));
        row.addView(hNextLabel, new TableRow.LayoutParams());
        row.addView(hNextLabel2, new TableRow.LayoutParams());

        table.addView(row, new TableLayout.LayoutParams());
    }
}
