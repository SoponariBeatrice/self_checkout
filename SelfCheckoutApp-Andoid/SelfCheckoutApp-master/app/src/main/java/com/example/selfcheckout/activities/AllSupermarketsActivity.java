package com.example.selfcheckout.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;


import com.example.selfcheckout.JsonPlaceHolderApi;
import com.example.selfcheckout.R;
import com.example.selfcheckout.RetrofitUser;
import com.example.selfcheckout.SupermarketService;
import com.example.selfcheckout.data.SupermarketData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSupermarketsActivity extends AppCompatActivity {

    SupermarketService service = new SupermarketService();
    List<SupermarketData> supermarkets;
    ListView listView;
    TextView t;
    public static String dataFormat;
    public static String supermarketName;
    Button myAccount;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkets);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView =(ListView) findViewById(R.id.list);
        myAccount = (Button) findViewById(R.id.myAccountButton);
        FrameLayout linearLayout =  this.getWindow().getDecorView().findViewById(android.R.id.content);
//        myAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AllSupermarketsActivity.this, MyBillsActivity.class);
//                startActivity(intent);
//            }
//        });

        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
               // vto.removeOnGlobalLayoutListener(this);

            }
        });
        getAllSupermarkets();


        final Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(supermarkets != null)
                {
                    String[] supermarketNames = new String[supermarkets.size()];
                    Log.e("SUPERMATKET", supermarkets.get(0).getName());

                    int i = 0;
                    for (SupermarketData s:supermarkets
                    ) {
                        supermarketNames[i] = s.getName();
                        Log.e("NAME", supermarketNames[i]);
                        i++;
                    }
                    ArrayAdapter<String> arr;
                    arr
                            = new ArrayAdapter<String>(
                            AllSupermarketsActivity.this,
                            R.layout.activity_listview,
                            R.id.textView,
                            supermarketNames);
                    listView.setAdapter(arr);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            SupermarketData supermarket = supermarkets.get(i);
                            Toast.makeText(AllSupermarketsActivity.this, supermarket.getName(),
                                    Toast.LENGTH_SHORT).show();
                            dataFormat = supermarket.getDataFormat();
                            supermarketName = supermarket.getName();
                            Intent myIntent = new Intent(AllSupermarketsActivity.this, ScanProductsActivity.class);
                            startActivity(myIntent);
                        }
                    });

                }

            }
        }, 1000);
    }
    View.OnClickListener btnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Log.e("CLICKED",v.getTag().toString());
        }

    };

    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        SupermarketData supermarket = supermarkets.get(position);
        Toast.makeText(AllSupermarketsActivity.this, supermarket.getName(),
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
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
    public void goToBills(MenuItem item){
        Intent intent = new Intent(AllSupermarketsActivity.this, MyBillsActivity.class);
        startActivity(intent);
    }

    public void goToShoppingList(MenuItem item)
    {
        Intent intent = new Intent(AllSupermarketsActivity.this, ShoppingList.class);
        startActivity(intent);
    }
    public void goToLogIn(MenuItem item)
    {
        Intent intent = new Intent(AllSupermarketsActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
