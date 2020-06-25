package com.dongzhili.easylib.bean;

public class LoginBean {
    public String customerId;
    public String loginName;
    public String nickName;
    public String url;
    public String amountMoney;
    public String integral;
    public String token;
    public String isTradePws;
    //工号
    private String cardid;

    //用户id
    private String id;

    //id
    private String empid;

    //姓名
    private String empname;

    private String pushId;

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }
}
