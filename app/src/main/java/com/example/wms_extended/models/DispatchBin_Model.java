package com.example.wms_extended.models;

public class DispatchBin_Model {
    String Barcode;
    String Qty;

    public DispatchBin_Model(String barcode, String qty) {
        Barcode = barcode;
        Qty = qty;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
