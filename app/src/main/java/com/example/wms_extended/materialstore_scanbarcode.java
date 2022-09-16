package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wms_extended.models.InventoryItem_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class materialstore_scanbarcode extends AppCompatActivity {

    //Declare Variables
    TextView Barcode_Holder;
    Button Store_Material_Holder;
    ImageView Store_Material_Img_Holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialstore_scanbarcode);

        //find view by ID
        Store_Material_Holder = findViewById(R.id.Store_Material_Scan_Barcode_Btn);
        Store_Material_Img_Holder = findViewById(R.id.Store_Material_Scan_Barcode_ImgBtn);
        Barcode_Holder = findViewById(R.id.barcodeHolder);
        String barcodeID = getIntent().getStringExtra("BarcodeValue");
        Barcode_Holder.setText(barcodeID, null);


        //Send pallet for scanning
        Store_Material_Holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Barcode_Holder.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Enter valid barcode value!", Toast.LENGTH_SHORT).show();
                } else {
                    String finalInventoryID = "inventory/bando/" + Barcode_Holder.getText();
                    storeInventory(finalInventoryID);
                }

            }
        });
    }

    void storeInventory(String finalInventoryID) {
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<InventoryItem_Model> call = bando_api.getInvnetory(finalInventoryID);
        call.enqueue(new Callback<InventoryItem_Model>() {
            @Override
            public void onResponse(Call<InventoryItem_Model> call, Response<InventoryItem_Model> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(materialstore_scanbarcode.this, "Material already added or not found", Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("Here", String.valueOf(response.body()));
                        Intent i = new Intent(materialstore_scanbarcode.this, StoreMaterial_Store_.class);
                        i.putExtra("PalletID", String.valueOf(response.body().getPalletNo()));
                        i.putExtra("Part", String.valueOf(response.body().getMaterialId()));
                        i.putExtra("Qty", String.valueOf(response.body().getPalletQty()));
                        i.putExtra("Location", String.valueOf(response.body().getLocationId()));
                        if(response.body().getStatus()==12){
                            i.putExtra("reasone2use", "Quarantine");

                        }else{
                            i.putExtra("reasone2use", "Storage");
                        }
                        startActivity(i);
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
    }
}


