package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wms_extended.models.CreateRequest_Model;
import com.example.wms_extended.models.FullInventory_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class matrialreuest_scanbarcode_screen2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner Request_MaterialDropdown;
    EditText Request_MaterialBinCount;
    Button Request_MaterialNewOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrialreuest_scanbarcode_screen2);
        Request_MaterialDropdown = findViewById(R.id.Request_MaterialDropdown);
        Request_MaterialBinCount = findViewById(R.id.Request_MaterialBinCount);
        Request_MaterialNewOk = findViewById(R.id.Request_MaterialNewOk);

        Request_MaterialNewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                String BinCountVal = pref.getString("BinCountVal", null);
                try {
                    String Val = String.valueOf(Request_MaterialBinCount.getText());
                    if (0 > Integer.parseInt(Val) || Integer.parseInt(Val) > Integer.parseInt(BinCountVal)) {
                        Toast.makeText(getApplicationContext(), "Entered Bin Count out of bounds!", Toast.LENGTH_SHORT).show();
                    } else {
                        createRequest();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Some error occurred!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        getInventoryAll();
    }

    private void createRequest() {

        //get cureent runnging class
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String DataToCreateRequest = pref.getString("DataToCreateRequest", null);
        String UserID = pref.getString("UserID", null);

        //convert it into class

        FullInventory_Model fullInventory_model = new Gson().fromJson(DataToCreateRequest, FullInventory_Model.class);
        CreateRequest_Model createRequest_model = new CreateRequest_Model();
        createRequest_model.setRequestedBy(UserID);
        Integer total = Integer.parseInt(String.valueOf(Request_MaterialBinCount.getText())) * fullInventory_model.getBinQty();

        createRequest_model.setBinCount(Integer.parseInt(String.valueOf(Request_MaterialBinCount.getText())));
        createRequest_model.setBinQty(fullInventory_model.getBinQty());
        createRequest_model.setMaterialId(fullInventory_model.getMaterialId());
        createRequest_model.setQuantity(total);

        Log.e("Here", String.valueOf(fullInventory_model.getMaterialId()));
        Log.e("Here", String.valueOf(fullInventory_model.getBinQty()));
        Log.e("Here", String.valueOf(Integer.parseInt(String.valueOf(Request_MaterialBinCount.getText()))));
        Log.e("Here", String.valueOf(total));


        Gson mGson = new Gson();
        String data = mGson.toJson(createRequest_model);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        Log.e("Here", String.valueOf(body));
        Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).createRequest(body);
        bando_api.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {

                    if (response.body() == null) {
                        Toast.makeText(matrialreuest_scanbarcode_screen2.this, "Network Error", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Log.e("Here", String.valueOf(jsonObject.get("report")));
                        if (String.valueOf(jsonObject.get("report")).equals("Success")) {
                            Toast.makeText(getApplicationContext(), "New request generated!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(matrialreuest_scanbarcode_screen2.this, homescreen.class);
                            i.putExtra("materialID", createRequest_model.getMaterialId());
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Inventory Planned", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    Log.e("Here", e.toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Here", "Great Try" + t.toString());
            }
        });
    }

    private void setSpinnerDropdownPart(Spinner spinner, String[] dropdownList) {
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dropdownList);
        spinner.setAdapter(adapter3);
        spinner.setOnItemSelectedListener(this);
    }

    private void getInventoryAll() {
        String finalUrl = "inventory/bando/available-materials4-request";
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<JsonArray> call = bando_api.reItems(finalUrl);
        ArrayList<FullInventory_Model> InventoryData = new ArrayList<>();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(matrialreuest_scanbarcode_screen2.this, "Error", Toast.LENGTH_SHORT).show();
                    } else {
//                        Log.e("There", String.valueOf(response.body()));
                        JsonArray jsonElements = response.body();
                        for (int i = 0; i < jsonElements.size(); i++) {
                            FullInventory_Model singledata = new Gson().fromJson(jsonElements.get(i), FullInventory_Model.class);
                            InventoryData.add(singledata);
//                            Log.e("There", String.valueOf(singledata.getMaterialId()));
                        }
                        String[] MaterialID = new String[InventoryData.size()];
                        for (int i = 0; i < InventoryData.size(); i++) {
                            MaterialID[i] = InventoryData.get(i).getDetails().trim();
                        }

                        //Set the values
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("Inventory", String.valueOf(jsonElements));
                        editor.apply();
                        setSpinnerDropdownPart(Request_MaterialDropdown, MaterialID);//setting the dropdown

                    }

                } catch (Exception e) {
                    Log.e("Here", "Error: " + e);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Here", "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
//        Log.e("Here", String.valueOf(pos));
        ArrayList<FullInventory_Model> InventoryData = new ArrayList<>();

        //get data
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String Inventory = pref.getString("Inventory", null);

        //convert into Jarray
        try {
            JSONArray jsonElements = new JSONArray(Inventory);
            for (int i = 0; i < jsonElements.length(); i++) {
                try {

                    FullInventory_Model singledata = new Gson().fromJson(String.valueOf(jsonElements.get(i)), FullInventory_Model.class);
                    Log.e("Here", String.valueOf(singledata));
                    InventoryData.add(singledata);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Database inconsistency at " + i, Toast.LENGTH_LONG).show();
                    Log.e("Here", String.valueOf(e));
                }
            }
            try {
                editor.putString("BinCountVal", String.valueOf(InventoryData.get(pos).getBinCount()));
                editor.apply();
                Request_MaterialBinCount.setText(String.valueOf(InventoryData.get(pos).getBinCount()));

                Gson gson = new Gson();
                String json = gson.toJson(InventoryData.get(pos));
                editor.putString("DataToCreateRequest", String.valueOf(json));
                editor.apply();

            } catch (Exception e) {
                Log.e("Where", String.valueOf(e));
            }

        } catch (JSONException e) {
            Log.e("Here", String.valueOf(e));
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}