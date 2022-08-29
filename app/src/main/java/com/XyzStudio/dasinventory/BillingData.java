package com.XyzStudio.dasinventory;

public class BillingData {


    public String getTypeBil() {
        return typeBil;
    }

    public void setTypeBil(String typeBil) {
        this.typeBil = typeBil;
    }

    public String getSnBil() {
        return snBil;
    }

    public void setSnBil(String snBil) {
        this.snBil = snBil;
    }

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public int getCounterAwalBil() {
        return counterAwalBil;
    }

    public void setCounterAwalBil(int counterAwalBil) {
        this.counterAwalBil = counterAwalBil;
    }

    public int getCounterAkhirBil() {
        return counterAkhirBil;
    }

    public void setCounterAkhirBil(int counterAkhirBil) {
        this.counterAkhirBil = counterAkhirBil;
    }

    public int getTotalBiayaBil() {
        return totalBiayaBil;
    }

    public void setTotalBiayaBil(int totalBiayaBil) {
        this.totalBiayaBil = totalBiayaBil;
    }

    public String getTempatBil() {
        return tempatBil;
    }

    public void setTempatBil(String tempatBil) {
        this.tempatBil = tempatBil;
    }

    private String tempatBil;
    private String typeBil;
    private String snBil;
    private int biaya;
    private int counterAwalBil;
    private int counterAkhirBil;
    private int totalBiayaBil;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    private String Key;

    public BillingData(String tempatBil,String typeBil, String snBil, int biaya, int counterAwalBil, int counterAkhirBil, int totalBiayaBil) {
        this.typeBil = typeBil;
        this.snBil = snBil;
        this.biaya = biaya;
        this.counterAwalBil = counterAwalBil;
        this.counterAkhirBil = counterAkhirBil;
        this.totalBiayaBil = totalBiayaBil;
        this.tempatBil =tempatBil;
    }



    public BillingData(){

    }








}
