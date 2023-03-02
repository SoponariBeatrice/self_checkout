package com.example.selfcheckout;

import com.example.selfcheckout.data.ProductData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XmlParser implements IApiParser{
    ProductData product;

    @Override
    public ProductData getProduct(String barcode) {
        JsonPlaceHolderApi api = RetrofitXml.getRetrofitInstance().create(JsonPlaceHolderApi.class);

        Call<ProductData> call = api.getProductXml(barcode);

        call.enqueue(new Callback<ProductData>() {

            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                System.out.println("ON RESPONSEEEEEEEE");
                product = response.body();
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                System.out.println("ON FAILUREEEE" + t.getMessage());
            }

        });
        return product;
    }


}
