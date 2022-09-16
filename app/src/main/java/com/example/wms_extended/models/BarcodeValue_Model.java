package com.example.wms_extended.models;

public class BarcodeValue_Model {
    String Barcode;
    Integer BarcodeInt;

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public Integer getBarcodeInt() {
        return BarcodeInt;
    }

    public void setBarcodeInt(Integer barcodeInt) {
        BarcodeInt = barcodeInt;
    }

    public BarcodeValue_Model(String barcode, Integer barcodeInt) {
        Barcode = barcode;
        BarcodeInt = barcodeInt;
    }
}
