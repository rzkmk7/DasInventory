package com.XyzStudio.dasinventory;

public class LaporanData {
    public String tgl_laporan;
    public String tempat_laporan;
    public String type_mesin;
    public String att;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String key;

    public String getType_mesin() {
        return type_mesin;
    }

    public void setType_mesin(String type_mesin) {
        this.type_mesin = type_mesin;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public LaporanData(){

    }
     public LaporanData(String tgl_laporan, String tempat_laporan, String type_mesin, String att) {
        this.tgl_laporan = tgl_laporan;
        this.tempat_laporan = tempat_laporan;
         this.type_mesin = type_mesin;
         this.att = att;
    }

    public String getTgl_laporan() {

        return tgl_laporan;
    }

    public void setTgl_laporan(String tgl_laporan) {

        this.tgl_laporan = tgl_laporan;
    }

    public String getTempat_laporan() {

        return tempat_laporan;
    }

    public void setTempat_laporan(String tempat_laporan) {

        this.tempat_laporan = tempat_laporan;
    }
}
