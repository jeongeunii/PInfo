package com.cookandroid.pinfo.LMain;

public class Store {
    private String dutyName; // 약국 이름
    private String dutyAddr; // 약국 주소
    private String dutyTel; // 전화번호
    private String dutyTime1; // 월요일 영업 종료 시간
    private String dutyTime2; // 화요일 영업 종료 시간
    private String dutyTime3; // 수요일 영업 종료 시간
    private String dutyTime4; // 목요일 영업 종료 시간
    private String dutyTime5; // 금요일 영업 종료 시간
    private String dutyTime6; // 토요일 영업 종료 시간
    private String dutyTime7; // 일요일 영업 종료 시간
    private String dutyTime8; // 공휴일 영업 종료 시간

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getDutyAddr() {
        return dutyAddr;
    }

    public void setDutyAddr(String dutyAddr) {
        this.dutyAddr = dutyAddr;
    }

    public String getDutyTel() {
        return dutyTel;
    }

    public void setDutyTel(String dutyTel) {
        this.dutyTel = dutyTel;
    }

    public String getDutyTime1() {
        return dutyTime1;
    }

    public void setDutyTime1(String dutyTime1) {
        this.dutyTime1 = dutyTime1;
    }

    public String getDutyTime2() {
        return dutyTime2;
    }

    public void setDutyTime2(String dutyTime2) {
        this.dutyTime2 = dutyTime2;
    }

    public String getDutyTime3() {
        return dutyTime3;
    }

    public void setDutyTime3(String dutyTime3) {
        this.dutyTime3 = dutyTime3;
    }

    public String getDutyTime4() {
        return dutyTime4;
    }

    public void setDutyTime4(String dutyTime4) {
        this.dutyTime4 = dutyTime4;
    }

    public String getDutyTime5() {
        return dutyTime5;
    }

    public void setDutyTime5(String dutyTime5) {
        this.dutyTime5 = dutyTime5;
    }

    public String getDutyTime6() {
        return dutyTime6;
    }

    public void setDutyTime6(String dutyTime6) {
        this.dutyTime6 = dutyTime6;
    }

    public String getDutyTime7() {
        return dutyTime7;
    }

    public void setDutyTime7(String dutyTime7) {
        this.dutyTime7 = dutyTime7;
    }

    public String getDutyTime8() {
        return dutyTime8;
    }

    public void setDutyTime8(String dutyTime8) {
        this.dutyTime8 = dutyTime8;
    }

    public String toString() {
        return "\n약국 이름 : " + dutyName + "\n" +
                "약국 주소 : " + dutyAddr + "\n";
    }

    public String listString() {
        return "\n약국 이름 : " + dutyName + "\n" +
                "약국 주소 : " + dutyAddr + "\n" +
                "전화번호 : " + dutyTel + "\n\n" +
                "월요일 영업 종료 : " + dutyTime1 + "\n" +
                "화요일 영업 종료 : " + dutyTime2 + "\n" +
                "수요일 영업 종료 : " + dutyTime3 + "\n" +
                "목요일 영업 종료 : " + dutyTime4 + "\n" +
                "금요일 영업 종료 : " + dutyTime5 + "\n" +
                "토요일 영업 종료 : " + dutyTime6 + "\n" +
                "일요일 영업 종료 : " + dutyTime7 + "\n" +
                "공휴일 영업 종료 : " + dutyTime8 + "\n";
    }
}
