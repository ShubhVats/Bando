package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wms_extended.models.GetNewJob_Model;
import com.google.gson.Gson;

public class RetriveMaterial_PickMaterial_2 extends AppCompatActivity {

    Button Retrive_Material_PickMaterialBtn;
    TextView Retrive_Material_Location, Retrive_Material_Pallet, Retrive_Material_Qty, Retrive_Material_OrderNo, Retrive_Material_BinCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_material_pick_material2);

        Retrive_Material_PickMaterialBtn = findViewById(R.id.Retrive_Material_PickMaterialBtn);
        Retrive_Material_Location = findViewById(R.id.Retrive_Material_Location);
        Retrive_Material_Pallet = findViewById(R.id.Retrive_Material_Pallet);
        Retrive_Material_Qty = findViewById(R.id.Retrive_Material_Qty);
        Retrive_Material_OrderNo = findViewById(R.id.Retrive_Material_OrderNo);
        Retrive_Material_BinCount = findViewById(R.id.Retrive_Material_BinCount);

        pickMat();

        Retrive_Material_PickMaterialBtn.setOnClickListener(view -> {
            Intent i = new Intent(RetriveMaterial_PickMaterial_2.this, RetriveMaterial_PutPallet_3.class);
            i.putExtra("Retrive_Material_OrderNo", Retrive_Material_OrderNo.getText().toString());
            i.putExtra("Retrive_Material_BinCount", Retrive_Material_BinCount.getText().toString());
            startActivity(i);
        });
    }

    private void pickMat() {
        try {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
//            String NewJob = pref.getString("NewJob", null);
            String NewJob = getIntent().getStringExtra("NewJob");
            Log.e("Here", NewJob + "asdasdasdas");

            GetNewJob_Model job = new Gson().fromJson(NewJob, GetNewJob_Model.class);
            Log.e("Here", String.valueOf(job) + "aldnakjsd");
            Retrive_Material_Location.setText(job.getLocationId());
            Retrive_Material_Pallet.setText(job.getPalletNo());
            Retrive_Material_Qty.setText(job.getQuantity().toString());
            Retrive_Material_OrderNo.setText(job.getOrderNo().toString());
            Retrive_Material_BinCount.setText(job.getBinCount().toString());
            Log.e("Here", NewJob);
        } catch (Exception e) {
            Log.e("Here", String.valueOf(e));
        }


    }
}