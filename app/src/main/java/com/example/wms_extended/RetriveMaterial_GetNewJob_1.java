package com.example.wms_extended;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetriveMaterial_GetNewJob_1 extends AppCompatActivity {

    Button Retrive_Material_GetNewJobBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_material_get_new_job1);
        Retrive_Material_GetNewJobBtn = findViewById(R.id.Retrive_Material_GetNewJobBtn);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String UserID = pref.getString("UserID", null);
//        Log.e("Here", UserID);


        Retrive_Material_GetNewJobBtn.setOnClickListener(view -> assignToUser(UserID));
    }

    private void assignToUser(String ID) {
        String url = "rmrequest-picklist2/bando/assign2User/";
        String finalUrl = url + ID;
        BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
        Call<JsonObject> call = bando_api.getJob(finalUrl);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    if (response.body() == null) {
                        Toast.makeText(getApplicationContext(), "No Pallet Found!", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("NewJob", "String.valueOf(jsonObject)");
                        Log.e("Here", String.valueOf(jsonObject));
                        Intent i = new Intent(RetriveMaterial_GetNewJob_1.this, RetriveMaterial_PickMaterial_2.class);
                        i.putExtra("NewJob",String.valueOf(jsonObject));
                        startActivity(i);
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

    }
}