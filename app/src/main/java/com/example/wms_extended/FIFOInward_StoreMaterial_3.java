package com.example.wms_extended;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wms_extended.models.LotStore_Model;
import com.example.wms_extended.models.Users;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FIFOInward_StoreMaterial_3 extends AppCompatActivity {
    Button FIFOBeltInward_StoreMaterial;
    EditText FIFOBeltInward_ScanTie_Barcode, FIFOBeltInward_ScanFIFO_Barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifoinward_store_material3);

        FIFOBeltInward_StoreMaterial = findViewById(R.id.FIFOBeltInward_StoreMaterial);
        FIFOBeltInward_ScanTie_Barcode = findViewById(R.id.FIFOBeltInward_ScanTie_Barcode);
        FIFOBeltInward_ScanFIFO_Barcode = findViewById(R.id.FIFOBeltInward_ScanFIFO_Barcode);

        String parent = getIntent().getStringExtra("parent");
        if (parent.equals("FIFOBeltInward")) {
            FIFOBeltInward_StoreMaterial.setOnClickListener(view -> storeMaterialInward());
        } else {
            FIFOBeltInward_StoreMaterial.setOnClickListener(view -> retriveMaterialOutward());
        }
    }

    private void retriveMaterialOutward() {
        String getLotCardNo = getIntent().getStringExtra("getLotCardNo");
        String FIFOBeltInward_BeltQty = getIntent().getStringExtra("FIFOBeltInward_BeltQty");
        String ScanTie_Barcode = FIFOBeltInward_ScanTie_Barcode.getText().toString();
        String ScanFIFO_Barcode = FIFOBeltInward_ScanFIFO_Barcode.getText().toString();

        LotStore_Model lotStore_model = new LotStore_Model();
        lotStore_model.setLotCardNo(getLotCardNo);
        lotStore_model.setBeltQty(Integer.parseInt(FIFOBeltInward_BeltQty));
        lotStore_model.setBinNO(ScanFIFO_Barcode);
        lotStore_model.setTieNo(ScanTie_Barcode);
        Log.e("Here",getLotCardNo);
        Log.e("Here",FIFOBeltInward_BeltQty);
        Log.e("Here",ScanFIFO_Barcode);
        Log.e("Here",ScanTie_Barcode);
        Gson mGson = new Gson();
        String data = mGson.toJson(lotStore_model);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).retriveBelt(ScanTie_Barcode, body);
        bando_api.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                try {

                    if (response.body() == null) {
                        Toast.makeText(FIFOInward_StoreMaterial_3.this, "Empty Response from server", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
//                        Log.e("Here",jsonObject.toString());
                        if (response.body().get("report").toString().equals("\"Failure\"")) {
                            Toast.makeText(FIFOInward_StoreMaterial_3.this, response.body().get("exception").toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FIFOInward_StoreMaterial_3.this, response.body().get("report").toString(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(FIFOInward_StoreMaterial_3.this, homescreen.class);
                            startActivity(i);
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
                Toast.makeText(FIFOInward_StoreMaterial_3.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void storeMaterialInward() {
        String getLotCardNo = getIntent().getStringExtra("getLotCardNo");
        String FIFOBeltInward_BeltQty = getIntent().getStringExtra("FIFOBeltInward_BeltQty");
        String ScanTie_Barcode = FIFOBeltInward_ScanTie_Barcode.getText().toString();
        String ScanFIFO_Barcode = FIFOBeltInward_ScanFIFO_Barcode.getText().toString();

        LotStore_Model lotStore_model = new LotStore_Model();
        lotStore_model.setLotCardNo(getLotCardNo);
        lotStore_model.setBeltQty(Integer.parseInt(FIFOBeltInward_BeltQty));
        lotStore_model.setBinNO(ScanFIFO_Barcode);
        lotStore_model.setTieNo(ScanTie_Barcode);
        Gson mGson = new Gson();
        String data = mGson.toJson(lotStore_model);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).storeBelt(getLotCardNo, body);
        bando_api.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                try {

                    if (response.body() == null) {
                        Toast.makeText(FIFOInward_StoreMaterial_3.this, "Empty Response from server", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
//                        Log.e("Here",jsonObject.toString());
                        if (response.body().get("report").toString().equals("\"Failure\"")) {
                            Toast.makeText(FIFOInward_StoreMaterial_3.this, response.body().get("exception").toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FIFOInward_StoreMaterial_3.this, response.body().get("report").toString(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(FIFOInward_StoreMaterial_3.this, homescreen.class);
                            startActivity(i);
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
                Toast.makeText(FIFOInward_StoreMaterial_3.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}