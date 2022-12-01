package com.deskita.common.entity;

public class Income {
    private String TenSP;
    private int SoluongBan;
    private Double thuNhap;
    public String getTenSP() {
        return TenSP;
    }
    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }
    public int getSoluongBan() {
        return SoluongBan;
    }
    public void setSoluongBan(int soluongBan) {
        SoluongBan = soluongBan;
    }
    public Double getThuNhap() {
        return thuNhap;
    }
    public void setThuNhap(Double thuNhap) {
        this.thuNhap = thuNhap;
    }
    public Income(String tenSP, int soluongBan, Double thuNhap) {
        TenSP = tenSP;
        SoluongBan = soluongBan;
        this.thuNhap = thuNhap;
    }
    public Income() {
    }
    
}
