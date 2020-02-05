package com.cookandroid.pinfo.LMain;

public class Mother {
    private String itemSeq; // 제품 번호
    private String itemName; // 제품명
    private String entpName; // 업체명
    private String category; // 카테고리
    private String mainIngr; // 주성분
    private String prohbtCon; // 금기 내용

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainIngr() {
        return mainIngr;
    }

    public void setMainIngr(String mainIngr) {
        this.mainIngr = mainIngr;
    }

    public String getProhbtCon() {
        return prohbtCon;
    }

    public void setProhbtCon(String prohbtCon) {
        this.prohbtCon = prohbtCon;
    }

    public String toString() {
        return "\n" + itemName + "\n";
    }

    public String listString() {
        return "\n제품명 : " + itemName + "\n" +
                "업체명 : " + entpName + "\n" +
                "카테고리 : " + category + "\n" +
                "주성분 : " + mainIngr + "\n" +
                "금기 내용 : " + prohbtCon + "\n";
    }
}
