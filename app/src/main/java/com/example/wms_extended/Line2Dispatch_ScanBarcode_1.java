package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wms_extended.models.DispatchReq_Model;
import com.example.wms_extended.models.LocationItem_Response_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Line2Dispatch_ScanBarcode_1 extends AppCompatActivity {

    Button Line2Dispatch_Ok;
    EditText Line2Dispatch_Barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line2_dispatch_scan_barcode1);

        Line2Dispatch_Ok = findViewById(R.id.Line2Dispatch_Ok);
        Line2Dispatch_Barcode = findViewById(R.id.Line2Dispatch_Barcode);

        Line2Dispatch_Ok.setOnClickListener(view -> scanDispatch());

    }

    private void scanDispatch() {
        String ID = Line2Dispatch_Barcode.getText().toString();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String UserID = pref.getString("UserID", null);

        if (ID.equals("")) {
            Toast.makeText(this, "Barcode value required!", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<DispatchReq_Model> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).dispatchCheck(UserID, ID);
        bando_api.enqueue(new Callback<DispatchReq_Model>() {
            @Override
            public void onResponse(@NonNull Call<DispatchReq_Model> call, @NonNull Response<DispatchReq_Model> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(Line2Dispatch_ScanBarcode_1.this, "No response from server.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!response.body().getMessage().equals("Success")) {
                            Toast.makeText(Line2Dispatch_ScanBarcode_1.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(Line2Dispatch_ScanBarcode_1.this, "Retrieval done", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Line2Dispatch_ScanBarcode_1.this, Line2Dispatch_MaterialPick_2.class);
                        i.putExtra("getMessage", response.body().getMessage());
                        i.putExtra("getRequestNo", response.body().getRequestNo());
                        i.putExtra("getNavisionBarcode", response.body().getNavisionBarcode());
                        i.putExtra("getNavCode", response.body().getNavCode());
                        i.putExtra("getDispatchQty", response.body().getDispatchQty().toString());
                        i.putExtra("getUserId", response.body().getUserId());
                        startActivity(i);
                    }
                } catch (Exception e) {
                    Log.e("Here", e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DispatchReq_Model> call, @NonNull Throwable t) {
                Log.e("Here", "Great Try" + t);
                Toast.makeText(Line2Dispatch_ScanBarcode_1.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}