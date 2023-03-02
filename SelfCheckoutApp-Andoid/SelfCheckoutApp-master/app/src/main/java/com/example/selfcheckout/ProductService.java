package com.example.selfcheckout;

import com.example.selfcheckout.data.ProductData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductService{
    ProductData product;
//
//    public ProductData getProductByBarcode(String barcode){
//
//        JsonPlaceHolderApi api = RetrofitUser.getRetrofitInstance().create(JsonPlaceHolderApi.class);
//
//        Call<ProductData> call = api.getProductByBarcode(barcode);
//
//        call.enqueue(new Callback<ProductData>() {
//
//            @Override
//            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
//
//                if(response.body() != null)
//                {
//                    product = response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductData> call, Throwable t) {
//
//            }
//
//        });
//        return product;
//    }

//    public ProductData getProduct(){
//
//        JsonPlaceHolderApi api = RetrofitProduct.getRetrofitInstance().create(JsonPlaceHolderApi.class);
//
//        Call<ProductData> call = api.getProduct();
//
//        call.enqueue(new Callback<ProductData>() {
//
//            @Override
//            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
//                System.out.println("ON RESPONSEEEEEEEE");
//                if(response.body() != null)
//                {
//                    product = response.body();
//                    System.out.println("PRODUCT ====== " + product.toString());
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

//    public ProductData getProductXml(){
//
//        JsonPlaceHolderApi api = RetrofitProduct.getRetrofitInstance().create(JsonPlaceHolderApi.class);
//
//        Call<ProductData> call = api.getProductXml();
//
//        call.enqueue(new Callback<ProductData>() {
//
//            @Override
//            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
//                System.out.println("ON RESPONSEEEEEEEE");
//                if(response.body() != null)
//                {
//                    product = response.body();
//                    System.out.println("PRODUCT ====== " + product.toString());
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
