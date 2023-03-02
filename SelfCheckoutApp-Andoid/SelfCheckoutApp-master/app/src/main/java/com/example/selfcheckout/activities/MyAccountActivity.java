package com.example.selfcheckout.activities;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.selfcheckout.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyAccountActivity extends AppCompatActivity {
    TextView name;
    TextView cardNb1;
    TextView cardNb2;
    TextView cardNb3;
    TextView cardNb4;
    TextView cvv;
    TextView month;
    TextView year;

    TextInputLayout nameInput;
    TextInputLayout cardNbInput;

    TextInputLayout cvvInput;
    TextInputEditText monthInput;
    TextInputEditText yearInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_activity);
        name = (TextView) findViewById(R.id.tv_card_owner);
        cardNb1 = findViewById(R.id.tv_card_one);
        cardNb2 = findViewById(R.id.tv_card_two);
        cardNb3 = findViewById(R.id.tv_card_three);
        cardNb4 = findViewById(R.id.tv_card_four);
        cvv = findViewById(R.id.tv_card_cv);
        month = findViewById(R.id.tv_card_month);
        year = findViewById(R.id.tv_card_year);

        nameInput = findViewById(R.id.til_card_name);
        cardNbInput = findViewById(R.id.til_card_no);
        cvvInput = findViewById(R.id.til_card_cv);
        monthInput = findViewById(R.id.tev_card_month);
        yearInput = findViewById(R.id.tev_card_year);

        Button payButton = findViewById(R.id.btn_pay);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setText(nameInput.getEditText().getText());
                cardNb1.setText(cardNbInput.getEditText().getText().subSequence(0,3));
                cardNb2.setText(cardNbInput.getEditText().getText().subSequence(4,7));
                cardNb3.setText(cardNbInput.getEditText().getText().subSequence(8,11));
                cardNb4.setText(cardNbInput.getEditText().getText().subSequence(12,15));
                cvv.setText(cvvInput.getEditText().getText());
                month.setText(monthInput.getText());
                year.setText(yearInput.getText());

                Toast.makeText(MyAccountActivity.this, "Card Added. ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


