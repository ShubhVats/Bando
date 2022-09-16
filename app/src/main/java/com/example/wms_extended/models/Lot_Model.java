package com.example.wms_extended.models;

public class Lot_Model {

    Integer nid;
    String lotCardNo;
    String specNo;
    String slabNo;
    String slabProdDate;
    String navCode;
    String model;
    Integer stdQty;
    Integer actQty;
    Integer okQty;
    Integer ngQty;
    String createDate;
    Integer status;
    String textStatus;


    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getLotCardNo() {
        return lotCardNo;
    }

    public void setLotCardNo(String lotCardNo) {
        this.lotCardNo = lotCardNo;
    }

    public String getSpecNo() {
        return specNo;
    }

    public void setSpecNo(String specNo) {
        this.specNo = specNo;
    }

    public String getSlabNo() {
        return slabNo;
    }

    public void setSlabNo(String slabNo) {
        this.slabNo = slabNo;
    }

    public String getSlabProdDate() {
        return slabProdDate;
    }

    public void setSlabProdDate(String slabProdDate) {
        this.slabProdDate = slabProdDate;
    }

    public String getNavCode() {
        return navCode;
    }

    public void setNavCode(String navCode) {
        this.navCode = navCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getStdQty() {
        return stdQty;
    }

    public void setStdQty(Integer stdQty) {
        this.stdQty = stdQty;
    }

    public Integer getActQty() {
        return actQty;
    }

    public void setActQty(Integer actQty) {
        this.actQty = actQty;
    }

    public Integer getOkQty() {
        return okQty;
    }

    public void setOkQty(Integer okQty) {
        this.okQty = okQty;
    }

    public Integer getNgQty() {
        return ngQty;
    }

    public void setNgQty(Integer ngQty) {
        this.ngQty = ngQty;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }
}
