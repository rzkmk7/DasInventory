package com.XyzStudio.dasinventory;

public class HistoryData {
    public HistoryData(){

    }
    public HistoryData(
            String namaBarang,
            Integer ambil,
            String dateAmbil,
            Integer stokAkhir) {
        this.namaBarang = namaBarang;
        this.ambil = ambil;
        this.dateAmbil = dateAmbil;
        this.stokAkhir = stokAkhir;
    }


    private String namaBarang;
    private Integer ambil;
    private String dateAmbil;
    private Integer stokAkhir;

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Integer getAmbil() {
        return ambil;
    }

    public void setAmbil(Integer ambil) {
        this.ambil = ambil;
    }

    public String getDateAmbil() {
        return dateAmbil;
    }

    public void setDateAmbil(String dateAmbil) {
        this.dateAmbil = dateAmbil;
    }

    public Integer getStokAkhir() {
        return stokAkhir;
    }

    public void setStokAkhir(Integer stokAkhir) {
        this.stokAkhir = stokAkhir;
    }
}
