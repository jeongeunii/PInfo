package com.cookandroid.pinfo.Mypage;

public class CheckDBStructure {
    int id;
    String name, dept, joiningDate;
    String salary;

    public CheckDBStructure(int id, String name, String dept, String joiningDate, String salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getSalary() {
        return salary;
    }
}
