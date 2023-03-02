package com.example.selfcheckout.PaymentConfig;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.selfcheckout.BillService;
import com.example.selfcheckout.ChartItem;
import com.example.selfcheckout.R;
import com.example.selfcheckout.activities.AllSupermarketsActivity;
import com.example.selfcheckout.activities.GenerateQrCodeActivity;
import com.example.selfcheckout.activities.LoginActivity;
import com.example.selfcheckout.activities.ScanProductsActivity;
import com.example.selfcheckout.data.BillData;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.SourceTypeModel;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.payments.paymentlauncher.PaymentResult;
import com.stripe.android.view.CardInputWidget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CheckoutActivity extends AppCompatActivity {
    /**
     * This example collects card payments, implementing the guide here: https://stripe.com/docs/payments/accept-a-payment-synchronously#android
     *
     * To run this app, follow the steps here: https://github.com/stripe-samples/accept-a-card-payment#how-to-run-locally
     */
    // 10.0.2.2 is the Android emulator's alias to localhost
    private static final String BACKEND_URL = "http://192.168.0.203:4242/";

    private OkHttpClient httpClient = new OkHttpClient();
    private Stripe stripe;
    private String filename = "shoppingList.txt";
    BillData bill;
    static BillService billService;
    private static Context context;
    public static Boolean flagForPayment = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckoutActivity.context = getApplicationContext();
        setContentView(R.layout.activity_checkout);
        loadPage();

    }

    private void loadPage() {
        // Clear the card widget
        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        cardInputWidget.clear();

        // For added security, our sample app gets the publishable key from the server
        Request request = new Request.Builder()
                .url(BACKEND_URL + "stripe-key")
                .get()
                .build();
        httpClient.newCall(request)
                .enqueue(new StripeKeyCallback(this));
    }

    private void pay() {
        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

        if (params == null) {
            return;
        }
        stripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
            @Override
            public void onSuccess(@NonNull PaymentMethod result) {
                // Create and confirm the PaymentIntent by calling the sample server's /pay endpoint.
                pay(result.id, null);
            }

            @Override
            public void onError(@NonNull Exception e) {
            }
        });
    }

    private void pay(@Nullable String paymentMethodId, @Nullable String paymentIntentId) {
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        final String json;

        if (paymentMethodId != null) {
            json = "{"
                    + "\"useStripeSdk\":true,"
                    + "\"paymentMethodId\":" + "\"" + paymentMethodId + "\","
                    + "\"currency\":\"usd\","
                    + "\"items\":["
                    + "{\"id\":\"photo_subscription\"}"
                    + "]"
                    + "}";
        } else {
            json = "{"
                    + "\"paymentIntentId\":" +  "\"" + paymentIntentId + "\""
                    + "}";
        }
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "pay")
                .post(body)
                .build();
        httpClient
                .newCall(request)
                .enqueue(new PayCallback(this, stripe));
    }

    private void displayAlert(@NonNull String title, @NonNull String message, boolean restartDemo) {
        runOnUiThread(() -> {
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(this)
                            .setTitle(title).
                            setMessage("Your bill was added in My Bills section!");
            flagForPayment = true;
            if(title.equals("Error"))
                builder.setMessage("Payment could not be processed");

            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            if (restartDemo) {
                builder.setPositiveButton("Ok, thanks",
                        (DialogInterface dialog, int index) -> loadPage());
            } else {
                builder.setPositiveButton("Ok", null);
            }
            builder
                    .create()
                    .show();
        });
    }

    private void onRetrievedKey(@NonNull String stripePublishableKey) {
        final Context applicationContext = getApplicationContext();
        PaymentConfiguration.init(applicationContext, stripePublishableKey);
        stripe = new Stripe(applicationContext, stripePublishableKey);

        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> pay());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private static final class StripeKeyCallback implements Callback {
        @NonNull private final WeakReference<CheckoutActivity> activityRef;

        private StripeKeyCallback(@NonNull CheckoutActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG)
                            .show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() -> Toast.makeText(activity,
                        "Error: " + response.toString(), Toast.LENGTH_LONG).show());
            } else {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                final ResponseBody responseBody = response.body();
                final Map<String, String> responseMap;
                if (responseBody != null) {
                    responseMap = gson.fromJson(responseBody.string(), type);
                } else {
                    responseMap = new HashMap<>();
                }

                final String stripePublishableKey = responseMap.get("publishableKey");
                if (stripePublishableKey != null) {
                    activity.runOnUiThread(() ->
                            activity.onRetrievedKey(stripePublishableKey));
                }
            }
        }
    }

    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<CheckoutActivity> activityRef;
        @NonNull private final Stripe stripe;

        private PayCallback(@NonNull CheckoutActivity activity, @NonNull Stripe stripe) {
            this.activityRef = new WeakReference<>(activity);
            this.stripe = stripe;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG)
                            .show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(activity, "Error: " + response.toString(), Toast.LENGTH_LONG)
                                .show());
            } else {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                final ResponseBody responseBody = response.body();
                final Map<String, String> responseMap;
                if (responseBody != null) {
                    responseMap = gson.fromJson(responseBody.string(), type);
                } else {
                    responseMap = new HashMap<>();
                }

                String error = responseMap.get("error");
                String paymentIntentClientSecret = responseMap.get("clientSecret");
                String requiresAction = responseMap.get("requiresAction");

                if (error != null) {
                    activity.displayAlert("Error", error, false);
                } else if (paymentIntentClientSecret != null) {
                    if ("true".equals(requiresAction)) {
                        activity.runOnUiThread(() ->
                                stripe.handleNextActionForPayment(activity, paymentIntentClientSecret));
                    } else {
                        // ToDo add bill
                       String shoppingList = activity.readData(activity.filename);
                       BillData bill = activity.createBill(shoppingList);
                       billService = new BillService();
                       billService.addBill(bill);
                        goToQrCode();
                       activity.displayAlert("Payment succeeded",
                                paymentIntentClientSecret, true);

                    }
                }

            }
        }
    }
    public static void goToQrCode()
    {
        Intent intent = new Intent(CheckoutActivity.context, GenerateQrCodeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);
    }
    public BillData createBill(String shoppingList)
    {
        BillData bill = new BillData();
        String[] parser = shoppingList.split("\n");
        System.out.println("SHOPPING LIST = " + shoppingList);
        String[] struct = new String[parser.length];

        List<String> productNames = new ArrayList<>();
        List<String> quantities = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        for(int i = 0; i < parser.length; i ++)
        {
            struct = parser[i].split(",");
            productNames.add(struct[0]);
            String[] nb1 = struct[2].split("\\.");
            System.out.println("TOTAL = " + nb1[0]);
            Integer quantity_int = Integer.parseInt(nb1[0])/Integer.parseInt(struct[1]);
            quantities.add(quantity_int.toString());
            prices.add(struct[2]);
            System.out.println("PRICES = " + struct[2]);
        }
        bill.setProductNames(productNames);
        bill.setPrices(prices);
        bill.setQuantities(quantities);
        bill.setIdUser(LoginActivity.idUser);
        bill.setTotal(Integer.toString(ScanProductsActivity.subtotal));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        bill.setDate(sdf.format(new Date()));
        bill.setSupermarket(AllSupermarketsActivity.supermarketName);
        return bill;
    }
    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        private final WeakReference<CheckoutActivity> activityRef;

        PaymentResultCallback(@NonNull CheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                activity.runOnUiThread(() -> {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    activity.displayAlert("Payment completed",
                            gson.toJson(paymentIntent), true);

                });
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.runOnUiThread(() -> {
                    final PaymentIntent.Error error = paymentIntent.getLastPaymentError();
                    final String errorMessage;
                    if (error != null && error.getMessage() != null) {
                        errorMessage = error.getMessage();
                    } else {
                        errorMessage = "Unknown error";
                    }
                    activity.displayAlert("Payment failed", errorMessage, false);
                });
            } else if (status == PaymentIntent.Status.RequiresConfirmation) {
                // After handling a required action on the client, the status of the PaymentIntent is
                // requires_confirmation. You must send the PaymentIntent ID to your backend
                // and confirm it to finalize the payment. This step enables your integration to
                // synchronously fulfill the order on your backend and return the fulfillment result
                // to your client.
                activity.pay(null, paymentIntent.getId());
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.runOnUiThread(() ->
                    activity.displayAlert("Error", e.toString(), false)
            );
        }

    }

    public String readData(String filename)
    {
        String currentShoppingList ="";
        try
        {
            FileInputStream fin = openFileInput(filename);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }
            currentShoppingList = temp.toString();
            fin.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.e("CURRENT ITEM" , currentShoppingList);
        return currentShoppingList;
    }
}