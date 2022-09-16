package com.example.wms_extended.models;

public class LotStore_Model {
    String binNO;
    String tieNo;
    String lotCardNo;
    Integer beltQty;

    public String getBinNO() {
        return binNO;
    }

    public void setBinNO(String binNO) {
        this.binNO = binNO;
    }

    public String getTieNo() {
        return tieNo;
    }

    public void setTieNo(String tieNo) {
        this.tieNo = tieNo;
    }

    public String getLotCardNo() {
        return lotCardNo;
    }

    public void setLotCardNo(String lotCardNo) {
        this.lotCardNo = lotCardNo;
    }

    public Integer getBeltQty() {
        return beltQty;
    }

    public void setBeltQty(Integer beltQty) {
        this.beltQty = beltQty;
    }
}
