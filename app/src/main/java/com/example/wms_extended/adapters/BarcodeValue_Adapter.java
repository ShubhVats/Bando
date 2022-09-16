package com.example.wms_extended.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms_extended.R;
import com.example.wms_extended.models.BarcodeValue_Model;

import java.util.ArrayList;

public class BarcodeValue_Adapter extends RecyclerView.Adapter<BarcodeValue_Adapter.MyViewHolder> {

    private ArrayList<BarcodeValue_Model> BarcodeValue_ModelList;

    public BarcodeValue_Adapter(ArrayList<BarcodeValue_Model> BarcodeValue_ModelList) {
        this.BarcodeValue_ModelList = BarcodeValue_ModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView barcodeValue;
        private Button removeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barcodeValue = itemView.findViewById(R.id.barcodeValue);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

    @NonNull
    @Override
    public BarcodeValue_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrive_material_barcodeitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BarcodeValue_Adapter.MyViewHolder holder, int position) {
        BarcodeValue_Model barcodeValueModel = BarcodeValue_ModelList.get(position);
        holder.barcodeValue.setText(barcodeValueModel.getBarcode());
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BarcodeValue_ModelList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());

                } catch (Exception e) {
                    Log.e("Here", String.valueOf(e));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return BarcodeValue_ModelList.size();
    }

}
