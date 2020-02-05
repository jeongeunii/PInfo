package com.cookandroid.pinfo.LMain;

public class Pill {
    private String itemSeq; // 제품 번호
    private String itemName; // 제품명
    private String entpName; // 업체명
    private String stoMeth; // 저장방법
    private String validTerm; // 유효기간

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEntpName() {
        return entpName;
    }

    public void setEntpName(String entpName) {
        this.entpName = entpName;
    }

    public String getStoMeth() {
        return stoMeth;
    }

    public void setStoMeth(String stoMeth) {
        this.stoMeth = stoMeth;
    }

    public String getValidTerm() {
        return validTerm;
    }

    public void setValidTerm(String validTerm) {
        this.validTerm = validTerm;
    }

    @Override
    public String toString() {
        return "\n" + itemName + "\n";
    }

    public String listString() {
        return "\n제품명 : " + itemName + "\n" +
                "업체명 : " + entpName + "\n" +
                "저장 방법 : " + stoMeth + "\n" +
                "유효기간 : " + validTerm + "\n";
    }
}
