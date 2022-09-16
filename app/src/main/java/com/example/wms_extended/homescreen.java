package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class homescreen extends AppCompatActivity {

    //Declare Variable
    Button Store_Material_Holder, Request_Material_Holder, Retrive_Material_Holder, Hold_Material_Holder, FIFOBeltInward_MaterialBtn, FIFOBeltOutward_MaterialBtn, Line2Dispatch_MaterialBtn, LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        //findViewById
        LogoutBtn = findViewById(R.id.LogoutBtn);
        Store_Material_Holder = findViewById(R.id.Store_MaterialBtn);
        Request_Material_Holder = findViewById(R.id.Request_MaterialBtn);
        Retrive_Material_Holder = findViewById(R.id.Retrive_MaterialBtn);
        Hold_Material_Holder = findViewById(R.id.Hold_MaterialBtn);
        FIFOBeltInward_MaterialBtn = findViewById(R.id.FIFOBeltInward_MaterialBtn);
        FIFOBeltOutward_MaterialBtn = findViewById(R.id.FIFOBeltOutward_MaterialBtn);
        Line2Dispatch_MaterialBtn = findViewById(R.id.Line2Dispatch_MaterialBtn);

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);

        Integer showStore1 = pref.getInt("showUserm1Login", 0);
        Integer showStore2 = pref.getInt("showUserm2", 0);
        Integer showStore3 = pref.getInt("showUserm3", 0);
        Integer showStore4 = pref.getInt("showUserm4", 0);
        Integer showStore5 = pref.getInt("showUserm5", 0);
        Integer showStore6 = pref.getInt("showUserm6", 0);
        Integer showStore7 = pref.getInt("showUserm7", 0);

        if (showStore1 == 1) {
            Store_Material_Holder.setVisibility(View.GONE);
            Log.e("Here",showStore1.toString());
        }
        if (showStore2 == 1) {
            Retrive_Material_Holder.setVisibility(View.GONE);
        }
        if (showStore3 == 1) {
            Request_Material_Holder.setVisibility(View.GONE);
        }
        if (showStore4 == 1) {
            Hold_Material_Holder.setVisibility(View.GONE);
        }
        if (showStore5 == 1) {
            FIFOBeltInward_MaterialBtn.setVisibility(View.GONE);
        }
        if (showStore6 == 1) {
            FIFOBeltOutward_MaterialBtn.setVisibility(View.GONE);
        }
        if (showStore7 == 1) {
            Line2Dispatch_MaterialBtn.setVisibility(View.GONE);
        }


        //Functions
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homescreen.this, login.class);
                finish();
                startActivity(i);
            }
        });
        Store_Material_Holder.setOnClickListener(view -> {
            Intent i = new Intent(homescreen.this, materialstore_scanbarcode.class);
            startActivity(i);
        });

        Request_Material_Holder.setOnClickListener(view -> {
            Intent i = new Intent(homescreen.this, matrequest_managment.class);
            startActivity(i);
        });
        Retrive_Material_Holder.setOnClickListener(view -> {
            Intent i = new Intent(homescreen.this, RetriveMaterial_GetNewJob_1.class);
            startActivity(i);
        });
        Hold_Material_Holder.setOnClickListener(view -> {
            Intent i = new Intent(homescreen.this, HoldMaterial_1.class);
            startActivity(i);
        });
        FIFOBeltInward_MaterialBtn.setOnClickListener(view -> {
            Intent i = new Intent(homescreen.this, FIFOInward_ScanLot_1.class);
            i.putExtra("parent", "FIFOBeltInward");
            startActivity(i);
        });
        FIFOBeltOutward_MaterialBtn.setOnClickListener(view -> {
            Intent i = new Intent(homescreen.this, FIFOInward_ScanLot_1.class);
            i.putExtra("parent", "FIFOBeltOutward");
            startActivity(i);
        });
        Line2Dispatch_MaterialBtn.setOnClickListener(view -> {
            Intent i = new Intent(homescreen.this, Line2Dispatch_ScanBarcode_1.class);
            startActivity(i);
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}