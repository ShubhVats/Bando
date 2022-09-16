package com.example.wms_extended;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wms_extended.models.InventoryItem_Model;
import com.example.wms_extended.models.Quarantine_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetriveMaterial_PutPallet_3 extends AppCompatActivity {

    ImageView Retrive_Material_ImgBtn;
    EditText Retrive_Material_CodeHolder;
    Button Retrive_Material_ScanSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_material_put_pallet3);

        Retrive_Material_ImgBtn = findViewById(R.id.Retrive_Material_ImgBtn);
        Retrive_Material_CodeHolder = findViewById(R.id.Retrive_Material_CodeHolder);
        Retrive_Material_ScanSuccess = findViewById(R.id.Retrive_Material_ScanSuccess);



        Retrive_Material_ScanSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPallet();
            }
        });
    }

    private void pickPallet() {
        String Retrive_Material_OrderNo = getIntent().getStringExtra("Retrive_Material_OrderNo");
        String Retrive_Material_BinCount = getIntent().getStringExtra("Retrive_Material_BinCount");

        //Code to Check Location
        Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).pickMaterial(Retrive_Material_OrderNo, Retrive_Material_CodeHolder.getText().toString());
        bando_api.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() == null) {
                    Toast.makeText(RetriveMaterial_PutPallet_3.this, "Server Error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Here", response.body().toString());
                    Quarantine_Model singledata = new Gson().fromJson(response.body(), Quarantine_Model.class);
                    if (singledata.getStatus() != 11) {
                        Intent i = new Intent(RetriveMaterial_PutPallet_3.this, RetriveMaterial_Handover_4.class);
                        i.putExtra("Retrive_Material_OrderNo", Retrive_Material_OrderNo);
                        i.putExtra("Retrive_Material_BinCount", Retrive_Material_BinCount);
                        i.putExtra("Retrive_Material_CodeHolder", Retrive_Material_CodeHolder.getText().toString());
                        startActivity(i);
                    } else if (singledata.getStatus() == 11) {
                        String finalInventoryID = "inventory/bando/status/" + singledata.getPalletNo().toString();
                        storeInventory2(finalInventoryID);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error :" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeInventory2(String finalInventoryID) {
        try {
            BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
            Call<InventoryItem_Model> call = bando_api.getInvnetory(finalInventoryID);
            call.enqueue(new Callback<InventoryItem_Model>() {
                @Override
                public void onResponse(Call<InventoryItem_Model> call, Response<InventoryItem_Model> response) {
                    try {
                        if (response.body() == null) {
                            Toast.makeText(RetriveMaterial_PutPallet_3.this, "Material already added or not found", Toast.LENGTH_LONG).show();
                        } else {
                            if (response.body().getPalletQty() == 0) {
                                Intent i = new Intent(RetriveMaterial_PutPallet_3.this, homescreen.class);
                                startActivity(i);
                            } else {
                                Log.e("Here", String.valueOf(response));
                                Intent i = new Intent(RetriveMaterial_PutPallet_3.this, StoreMaterial_Store_.class);
                                i.putExtra("PalletID", String.valueOf(response.body().getPalletNo()));
                                i.putExtra("Part", String.valueOf(response.body().getMaterialId()));
                                i.putExtra("Qty", String.valueOf(response.body().getPalletQty()));
                                i.putExtra("Location", String.valueOf(response.body().getLocationId()));
                                if(response.body().getStatus()==12){
                                    i.putExtra("reasone2use", "Quarantine");

                                }else{
                                    i.putExtra("reasone2use", "Restore");
                                }
                                startActivity(i);
                            }
                        }

                    } catch (Exception e) {
                        Log.e("Here", "Error: " + e);

                    }
                }

                @Override
                public void onFailure(Call<InventoryItem_Model> call, Throwable t) {
                    Log.e("Here", "onFailure: " + t.getMessage());

                }
            });

        } catch (Exception e) {
            Log.e("Here", String.valueOf(e));
        }
    }
}
