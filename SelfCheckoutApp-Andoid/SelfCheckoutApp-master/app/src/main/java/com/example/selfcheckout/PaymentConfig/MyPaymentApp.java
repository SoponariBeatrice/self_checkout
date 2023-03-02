package com.example.selfcheckout.PaymentConfig;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;

public class MyPaymentApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51FrQwlBnheRwo4jaGI5iqBTAA9Z9KnwBOOCiNoTMhhLsox5vKpFPB8s61gacy9H4kQZ0Jol31w1KpAHtuS7MKO1100ZOqM7qyt"
        );
    }
}