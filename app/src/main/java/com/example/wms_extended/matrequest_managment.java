package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms_extended.adapters.RequestMaterial_Adapter;
import com.example.wms_extended.models.RequestMaterial_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class matrequest_managment extends AppCompatActivity {
    private ArrayList<RequestMaterial_Model> requestMaterial_modelArrayList;
    private RecyclerView recyclerView;
    Button MaterialRequest_Holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrequest_managment);
        recyclerView = findViewById(R.id.request_recycler_itemView);
        MaterialRequest_Holder = findViewById(R.id.material_request_btn);


        //creating ArrayList
        requestMaterial_modelArrayList = new ArrayList<>();

        //getting data from local
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String UserID = pref.getString("UserID", null);
        //api request for data
        getRequestItem(UserID);


        MaterialRequest_Holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(matrequest_managment.this, matrialreuest_scanbarcode_screen2.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        RequestMaterial_Adapter adapter = new RequestMaterial_Adapter(requestMaterial_modelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void getRequestItem(String UserID) {
        String url = "RmRequestItemes/bando/";
        String finalUrl = url + UserID;
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<JsonArray> call = bando_api.reItems(finalUrl);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(matrequest_managment.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Here", String.valueOf(response.body()));
                        JsonArray jsonElements = response.body();
                        for (int i = 0; i < jsonElements.size(); i++) {
                            Log.e("here", String.valueOf(jsonElements.get(i)));
                            RequestMaterial_Model singledata = new Gson().fromJson(jsonElements.get(i), RequestMaterial_Model.class);
                            Log.e("here", singledata.getOrderNo());

                            requestMaterial_modelArrayList.add(singledata);

                        }
                        setAdapter();                            //setAdpater

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("ConfirmMaterialDeliveryList", String.valueOf(jsonElements));
                        editor.apply();

                    }

                } catch (Exception e) {
                    Log.e("Here", "Error: " + e);

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Here", "onFailure: " + t.getMessage());

            }
        });
    }


}