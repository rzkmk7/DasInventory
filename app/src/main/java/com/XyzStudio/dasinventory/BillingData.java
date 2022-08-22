package com.XyzStudio.dasinventory;

public class BillingData {

    private String namaBarangBil;
    private String jmlStokBil;
    private String typeBil;
    private String ketBil;

    public BillingData(){

    }
    public BillingData(String namaBarangBil, String jmlStokBil, String typeBil, String ketBil) {
        this.namaBarangBil = namaBarangBil;
        this.jmlStokBil = jmlStokBil;
        this.typeBil = typeBil;
        this.ketBil = ketBil;
    }
    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    private String Key;
    public String getNamaBarangBil() {
        return namaBarangBil;
    }

    public void setNamaBarangBil(String namaBarangBil) {
        this.namaBarangBil = namaBarangBil;
    }

    public String getJmlStokBil() {
        return jmlStokBil;
    }

    public void setJmlStokBil(String jmlStokBil) {
        this.jmlStokBil = jmlStokBil;
    }

    public String getTypeBil() {
        return typeBil;
    }

    public void setTypeBil(String typeBil) {
        this.typeBil = typeBil;
    }

    public String getKetBil() {
        return ketBil;
    }

    public void setKetBil(String ketBil) {
        this.ketBil = ketBil;
    }







}
