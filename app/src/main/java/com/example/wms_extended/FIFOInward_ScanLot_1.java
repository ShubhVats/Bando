package com.example.wms_extended;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wms_extended.models.Lot_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FIFOInward_ScanLot_1 extends AppCompatActivity {

    Button FIFOBeltInward_ScanLot_OK;
    TextView FIFOBeltInward_Title1, FIFOBeltInward_Title2, FIFOBeltInward_Title3;
    EditText FIFOBeltInward_Barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifoinward_scan_lot1);

        FIFOBeltInward_ScanLot_OK = findViewById(R.id.FIFOBeltInward_ScanLot_OK);
        FIFOBeltInward_Title1 = findViewById(R.id.FIFOBeltInward_Title1);
        FIFOBeltInward_Title2 = findViewById(R.id.FIFOBeltInward_Title2);
        FIFOBeltInward_Title3 = findViewById(R.id.FIFOBeltInward_Title3);
        FIFOBeltInward_Barcode = findViewById(R.id.FIFOBeltInward_Barcode);

        String parent = getIntent().getStringExtra("parent");

        if (parent.equals("FIFOBeltInward")) {
            FIFOBeltInward_Title1.setText("Fifo Belt Storage");
            FIFOBeltInward_Title2.setText("Scan Lot");
            FIFOBeltInward_Title3.setText("Lot Barcode");
            FIFOBeltInward_ScanLot_OK.setOnClickListener(view -> {
                scanLotInward();
            });
        } else {
            FIFOBeltInward_Title1.setText("Fifo Belt Retrival");
            FIFOBeltInward_Title2.setText("Scan Barcode");
            FIFOBeltInward_Title3.setText("Tie Barcode");
            FIFOBeltInward_ScanLot_OK.setOnClickListener(view -> {
                scanLotOutward();
            });
        }
    }

    private void scanLotInward() {
        String ID = FIFOBeltInward_Barcode.getText().toString();
        if (ID.equals("")) {
            Toast.makeText(this, "Barcode value required!", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "belt_section/lot_info/";
        String finalUrl = url + ID;
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<Lot_Model> call = bando_api.getLot(finalUrl);
        call.enqueue(new Callback<Lot_Model>() {
            @Override
            public void onResponse(Call<Lot_Model> call, Response<Lot_Model> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(getApplicationContext(), "No Lot Found!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Here", "1" + String.valueOf(response.body().getNid()));
                        Log.e("Here", "2" + String.valueOf(response.body().getLotCardNo()));
                        Log.e("Here", "3" + String.valueOf(response.body().getSpecNo()));
                        Log.e("Here", "4" + String.valueOf(response.body().getSlabNo()));
                        Log.e("Here", "5" + String.valueOf(response.body().getSlabProdDate()));
                        Log.e("Here", "6" + String.valueOf(response.body().getNavCode()));
                        Log.e("Here", "7" + String.valueOf(response.body().getModel()));
                        Log.e("Here", "8" + String.valueOf(response.body().getStdQty()));
                        Log.e("Here", "9" + String.valueOf(response.body().getActQty()));
                        Log.e("Here", "10" + String.valueOf(response.body().getOkQty()));
                        Log.e("Here", "11" + String.valueOf(response.body().getNgQty()));
                        Log.e("Here", "12" + String.valueOf(response.body().getCreateDate()));
                        Log.e("Here", "13" + String.valueOf(response.body().getStatus()));
                        Intent i = new Intent(FIFOInward_ScanLot_1.this, FIFOInward_StoreBelt_2.class);
                        i.putExtra("parent", "FIFOBeltInward");
                        i.putExtra("getNid", response.body().getNid().toString());
                        i.putExtra("getLotCardNo", response.body().getLotCardNo().toString());
                        i.putExtra("getSpecNo", response.body().getSpecNo().toString());
                        i.putExtra("getSlabNo", response.body().getSlabNo().toString());
                        i.putExtra("getSlabProdDate", response.body().getSlabProdDate().toString());
                        i.putExtra("getNavCode", response.body().getNavCode().toString());
                        i.putExtra("getModel", response.body().getModel().toString());
                        i.putExtra("getStdQty", response.body().getStdQty().toString());
                        if (response.body().getActQty() == null) {
                            i.putExtra("getActQty", "0");

                        } else {
                            i.putExtra("getActQty", response.body().getActQty().toString());
                        }
                        i.putExtra("getOkQty", response.body().getOkQty().toString());
                        i.putExtra("getNgQty", response.body().getNgQty().toString());
                        i.putExtra("getCreateDate", response.body().getCreateDate().toString());
                        i.putExtra("getStatus", response.body().getStatus().toString());
                        startActivity(i);
                    }
                } catch (Exception e) {
                    Log.e("Here", e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Lot_Model> call, Throwable t) {
                Log.e("Here", "Great Try" + t.toString());
            }
        });
    }

    private void scanLotOutward() {
        String ID = FIFOBeltInward_Barcode.getText().toString();
        if (ID.equals("")) {
            Toast.makeText(this, "Barcode value required!", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "/api/belt_section/tie_info/";
        String finalUrl = url + ID;
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<Lot_Model> call = bando_api.getLot(finalUrl);
        call.enqueue(new Callback<Lot_Model>() {
            @Override
            public void onResponse(Call<Lot_Model> call, Response<Lot_Model> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(getApplicationContext(), "No Tie Found!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Here", "1" + String.valueOf(response.body().getNid()));
                        Log.e("Here", "2" + String.valueOf(response.body().getLotCardNo()));
                        Log.e("Here", "3" + String.valueOf(response.body().getSpecNo()));
                        Log.e("Here", "4" + String.valueOf(response.body().getSlabNo()));
                        Log.e("Here", "5" + String.valueOf(response.body().getSlabProdDate()));
                        Log.e("Here", "6" + String.valueOf(response.body().getNavCode()));
                        Log.e("Here", "7" + String.valueOf(response.body().getModel()));
                        Log.e("Here", "8" + String.valueOf(response.body().getStdQty()));
                        Log.e("Here", "9" + String.valueOf(response.body().getActQty()));
                        Log.e("Here", "10" + String.valueOf(response.body().getOkQty()));
                        Log.e("Here", "11" + String.valueOf(response.body().getNgQty()));
                        Log.e("Here", "12" + String.valueOf(response.body().getCreateDate()));
                        Log.e("Here", "13" + String.valueOf(response.body().getStatus()));
                        Intent i = new Intent(FIFOInward_ScanLot_1.this, FIFOInward_StoreBelt_2.class);
                        i.putExtra("parent", "FIFOBeltOutward");
                        i.putExtra("getNid", response.body().getNid().toString());
                        i.putExtra("getLotCardNo", response.body().getLotCardNo().toString());
                        i.putExtra("getSpecNo", response.body().getSpecNo().toString());
                        i.putExtra("getSlabNo", response.body().getSlabNo().toString());
                        i.putExtra("getSlabProdDate", response.body().getSlabProdDate().toString());
                        i.putExtra("getNavCode", response.body().getNavCode().toString());
                        i.putExtra("getModel", response.body().getModel().toString());
                        i.putExtra("getStdQty", response.body().getStdQty().toString());
                        if (response.body().getActQty() == null) {
                            i.putExtra("getActQty", "0");

                        } else {
                            i.putExtra("getActQty", response.body().getActQty().toString());
                        }
                        i.putExtra("getOkQty", response.body().getOkQty().toString());
                        i.putExtra("getNgQty", response.body().getNgQty().toString());
                        i.putExtra("getCreateDate", response.body().getCreateDate().toString());
                        i.putExtra("getStatus", response.body().getStatus().toString());
                        startActivity(i);
                    }
                } catch (Exception e) {
                    Log.e("Here", e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Lot_Model> call, Throwable t) {
                Log.e("Here", "Great Try" + t.toString());
            }
        });

    }


}