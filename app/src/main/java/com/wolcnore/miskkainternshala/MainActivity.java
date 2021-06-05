package com.wolcnore.miskkainternshala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wolcnore.miskkainternshala.adapter.WorldDataAdapter;
import com.wolcnore.miskkainternshala.db.AppDatabase;
import com.wolcnore.miskkainternshala.db.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AppDatabase db;
    RecyclerView rcv;
    Button delete;

    WorldDataAdapter worldDataAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDBInstance(getApplicationContext());
        rcv = findViewById(R.id.rcv);
        delete = findViewById(R.id.delete);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Data Fetching wait ..");

        progressDialog.setCancelable(false);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);


        if (InternetConnection.checkConnection(getApplicationContext())) {
            Toast.makeText(this, "Internet available", Toast.LENGTH_SHORT).show();
            getData();
        } else {
            Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show();

            getLocalData();
        }

        delete.setOnClickListener(this);

    }

    private void getData() {

        progressDialog.show();
        Call<List<Pojo>> call = RetrofitClient.getInstance().getMyApi().getsuperHeroes();

        call.enqueue(new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onResponse: " + response.body());
                List<Pojo> list = response.body();

/*
                rcv.setAdapter(new WorldDataAdapter(list, getApplicationContext()));
*/

                Data save = new Data(list);
                db.userDao().insertAll(save);

                getLocalData();


            }

            @Override
            public void onFailure(Call<List<Pojo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ertror", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailure: ", t);

            }
        });
    }

    void getLocalData() {

        if (db.userDao().getData() != null) {
            List<Pojo> list = db.userDao().getData().list;
            if (list.size() > 0) {

                worldDataAdapter = new WorldDataAdapter(list, getApplicationContext());
                rcv.setAdapter(worldDataAdapter);
            }
        } else {
            Toast.makeText(this, "Room db is clear.. Please Be online..", Toast.LENGTH_LONG).show();
        }

        progressDialog.dismiss();


    }

    void deleteAllData() {
        AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
        int a = db.userDao().deleteData();
        Log.d("TAG", "onClick: after delete :" + db.userDao().getData());

        List<Pojo> list = null;
        worldDataAdapter = new WorldDataAdapter(list, getApplicationContext());
        rcv.setAdapter(worldDataAdapter);


    }

    @Override
    public void onClick(View v) {
        if (v == delete) {
            deleteAllData();
        }
    }

    public static class InternetConnection {

        /**
         * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
         */
        public static boolean checkConnection(Context context) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr != null) {
                NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

                if (activeNetworkInfo != null) { // connected to the internet
                    // connected to the mobile provider's data plan
                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        return true;
                    } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
            return false;
        }
    }

}