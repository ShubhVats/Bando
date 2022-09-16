package com.example.wms_extended;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wms_extended.adapters.DispatchBin_Adapter;
import com.example.wms_extended.models.BarcodeValue_Model;
import com.example.wms_extended.models.DispatchBin_Model;
import com.example.wms_extended.models.LotStore_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Line2Dispatch_MaterialPick_2 extends AppCompatActivity {
    private ArrayList<DispatchBin_Model> dispatchBin_modelArrayList = new ArrayList<>();;
    private RecyclerView recyclerView;
    DispatchBin_Adapter adapter = new DispatchBin_Adapter(dispatchBin_modelArrayList);

    TextView Line2Dispatch_NavNo, Line2Dispatch_Qty;
    Button Line2Dispatch_Add,Line2Dispatch_Store;
    EditText Line2Dispatch_Bin_Barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line2_dispatch_material_pick2);

        recyclerView = findViewById(R.id.Line2Dispatch_RecyclerView);
        Line2Dispatch_NavNo = findViewById(R.id.Line2Dispatch_NavNo);
        Line2Dispatch_Qty = findViewById(R.id.Line2Dispatch_Qty);
        Line2Dispatch_Add = findViewById(R.id.Line2Dispatch_Add);
        Line2Dispatch_Bin_Barcode = findViewById(R.id.Line2Dispatch_Bin_Barcode);
        Line2Dispatch_Store = findViewById(R.id.Line2Dispatch_Store);

        String getMessage = getIntent().getStringExtra("getMessage");
        String getRequestNo = getIntent().getStringExtra("getRequestNo");
        String getNavisionBarcode = getIntent().getStringExtra("getNavisionBarcode");
        String getNavCode = getIntent().getStringExtra("getNavCode");
        String getDispatchQty = getIntent().getStringExtra("getDispatchQty");
        String getUserId = getIntent().getStringExtra("getUserId");

        Line2Dispatch_NavNo.setText(getNavCode);
        Line2Dispatch_Qty.setText(getDispatchQty);

//        //creating ArrayList
//        dispatchBin_modelArrayList = new ArrayList<>();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String UserID = pref.getString("UserID", null);

        String ID = UserID + "/" + getRequestNo;
        getDispatchLot(ID);


        Line2Dispatch_Add.setOnClickListener(view -> {
            int q = dispatchBin_modelArrayList.size() + 1;
            String val = Line2Dispatch_Bin_Barcode.getText().toString();
            for (int i = 0; i < dispatchBin_modelArrayList.size(); i++) {
                String repeat = dispatchBin_modelArrayList.get(i).getBarcode();
                if (repeat.equals(val)) {
                    Toast.makeText(Line2Dispatch_MaterialPick_2.this, "Please read unique barcode!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (val.equals("")) {
                Toast.makeText(Line2Dispatch_MaterialPick_2.this, "Please read unique barcode!", Toast.LENGTH_SHORT).show();
            } else {
                checkAndAddPallet();

            }

        });
        Log.e("Here", getRequestNo);

        Line2Dispatch_Store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Line2Dispatch_MaterialPick_2.this, "Toast", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkAndAddPallet() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String UserID = pref.getString("UserID", null);
        String getRequestNo = getIntent().getStringExtra("getRequestNo");
        String Barcode = Line2Dispatch_Bin_Barcode.getText().toString();

        Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).checkPallet(UserID,getRequestNo,Barcode);
        bando_api.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(Line2Dispatch_MaterialPick_2.this, "Empty Response from server", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Log.e("Here",jsonObject.toString());
                        if (response.body().get("report").toString().equals("\"Failure\"")) {
                            Toast.makeText(Line2Dispatch_MaterialPick_2.this, response.body().get("exception").toString(), Toast.LENGTH_LONG).show();
                        } else {
                            DispatchBin_Model dispatchBin_model = new DispatchBin_Model(Barcode, "");
                            dispatchBin_modelArrayList.add(dispatchBin_model);
                            adapter.notifyItemInserted(dispatchBin_modelArrayList.size());
                            Log.e("Here", String.valueOf(dispatchBin_model.getBarcode()));
                            Toast.makeText(Line2Dispatch_MaterialPick_2.this, "Added", Toast.LENGTH_SHORT).show();
//
//                            Toast.makeText(Line2Dispatch_MaterialPick_2.this, response.body().get("report").toString(), Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(Line2Dispatch_MaterialPick_2.this, homescreen.class);
//                            startActivity(i);
                        }

                    }

                } catch (Exception e) {
                    Log.e("Here", e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("Here", "Great Try" + t);
                Toast.makeText(Line2Dispatch_MaterialPick_2.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private DispatchBin_Adapter setAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void getDispatchLot(String UserID) {
        String url = "/api/belt_section/dispatch_request_view_boxes/";
        String finalUrl = url + UserID;
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<JsonArray> call = bando_api.reItems(finalUrl);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(Line2Dispatch_MaterialPick_2.this, "No response from server!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Here", String.valueOf(response.body()));
                        JsonArray jsonElements = response.body();
                        for (int i = 0; i < jsonElements.size(); i++) {
                            Log.e("here", String.valueOf(jsonElements.get(i)));
                            DispatchBin_Model singledata = new Gson().fromJson(jsonElements.get(i), DispatchBin_Model.class);
                            Log.e("here", singledata.getBarcode());
                            dispatchBin_modelArrayList.add(singledata);
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