package com.example.selfcheckout;

import com.example.selfcheckout.data.ProductData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonParser implements IApiParser {
    ProductData product;

    @Override
    public ProductData getProduct(String barcode) {
        JsonPlaceHolderApi api = RetrofitProduct.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        Call<ProductData> call = api.getProduct(barcode);
        Response<ProductData> response = null;
        try {
            response = call.execute();
            product = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return product;
    }
//    @Override
//    public ProductData getProduct(String barcode) {
//
//        JsonPlaceHolderApi api = RetrofitProduct.getRetrofitInstance().create(JsonPlaceHolderApi.class);
//
//        Call<ProductData> call = api.getProduct(barcode);
//
//        call.enqueue(new Callback<ProductData>() {
//
//            @Override
//            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
//                System.out.println("ON RESPONSEEEEEEEE");
//                if(response.body() != null)
//                {
//                    System.out.println("PRODUCT === " + response.body());
//                    product = response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductData> call, Throwable t) {
//                System.out.println("ON FAILUREEEE" + t.getMessage());
//            }
//
//        });
//        return product;
//    }
}
