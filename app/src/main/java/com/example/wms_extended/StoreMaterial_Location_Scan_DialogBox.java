package com.example.wms_extended;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.wms_extended.models.LocationItem_Model;
import com.example.wms_extended.models.LocationItem_Response_Model;
import com.example.wms_extended.rest_api.BANDO_API;
import com.example.wms_extended.rest_api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreMaterial_Location_Scan_DialogBox extends AppCompatDialogFragment {
    private Button Done;
    private Button Cancel;
    private TextView value;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Open Modal
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_store_material_location_scan, null);
        builder.setView(view);

        //Find view by ID
        Button cancel = view.findViewById(R.id.Store_Material_Cancel_Location_btn);
        ImageView scan = view.findViewById(R.id.Store_Material_Scan_Barcode_ImgBtn);
        Button ok = view.findViewById(R.id.Store_Material_Done_Location_btn);
        EditText resultEditText = view.findViewById(R.id.resultEditText);


        //Button Functions OK
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code to POST Inventory

                SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                LocationItem_Model locationItem_model = new LocationItem_Model();
                locationItem_model.setInventoryId(pref.getString("PalletID", null));
                locationItem_model.setLocationId(String.valueOf(resultEditText.getText()));
                Log.e("Here", pref.getString("reasone2use", null));
                Log.e("Here", pref.getString("PalletID", null));
                Log.e("Here", pref.getString("PalletID", null));
                Gson mGson = new Gson();
                String data = mGson.toJson(locationItem_model);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);

                Call<JsonObject> bando_api = RetrofitClient.getRetrofitInstance().create(BANDO_API.class).storeMaterial(pref.getString("PalletID", null), pref.getString("reasone2use", null), locationItem_model);
                bando_api.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body() == null) {
                            Toast.makeText(getContext(), "No Response from Server", Toast.LENGTH_SHORT).show();
                        } else {
                            LocationItem_Response_Model resp = new Gson().fromJson(response.body(), LocationItem_Response_Model.class);
                            if (resp.getReport().equals("Success")) {
                                Toast.makeText(getContext(), "Material Stored Successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getContext(), homescreen.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getContext(), "Wrong Location", Toast.LENGTH_LONG).show();
                            }
                            Log.e("Here", String.valueOf(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("Here", String.valueOf(t));
                    }
                });
            }
        });
        //Cancel Function
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setView(view);
                Intent i = new Intent(getContext(), homescreen.class);
                startActivity(i);
            }
        });
        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Toast.makeText(getContext(), "sdfsfsfs", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
//            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    String getBarVal() {
        //get barcode value
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String BarcodeVal = pref.getString("BarValue", null);
        Log.e("Here", BarcodeVal);
        return BarcodeVal;
    }
}
