package com.example.day1_exam.bean;

public class UserBean {

    private String tal;
    private String pwd;

    public UserBean(String tal, String pwd) {
        this.tal = tal;
        this.pwd = pwd;
    }

    public String getTal() {
        return tal;
    }

    public void setTal(String tal) {
        this.tal = tal;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "tal='" + tal + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

}
