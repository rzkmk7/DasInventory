package com.XyzStudio.dasinventory;

public class InventoryData {

    public InventoryData(){

    }
    public InventoryData(String namaBarang, String jmlStok, String type, String ket) {
        this.namaBarang = namaBarang;
        this.jmlStok = jmlStok;
        this.type = type;
        this.ket = ket;
    }

    private String namaBarang;
    private String jmlStok;
    private String type;
    private String ket;

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
