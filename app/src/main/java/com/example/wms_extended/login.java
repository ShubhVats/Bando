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

import com.example.wms_extended.models.Users;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    EditText LoginID_Holder, Password_Holder;
    Button LoginButton_Holder;

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ignored) {
        }
        return "02:00:00:00:00:00";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginID_Holder = findViewById(R.id.Login_LoginID);
        Password_Holder = findViewById(R.id.Login_Password);
        LoginButton_Holder = findViewById(R.id.Login_LoginBtn);

        LoginButton_Holder.setOnClickListener(view -> {
            Users users = new Users();
            users.setUserPwd(Password_Holder.getText().toString());
            users.setUserId(LoginID_Holder.getText().toString());
            users.setDeviceMacId(getMacAddr());
            Gson mGson = new Gson();
            String data = mGson.toJson(users);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, data);
            Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).Login(body);
            bando_api.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    try {

                        if (response.body() == null) {
                            Toast.makeText(login.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                        } else {

                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            Log.e("njfj", "cjcjzks " + jsonObject);
                            Log.e("Here", String.valueOf(jsonObject.getJSONObject("user")));
                            JSONObject j = jsonObject.getJSONObject("user");
                            Log.e("Here", j.getString("userId"));
                            String userRoles = j.getString("userRoles");

                            Intent i = new Intent(login.this, homescreen.class);
                            String userId = j.getString("userId");
//TODO
                            // Saving Data locally
                            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("UserID", userId); // Storing string

                            editor.putInt("showUserm1Login", 0);
                            editor.putInt("showUserm2", 0);
                            editor.putInt("showUserm3", 0);
                            editor.putInt("showUserm4", 0);
                            editor.putInt("showUserm5", 0);
                            editor.putInt("showUserm6", 0);
                            editor.putInt("showUserm7", 0);

                            if (!userRoles.contains("m1")) {
                                editor.putInt("showUserm1Login", 1); // Storing string
                            }
                            if (!userRoles.contains("m2")) {
                                editor.putInt("showUserm2", 1); // Storing string
                            }
                            if (!userRoles.contains("m3")) {
                                editor.putInt("showUserm3", 1); // Storing string
                            }
                            if (!userRoles.contains("m4")) {
                                editor.putInt("showUserm4", 1); // Storing string
                            }
                            if (!userRoles.contains("m5")) {
                                editor.putInt("showUserm5", 1); // Storing string
                            }
                            if (!userRoles.contains("m6")) {
                                editor.putInt("showUserm6", 1); // Storing string
                            }
                            if (!userRoles.contains("m7")) {
                                editor.putInt("showUserm7", 1); // Storing string
                            }
                            editor.apply();

                            startActivity(i);
                        }

                    } catch (Exception e) {
                        Log.e("Here", e.toString());
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Log.e("Here", "Great Try" + t);
                    Toast.makeText(login.this, t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        });

//        LoginButton_Holder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BANDO_API bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class);
//                Call<test_model> call = bando_api.getAllData();
//
//                call.enqueue(new Callback<test_model>() {
//                    @Override
//                    public void onResponse(Call<test_model> call, Response<test_model> response) {
//                        Log.e("Here", "onResponse: code: " + response.code());
//                        ArrayList<test_model.data> data = response.body().getData();
//
//                        for (test_model.data data1 : data) {
//                            Log.e("There", "onResponse: emails : " + data1.getEmail());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<test_model> call, Throwable t) {
//                        Log.e("Here", "onFailure: " + t.getMessage());
//                    }
//                });
//            }
//        });

    }
}