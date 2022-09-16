package com.example.wms_extended.models;

import com.google.gson.JsonElement;

public class InventoryItem_Model {
    public InventoryItem_Model(JsonElement element) {
        this.nid = getNid();
        this.grnMat = getGrnMat();
        this.lotNumber = getLotNumber();
        this.palletNo = getPalletNo();
        this.materialId = getMaterialId();
        this.palletQty = getPalletQty();
        this.locationId = getLocationId();
        this.createDate = getCreateDate();
        this.textStatus = getTextStatus();
        this.status = getStatus();
        this.binCount = getBinCount();
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getGrnMat() {
        return grnMat;
    }

    public void setGrnMat(String grnMat) {
        this.grnMat = grnMat;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Integer getPalletQty() {
        return palletQty;
    }

    public void setPalletQty(Integer palletQty) {
        this.palletQty = palletQty;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    Integer nid;
    String grnMat;
    String lotNumber;
    String palletNo;
    String materialId;
    Integer palletQty;
    Integer binCount;
    String locationId;
    String createDate;
    String textStatus;
    Integer status;

    public Integer getBinCount() {
        return binCount;
    }

    public void setBinCount(Integer binCount) {
        this.binCount = binCount;
    }
}
