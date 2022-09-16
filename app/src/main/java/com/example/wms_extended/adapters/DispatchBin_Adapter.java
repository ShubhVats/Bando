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
import com.example.wms_extended.models.DispatchBin_Model;

import java.util.ArrayList;

public class DispatchBin_Adapter extends RecyclerView.Adapter<DispatchBin_Adapter.MyViewHolder> {

    private ArrayList<DispatchBin_Model> DispatchBin_ModelList;

    public DispatchBin_Adapter(ArrayList<DispatchBin_Model> DispatchBin_ModelList) {
        this.DispatchBin_ModelList = DispatchBin_ModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Barcode;
        private TextView Qty;
        private Button removeButton;

        public MyViewHolder(final View view) {
            super(view);
            Barcode = view.findViewById(R.id.orderNum);
            Qty = view.findViewById(R.id.textStatus);
            removeButton = view.findViewById(R.id.textStatus);
        }
    }

    @NonNull
    @Override
    public DispatchBin_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.line2dispatch_dispatchbin, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DispatchBin_Adapter.MyViewHolder holder, int position) {
        DispatchBin_Model dispatchBin_model = DispatchBin_ModelList.get(position);
        holder.Barcode.setText(dispatchBin_model.getBarcode());
        holder.Qty.setText(dispatchBin_model.getQty());
        holder.removeButton.setText("remove");
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DispatchBin_ModelList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());

                } catch (Exception e) {
                    Log.e("Here", String.valueOf(e));
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return DispatchBin_ModelList.size();

    }
}
