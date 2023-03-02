package com.example.selfcheckout;
import com.example.selfcheckout.data.BillData;
import com.example.selfcheckout.data.ProductData;
import com.example.selfcheckout.data.SupermarketData;
import com.example.selfcheckout.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @POST("/api/auth/signup")
    Call<User> registerUser(@Body User user);
    @POST("/api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("/api/auth/product/{barcode}")
     Call<ProductData> getProduct(@Path("barcode") String barcode);

    @GET("/api/auth/product/xml/{barcode}")
    Call<ProductData> getProductXml(@Path("barcode") String barcode);

    @GET("/api/all-supermarkets")
    Call<List<SupermarketData>> getAllSupermarkets();

    @POST("/api/auth/bill")
    Call<BillData> addBill(@Body BillData billData);

    @GET("/api/auth/get-all-bills/{idUser}")
    Call<List<BillData>> getAllBills(@Path("idUser") Long idUser);
}