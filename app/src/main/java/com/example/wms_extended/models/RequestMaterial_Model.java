package com.example.wms_extended.models;

import com.google.gson.JsonElement;

public class RequestMaterial_Model {
    Integer nid;
    String orderNo;
    String requestedBy;
    String materialId;
    Integer binQty;
    Integer binCount;
    Integer quantity;
    String textStatus;
    Integer status;

    public RequestMaterial_Model(Integer nid, String orderNo, String requestedBy, String materialId, Integer binQty, Integer binCount, Integer quantity, String textStatus, Integer status) {
        this.nid = nid;
        this.orderNo = orderNo;
        this.requestedBy = requestedBy;
        this.materialId = materialId;
        this.binQty = binQty;
        this.binCount = binCount;
        this.quantity = quantity;
        this.textStatus = textStatus;
        this.status = status;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Integer getBinQty() {
        return binQty;
    }

    public void setBinQty(Integer binQty) {
        this.binQty = binQty;
    }

    public Integer getBinCount() {
        return binCount;
    }

    public void setBinCount(Integer binCount) {
        this.binCount = binCount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
