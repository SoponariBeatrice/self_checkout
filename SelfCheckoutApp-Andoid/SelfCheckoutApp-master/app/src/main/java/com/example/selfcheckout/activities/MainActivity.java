package com.example.selfcheckout.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.selfcheckout.JsonPlaceHolderApi;
import com.example.selfcheckout.R;
import com.example.selfcheckout.RetrofitUser;
import com.example.selfcheckout.data.ProductData;
import com.example.selfcheckout.data.User;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity" ;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText username;
    TextView textViewResult;
    Button signUpButton;
    Button loginButton;
    TextInputLayout error;
    public static User user;
    Boolean flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = false;
        setContentView(R.layout.activity_main);
        firstNameInput = (EditText) findViewById(R.id.firstName);
        lastNameInput = (EditText) findViewById(R.id.lastName);
        emailInput = (EditText) findViewById(R.id.email);
        passwordInput = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        signUpButton = (Button) findViewById(R.id.signupButton);
        error = (TextInputLayout) findViewById(R.id.error);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(firstNameInput.getText().toString(),lastNameInput.getText().toString(),emailInput.getText().toString(),passwordInput.getText().toString(),username.getText().toString());
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                registerUser(user);

                if(flag)
                {
                    startActivity(myIntent);
                }
                else
                {
                    error.setHelperText(" Complete all fields correctly!");
                    System.out.println("username already taken");
                }
            }
        });
    }
    private void registerUser(User newUser){
        JsonPlaceHolderApi api = RetrofitUser.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        Log.e(TAG,newUser.toString());
        Call<User> call = api.registerUser(newUser);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               if(response.body() != null)
               {
                   Log.e(TAG,"Works " + response.body().getFirstName());
                   flag = true;
               }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG,"on failure: " + t.getMessage());

            }
        });

    }
}