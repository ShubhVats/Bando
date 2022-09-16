package com.example.wms_extended;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FIFOInward_StoreBelt_2 extends AppCompatActivity {

    Button FIFOBeltInward_StoreBelt;
    TextView FIFOBeltInward_Store_Title1, FIFOBeltInward_Store_Title2, FIFOBeltInward_ModelName, FIFOBeltInward_NavCode, FIFOBeltInward_SpecNo, FIFOBeltInward_slabProdDate, FIFOBeltInward_LotCardNo, FIFOBeltInward_Nid;
    EditText FIFOBeltInward_BeltQty;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifoinward_store_belt2);

        FIFOBeltInward_StoreBelt = findViewById(R.id.FIFOBeltInward_StoreBelt);
        FIFOBeltInward_Store_Title1 = findViewById(R.id.FIFOBeltInward_Store_Title1);
        FIFOBeltInward_Store_Title2 = findViewById(R.id.FIFOBeltInward_Store_Title2);
        FIFOBeltInward_ModelName = findViewById(R.id.FIFOBeltInward_ModelName);
        FIFOBeltInward_NavCode = findViewById(R.id.FIFOBeltInward_NavCode);
        FIFOBeltInward_SpecNo = findViewById(R.id.FIFOBeltInward_SpecNo);
        FIFOBeltInward_slabProdDate = findViewById(R.id.FIFOBeltInward_slabProdDate);
        FIFOBeltInward_LotCardNo = findViewById(R.id.FIFOBeltInward_LotCardNo);
        FIFOBeltInward_BeltQty = findViewById(R.id.FIFOBeltInward_BeltQty);
        FIFOBeltInward_Nid = findViewById(R.id.FIFOBeltInward_Nid);

        String parent = getIntent().getStringExtra("parent");

        if (parent.equals("FIFOBeltInward")) {

            String getNid = getIntent().getStringExtra("getNid");
            String getLotCardNo = getIntent().getStringExtra("getLotCardNo");
            String getSpecNo = getIntent().getStringExtra("getSpecNo");
            String getSlabNo = getIntent().getStringExtra("getSlabNo");
            String getSlabProdDate = getIntent().getStringExtra("getSlabProdDate");
            String getNavCode = getIntent().getStringExtra("getNavCode");
            String getModel = getIntent().getStringExtra("getModel");
            String getStdQty = getIntent().getStringExtra("getStdQty");
            String getActQty = getIntent().getStringExtra("getActQty");
            String getOkQty = getIntent().getStringExtra("getOkQty");
            String getNgQty = getIntent().getStringExtra("getNgQty");
            String getCreateDate = getIntent().getStringExtra("getCreateDate");
            String getStatus = getIntent().getStringExtra("getStatus");

            FIFOBeltInward_Store_Title1.setText("Fifo Belt Inward");
            FIFOBeltInward_Store_Title2.setText("Store Belt");
            FIFOBeltInward_StoreBelt.setText("Store");
            FIFOBeltInward_Nid.setText(getNid);
            FIFOBeltInward_ModelName.setText(getModel);
            FIFOBeltInward_NavCode.setText(getNavCode);
            FIFOBeltInward_SpecNo.setText(getSpecNo);
            FIFOBeltInward_slabProdDate.setText(getSlabProdDate);
            FIFOBeltInward_LotCardNo.setText(getLotCardNo);
            FIFOBeltInward_BeltQty.setText(getOkQty);

            FIFOBeltInward_StoreBelt.setOnClickListener(view -> {
                        if (i > 0) {
                            Intent i = new Intent(FIFOInward_StoreBelt_2.this, FIFOInward_StoreMaterial_3.class);
                            i.putExtra("parent", "FIFOBeltInward");
                            i.putExtra("getLotCardNo", getLotCardNo);
                            i.putExtra("FIFOBeltInward_BeltQty", FIFOBeltInward_BeltQty.getText().toString());
                            startActivity(i);
                        } else {
                            Toast.makeText(this, "Sure to proceed.", Toast.LENGTH_SHORT).show();
                        }
                        i++;
                    }
            );
        } else {

            String getNid = getIntent().getStringExtra("getNid");
            String getLotCardNo = getIntent().getStringExtra("getLotCardNo");
            String getSpecNo = getIntent().getStringExtra("getSpecNo");
            String getSlabNo = getIntent().getStringExtra("getSlabNo");
            String getSlabProdDate = getIntent().getStringExtra("getSlabProdDate");
            String getNavCode = getIntent().getStringExtra("getNavCode");
            String getModel = getIntent().getStringExtra("getModel");
            String getStdQty = getIntent().getStringExtra("getStdQty");
            String getActQty = getIntent().getStringExtra("getActQty");
            String getOkQty = getIntent().getStringExtra("getOkQty");
            String getNgQty = getIntent().getStringExtra("getNgQty");
            String getCreateDate = getIntent().getStringExtra("getCreateDate");
            String getStatus = getIntent().getStringExtra("getStatus");


            FIFOBeltInward_Store_Title1.setText("Fifo Belt Retrival");
            FIFOBeltInward_Store_Title2.setText("Retrive Belt");
            FIFOBeltInward_StoreBelt.setText("Retrive");
            FIFOBeltInward_Nid.setText(getNid);
            FIFOBeltInward_ModelName.setText(getModel);
            FIFOBeltInward_NavCode.setText(getNavCode);
            FIFOBeltInward_SpecNo.setText(getSpecNo);
            FIFOBeltInward_slabProdDate.setText(getSlabProdDate);
            FIFOBeltInward_LotCardNo.setText(getLotCardNo);
            FIFOBeltInward_BeltQty.setText(getOkQty);

            FIFOBeltInward_StoreBelt.setOnClickListener(view -> {
                        if (i > 0) {
                            Intent i = new Intent(FIFOInward_StoreBelt_2.this, FIFOInward_StoreMaterial_3.class);
                            i.putExtra("parent", "FIFOBeltOutward");
                            i.putExtra("getLotCardNo", getLotCardNo);
                            i.putExtra("FIFOBeltInward_BeltQty", FIFOBeltInward_BeltQty.getText().toString());
                            startActivity(i);
                        } else {
                            Toast.makeText(this, "Sure to proceed.", Toast.LENGTH_SHORT).show();
                        }
                        i++;
                    }
            );
        }
    }
}