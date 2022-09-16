package com.example.wms_extended.models;

public class CreateRequest_Model {

    String requestedBy;
    String materialId;
    Integer binQty;
    Integer binCount;
    Integer quantity;

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
}
