package com.example.wms_extended.models;

public class FullInventory_Model {

    String materialId;
    Integer binQty;
    Integer binCount;
    Integer totalQty;
    String details;


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

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}
