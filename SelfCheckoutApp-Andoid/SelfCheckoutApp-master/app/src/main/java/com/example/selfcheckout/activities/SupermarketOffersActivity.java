package com.example.selfcheckout.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.selfcheckout.R;

public class SupermarketOffersActivity extends AppCompatActivity {
    private static String TAG = "SupermarketOffersActivity";
    Button startShoppingSessionButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_page_for_supermarket);
        startShoppingSessionButton =  (Button) findViewById(R.id.startShoppingSessionButton);
        startShoppingSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SupermarketOffersActivity.this, ScanProductsActivity.class);
                startActivity(myIntent);

            }
        });
    }



}



