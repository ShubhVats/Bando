package com.example.wms_extended.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms_extended.R;
import com.example.wms_extended.models.RequestMaterial_Model;

import java.util.ArrayList;

public class RequestMaterial_Adapter extends RecyclerView.Adapter<RequestMaterial_Adapter.MyViewHolder> {

    private ArrayList<RequestMaterial_Model> RequestMaterial_ModelList;


    public RequestMaterial_Adapter(ArrayList<RequestMaterial_Model> RequestMaterial_ModelList) {
        this.RequestMaterial_ModelList = RequestMaterial_ModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView orderNum;
        private TextView matID_binQty;
        private TextView textStatus;

        public MyViewHolder(final View view) {
            super(view);
            orderNum = view.findViewById(R.id.orderNum);
            matID_binQty = view.findViewById(R.id.matID_binQty);
            textStatus = view.findViewById(R.id.textStatus);
        }

    }

    @NonNull
    @Override
    public RequestMaterial_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.materialrequest_requestitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestMaterial_Adapter.MyViewHolder holder, int position) {
        RequestMaterial_Model requestMaterial_model = RequestMaterial_ModelList.get(position);
        holder.orderNum.setText(requestMaterial_model.getOrderNo());
        holder.matID_binQty.setText(requestMaterial_model.getMaterialId());
        holder.textStatus.setText(requestMaterial_model.getTextStatus());

    }

    @Override
    public int getItemCount() {
        return RequestMaterial_ModelList.size();
    }
}
