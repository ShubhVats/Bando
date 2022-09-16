package com.example.wms_extended;

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

import com.example.wms_extended.adapters.BarcodeValue_Adapter;
import com.example.wms_extended.models.BarcodeValue_Model;
import com.example.wms_extended.models.Hold_Model;
import com.example.wms_extended.models.LocationItem_Response_Model;
import com.example.wms_extended.models.Users;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoldMaterial_1 extends AppCompatActivity {
    private ArrayList<BarcodeValue_Model> barcodeValue_modelArrayList;
    private RecyclerView recyclerView;
    EditText Hold_Material_Barcode;
    Button Hold_Material_Add, Hold_Material_Request_Ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hold_material1);

        Hold_Material_Add = findViewById(R.id.Hold_Material_Add);
        Hold_Material_Barcode = findViewById(R.id.Hold_Material_Barcode);
        Hold_Material_Request_Ok = findViewById(R.id.Hold_Material_Request_Ok);
        recyclerView = findViewById(R.id.Hold_Material_RecyclerView);
        //creating ArrayList
        barcodeValue_modelArrayList = new ArrayList<>();

        BarcodeValue_Adapter adapter = setAdapter();
        Hold_Material_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = barcodeValue_modelArrayList.size() + 1;
                String val = Hold_Material_Barcode.getText().toString();
                for (int i = 0; i < barcodeValue_modelArrayList.size(); i++) {
                    String repeat = barcodeValue_modelArrayList.get(i).getBarcode();
                    if (repeat.equals(val)) {
                        Toast.makeText(HoldMaterial_1.this, "Please read unique barcode!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (val.equals("")) {
                    Toast.makeText(HoldMaterial_1.this, "Please read unique barcode!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HoldMaterial_1.this, "Added", Toast.LENGTH_SHORT).show();
                    BarcodeValue_Model barcodeValueModel = new BarcodeValue_Model(val, q);
                    barcodeValue_modelArrayList.add(barcodeValueModel);
                    adapter.notifyItemInserted(barcodeValue_modelArrayList.size());
                }

            }
        });

        Hold_Material_Request_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postHandOverBin();
            }
        });

    }

    private BarcodeValue_Adapter setAdapter() {
        BarcodeValue_Adapter adapter = new BarcodeValue_Adapter(barcodeValue_modelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void postHandOverBin() {
        try {
            String Retrive_Material_OrderNo = getIntent().getStringExtra("Retrive_Material_OrderNo");
            String Retrive_Material_BinCount = getIntent().getStringExtra("Retrive_Material_BinCount");
            String Retrive_Material_CodeHolder = getIntent().getStringExtra("Retrive_Material_CodeHolder");
            String[] data = new String[barcodeValue_modelArrayList.size()];
            for (int i = 0; i < barcodeValue_modelArrayList.size(); i++) {
                data[i] = barcodeValue_modelArrayList.get(i).getBarcode();
            }
//        String data = mGson.toJson(users);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, String.valueOf(data));
            for (int i = 0; i < data.length; i++) {
                Log.e("Here", String.valueOf(data[i]));
            }
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            String UserID = pref.getString("UserID", null);
            Hold_Model resp = new Hold_Model();
            resp.setBins2Hold(data);
            resp.setUserId(UserID);
            resp.setUserComments("");
            Log.e("Here", resp.getUserComments());
            Log.e("Here", resp.getUserId());
            Log.e("Here", String.valueOf(resp.getBins2Hold()));
            Gson mGson = new Gson();
            String data2 = mGson.toJson(resp);
            MediaType JSON2 = MediaType.parse("application/json; charset=utf-8");
            RequestBody body2 = RequestBody.create(JSON2, data2);
            Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).holdBin(body2);
            bando_api.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        if (response.body() == null) {
                            Toast.makeText(HoldMaterial_1.this, "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            Log.e("Here", String.valueOf(jsonObject));
                            LocationItem_Response_Model res = new Gson().fromJson(String.valueOf(jsonObject), LocationItem_Response_Model.class);
                            if (res.getReport().equals("Success")) {
                                Toast.makeText(HoldMaterial_1.this, "Material Held Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(HoldMaterial_1.this, homescreen.class);
                                startActivity(i);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Here", e.toString());
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("Here", "Great Try" + t.toString());
                }
            });
        } catch (Exception e) {
            Log.e("here", String.valueOf(e));
        }
    }
}