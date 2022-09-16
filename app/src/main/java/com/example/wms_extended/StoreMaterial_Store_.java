package com.example.wms_extended;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StoreMaterial_Store_ extends AppCompatActivity {

    TextView Pallet_Holder, Part_Holder, Qty_Holder, Location_Holder;
    Button StoreMaterial_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_material_store);

        Pallet_Holder = findViewById(R.id.StoreMaterial_Pallet);
        Part_Holder = findViewById(R.id.StoreMaterial_Part);
        Qty_Holder = findViewById(R.id.StoreMaterial_Qty);
        Location_Holder = findViewById(R.id.StoreMaterial_Location);
        StoreMaterial_Btn = findViewById(R.id.StoreMaterial_Btn);

        //
        String PalletID = getIntent().getExtras().getString("PalletID");
        String Part = getIntent().getExtras().getString("Part");
        String Qty = getIntent().getExtras().getString("Qty");
        String Location = getIntent().getExtras().getString("Location");
        String reasone2use = getIntent().getStringExtra("reasone2use");

        Pallet_Holder.setText(PalletID);
        Part_Holder.setText(Part);
        Qty_Holder.setText(Qty);
        Location_Holder.setText(Location);

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("PalletID", PalletID); // Storing string
        editor.putString("Location", Location); // Storing string
        editor.putString("reasone2use",reasone2use);
        editor.apply();


        StoreMaterial_Btn.setOnClickListener(view -> openDialog());
        //get inventory ID
//        String URL = "inventory/bando/";
//        String InventoryID = getIntent().getStringExtra("InventoryID");
//        String finalInventoryID = URL + InventoryID;;


//        StoreMaterial_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
//                Call<InventoryItem_Model> call = bando_api.getInvnetory(finalInventoryID);
//
//                call.enqueue(new Callback<InventoryItem_Model>() {
//                    @Override
//                    public void onResponse(Call<InventoryItem_Model> call, Response<InventoryItem_Model> response) {
//                        try {
//
//                            if (response.body() == null) {
//                                Toast.makeText(StoreMaterial_Store_.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
//                            } else {
//                                InventoryItem_Model Data = response.body();
//                                Pallet_Holder.setText(Data.getPalletNo());
//                                Log.e("Here", "Great Success!" + Data);
//                                Log.e("Here", "Great Success!" + response.body().getPalletNo());
//                                Intent i = new Intent(StoreMaterial_Store_.this, homescreen.class);
//                            }
//
//                        } catch (Exception e) {
//                            Log.e("Here", "Error: "+ e.toString());
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<InventoryItem_Model> call, Throwable t) {
//                        Log.e("Here", "onFailure: " + t.getMessage());
//
//                    }
//                });
//            }
//        });
    }

    public void openDialog() {
        StoreMaterial_Location_Scan_DialogBox dialog = new StoreMaterial_Location_Scan_DialogBox();
        dialog.show(getSupportFragmentManager(), "Location Dialog");
    }

}