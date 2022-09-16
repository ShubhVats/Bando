package com.example.wms_extended;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wms_extended.models.RequestMaterial_Model;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class materialrequest_confirmmatrialdelivery_screen3 extends AppCompatActivity {

    TextView Request_MaterialConfirm_OrderID, Request_MaterialConfirm_Part, Request_MaterialConfirm_Qty, Request_MaterialConfirm_OrderBinCount;
    Button Request_MaterialConfirm_OrderConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialrequest_confirmmatrialdelivery_screen3);


        Request_MaterialConfirm_OrderID = findViewById(R.id.Request_MaterialConfirm_OrderID);
        Request_MaterialConfirm_Part = findViewById(R.id.Request_MaterialConfirm_Part);
        Request_MaterialConfirm_Qty = findViewById(R.id.Request_MaterialConfirm_Qty);
        Request_MaterialConfirm_OrderBinCount = findViewById(R.id.Request_MaterialConfirm_OrderBinCount);
        Request_MaterialConfirm_OrderConfirm = findViewById(R.id.Request_MaterialConfirm_OrderConfirm);

        //Getting ConfirmMaterialDeliveryList data
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String ConfirmMaterialDeliveryList = pref.getString("ConfirmMaterialDeliveryList", null);

        //Marry with materialID
        String materialID = getIntent().getStringExtra("materialID");
        Log.e("Here", materialID);

        //Single Obj
        JSONArray jsonElements = null;
        try {
            jsonElements = new JSONArray(ConfirmMaterialDeliveryList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonElements.length(); i++) {
            try {
                RequestMaterial_Model singledata = new Gson().fromJson(String.valueOf(jsonElements.get(i)), RequestMaterial_Model.class);
                if (singledata.getMaterialId().equals(materialID)) {
                    try {

                        Request_MaterialConfirm_OrderID.setText(singledata.getOrderNo());
                        Request_MaterialConfirm_Part.setText(singledata.getMaterialId());
                        Request_MaterialConfirm_Qty.setText(String.valueOf(singledata.getQuantity()));
                        Request_MaterialConfirm_OrderBinCount.setText(String.valueOf(singledata.getBinCount()));
                    } catch (Exception e) {
                        Log.e("Here", String.valueOf(e));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Request_MaterialConfirm_OrderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Coming in next Update!!", Toast.LENGTH_SHORT).show();
                confirmRequest();
            }
        });
    }

    private void confirmRequest() {
    }

}