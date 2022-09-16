package com.example.wms_extended.models;

import java.util.ArrayList;

public class DispatchReq_Model {

    String message;
    String requestNo;
    String navisionBarcode;
    String navCode;
    Integer dispatchQty;
    String userId;

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNavisionBarcode() {
        return navisionBarcode;
    }

    public void setNavisionBarcode(String navisionBarcode) {
        this.navisionBarcode = navisionBarcode;
    }

    public String getNavCode() {
        return navCode;
    }

    public void setNavCode(String navCode) {
        this.navCode = navCode;
    }

    public Integer getDispatchQty() {
        return dispatchQty;
    }

    public void setDispatchQty(Integer dispatchQty) {
        this.dispatchQty = dispatchQty;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
