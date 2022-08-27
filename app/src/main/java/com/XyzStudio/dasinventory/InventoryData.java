package com.XyzStudio.dasinventory;

public class InventoryData {

    public InventoryData(){

    }
    public InventoryData(String namaBarang, String jmlStok, String type, String ket, String date, String stokAkhir) {
        this.namaBarang = namaBarang;
        this.jmlStok = jmlStok;
        this.type = type;
        this.ket = ket;
        this.date = date;
        this.stokAkhir = stokAkhir;
    }


    private String namaBarang;
    private String jmlStok;
    private String type;
    private String ket;
    private String date;
    private String stokAkhir;
    private String Key;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStokAkhir() {
        return stokAkhir;
    }

    public void setStokAkhir(String stokAkhir) {
        this.stokAkhir = stokAkhir;
    }



    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }



    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getJmlStok() {
        return jmlStok;
    }

    public void setJmlStok(String jmlStok) {
        this.jmlStok = jmlStok;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }



}
