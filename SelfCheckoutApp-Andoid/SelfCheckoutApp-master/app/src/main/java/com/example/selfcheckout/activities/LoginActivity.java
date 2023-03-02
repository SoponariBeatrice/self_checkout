package com.example.selfcheckout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.selfcheckout.JsonPlaceHolderApi;
import com.example.selfcheckout.LoginRequest;
import com.example.selfcheckout.LoginResponse;
import com.example.selfcheckout.R;
import com.example.selfcheckout.RetrofitUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";
    Button loginButton;
    EditText passwordInput;
    EditText usernameInput;
    TextView error;
    public static String token;
    Boolean flag;
    public static Long idUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        passwordInput = (EditText) findViewById(R.id.password);
        usernameInput = (EditText) findViewById(R.id.username);
        error = (TextView) findViewById(R.id.errorAtLogin);
        flag = false;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, AllSupermarketsActivity.class);
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                loginUser(username,password);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(flag)
                            startActivity(myIntent);
                        else
                        {
                            error.setText("Username or password wrong");
                        }
                    }
                }, 1000);


            }
        });
    }
    public void loginUser(String username, String password)
    {
        JsonPlaceHolderApi api = RetrofitUser.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> call = api.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               if(response.body() != null)
               {
                   token = response.body().token;
                   idUser = response.body().id;
                   Log.e(TAG,"Works " + response.body().token + response.body().username + response.body().id + response.body().email + response.body().roles);
                   flag = true;
               }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG,"on failure: " + t.getMessage());
            }
        });
    }
}
