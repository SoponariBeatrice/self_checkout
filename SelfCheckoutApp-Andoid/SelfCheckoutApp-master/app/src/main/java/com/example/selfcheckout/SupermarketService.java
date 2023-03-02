package com.example.selfcheckout;

import android.util.Log;

import com.example.selfcheckout.data.ProductData;
import com.example.selfcheckout.data.SupermarketData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupermarketService {
    List<SupermarketData> supermarkets;
    public List<SupermarketData> getAllSupermarkets() {
        JsonPlaceHolderApi api = RetrofitUser.getRetrofitInstance().create(JsonPlaceHolderApi.class);

        Call<List<SupermarketData>> call = api.getAllSupermarkets();

        call.enqueue(new Callback<List<SupermarketData>>() {
            @Override
            public void onResponse(Call<List<SupermarketData>> call, Response<List<SupermarketData>> response) {
                System.out.println("ON RESPONSEEEEEEEE");
                if(response.isSuccessful())
                {
                    supermarkets = response.body();
                    for (SupermarketData s:supermarkets
                         ) {
                        System.out.println(s.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SupermarketData>> call, Throwable t) {
                System.out.println("ON FAILUREEEE" + t.getMessage());
            }

        });
        return supermarkets;
    }
}
