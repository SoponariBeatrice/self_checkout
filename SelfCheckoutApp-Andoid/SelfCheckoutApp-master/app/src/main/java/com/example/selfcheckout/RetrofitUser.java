package com.example.selfcheckout;

import androidx.annotation.NonNull;

import com.example.selfcheckout.activities.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;
import java.util.logging.XMLFormatter;

import co.infinum.retromock.BodyFactory;
import co.infinum.retromock.NonEmptyBodyFactory;
import co.infinum.retromock.Retromock;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitUser {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + LoginActivity.token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://192.168.0.203:8090")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}