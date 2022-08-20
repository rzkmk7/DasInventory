package com.XyzStudio.dasinventory;

public class LaporanData {
    public String tgl_laporan;
    public String tempat_laporan;

    public LaporanData(){

    }
     public LaporanData(String tgl_laporan, String tempat_laporan) {
        this.tgl_laporan = tgl_laporan;
        this.tempat_laporan = tempat_laporan;
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
