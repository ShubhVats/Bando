package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms_extended.adapters.BarcodeValue_Adapter;
import com.example.wms_extended.models.BarcodeValue_Model;
import com.example.wms_extended.models.InventoryItem_Model;
import com.example.wms_extended.models.LocationItem_Response_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetriveMaterial_Handover_4 extends AppCompatActivity {
    private ArrayList<BarcodeValue_Model> barcodeValue_modelArrayList;
    private RecyclerView recyclerView;
    Button Retrive_Material_Add, Retrive_Material_Deliver_OK, Retrive_Material_Home;
    TextView Retrive_Material_Barcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_material_handover4);
        Retrive_Material_Add = findViewById(R.id.Retrive_Material_Add);
        Retrive_Material_Barcode = findViewById(R.id.Retrive_Material_Barcode);
        Retrive_Material_Deliver_OK = findViewById(R.id.Retrive_Material_Deliver_OK);
        recyclerView = findViewById(R.id.Retrive_Material_RecyclerView);
        Retrive_Material_Home = findViewById(R.id.Retrive_Material_Home);

        //creating ArrayList
        barcodeValue_modelArrayList = new ArrayList<>();

        BarcodeValue_Adapter adapter = setAdapter();

        Integer Retrive_Material_BinCount = Integer.valueOf(getIntent().getStringExtra("Retrive_Material_BinCount"));
        Log.e("Here", Retrive_Material_BinCount.toString());
        String Retrive_Material_OrderNo = getIntent().getStringExtra("Retrive_Material_OrderNo");
        String Retrive_Material_CodeHolder = getIntent().getStringExtra("Retrive_Material_CodeHolder");

        Retrive_Material_Home.setOnClickListener(view -> {
            Intent i = new Intent(RetriveMaterial_Handover_4.this, homescreen.class);
            startActivity(i);
        });

        Retrive_Material_Add.setOnClickListener(view -> {
            int q = barcodeValue_modelArrayList.size() + 1;
            String val = Retrive_Material_Barcode.getText().toString();
            for (int i = 0; i < barcodeValue_modelArrayList.size(); i++) {
                String repeat = barcodeValue_modelArrayList.get(i).getBarcode();
                if (repeat.equals(val)) {
                    Toast.makeText(RetriveMaterial_Handover_4.this, "Please read unique barcode!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (val.equals("")) {
                Toast.makeText(RetriveMaterial_Handover_4.this, "Please read unique barcode!", Toast.LENGTH_SHORT).show();
            } else if (barcodeValue_modelArrayList.size() >= Retrive_Material_BinCount) {
                Log.e("Here", barcodeValue_modelArrayList.size() + "   " + Retrive_Material_BinCount);
                Toast.makeText(RetriveMaterial_Handover_4.this, "Bin limit reached!", Toast.LENGTH_SHORT).show();
            } else {

                try {

                    // GET THE RESULT IF ELIGIBLE OR NOT
                    if (checkEligibility(Retrive_Material_OrderNo, Retrive_Material_CodeHolder, val) == 0) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        Boolean res = pref.getBoolean("BooleanRes", true);
                        Log.e("Here", "RES" + res);
                        if (res) {

                            Toast.makeText(RetriveMaterial_Handover_4.this, "Added", Toast.LENGTH_SHORT).show();
                            BarcodeValue_Model barcodeValueModel = new BarcodeValue_Model(val, q);
                            barcodeValue_modelArrayList.add(barcodeValueModel);
                            adapter.notifyItemInserted(barcodeValue_modelArrayList.size());
                        } else {
                            Toast.makeText(this, "Bin not added in system", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                    Log.e("Here", e.toString());
                }


            }

        });
        Retrive_Material_Deliver_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postHandOverBin();
            }
        });
    }

    private Integer checkEligibility(String retrive_Material_OrderNo, String retrive_Material_CodeHolder, String val) {
        String url = "/api/rmrequest-picklist2/bando/is_bin_eligible4handover/" + retrive_Material_OrderNo + "/" + retrive_Material_CodeHolder + "/" + val;
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<LocationItem_Response_Model> call = bando_api.getEligible(url);
        call.enqueue(new Callback<LocationItem_Response_Model>() {
            @Override
            public void onResponse(Call<LocationItem_Response_Model> call, Response<LocationItem_Response_Model> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(getApplicationContext(), "No Tie Found!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Here", response.body().getReport().toString());
                        if (Objects.equals(response.body().getReport(), "Success")) {
                            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("BooleanRes", true); // Storing string
                        } else {
                            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("BooleanRes", false); // Storing string
                        }
                    }
                } catch (Exception e) {
                    Log.e("Here", e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LocationItem_Response_Model> call, Throwable t) {
                Log.e("Here", "Great Try" + t.toString());
            }

        });
        return 0;
    }

    private void postHandOverBin() {
        try {

            String Retrive_Material_OrderNo = getIntent().getStringExtra("Retrive_Material_OrderNo");
            String Retrive_Material_BinCount = getIntent().getStringExtra("Retrive_Material_BinCount");
            String Retrive_Material_CodeHolder = getIntent().getStringExtra("Retrive_Material_CodeHolder");
            String[] data = new String[barcodeValue_modelArrayList.size()];
            for (int i = 0; i < barcodeValue_modelArrayList.size(); i++) {
                data[i] = barcodeValue_modelArrayList.get(i).getBarcode();
            }
//        String data = mGson.toJson(users);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, String.valueOf(data));
            for (int i = 0; i < data.length; i++) {
                Log.e("Here", String.valueOf(data[i]));
//                Log.e("Here", String.valueOf(body[i]));

            }
            Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).handOverBin(Retrive_Material_OrderNo, Retrive_Material_CodeHolder, data);
            bando_api.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {

                        if (response.body() == null) {
                            Toast.makeText(RetriveMaterial_Handover_4.this, "Error+123", Toast.LENGTH_SHORT).show();
                        } else {

                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            Log.e("Here", String.valueOf(jsonObject));
                            LocationItem_Response_Model res = new Gson().fromJson(String.valueOf(jsonObject), LocationItem_Response_Model.class);
                            if (res.getReport().equals("Success")) {
                                Toast.makeText(RetriveMaterial_Handover_4.this, "Retrieval done", Toast.LENGTH_SHORT).show();
                                String finalInventoryID = "inventory/bando/status/" + Retrive_Material_CodeHolder;
                                storeInventory(finalInventoryID);
                            } else {
                                Toast.makeText(RetriveMaterial_Handover_4.this, String.valueOf(response.body().get("exception")), Toast.LENGTH_SHORT).show();
                            }

                        }

                    } catch (Exception e) {
                        Log.e("Here", e.toString());
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("Here", "Great Try" + t.toString());
                }
            });
        } catch (Exception e) {
            Log.e("here", String.valueOf(e));
        }
    }

    private BarcodeValue_Adapter setAdapter() {
        BarcodeValue_Adapter adapter = new BarcodeValue_Adapter(barcodeValue_modelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void storeInventory(String finalInventoryID) {
        try {
            BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
            Call<InventoryItem_Model> call = bando_api.getInvnetory(finalInventoryID);
            call.enqueue(new Callback<InventoryItem_Model>() {
                @Override
                public void onResponse(Call<InventoryItem_Model> call, Response<InventoryItem_Model> response) {
                    try {
                        if (response.body() == null) {
                            Toast.makeText(RetriveMaterial_Handover_4.this, "Material already added or not found", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("Here", String.valueOf(response));
                            Intent i = new Intent(RetriveMaterial_Handover_4.this, StoreMaterial_Store_.class);
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