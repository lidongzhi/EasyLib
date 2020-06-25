package com.dongzhili.easylib.bean;

public class VersionBean {
    public String code;
    public int codeNum;
    public int isConstraint;
    public String remarks;
    //版本名称
    private String version_name;

    //版本号
    private int versions;

    //下载路径
    public String url;

    //是否强制更新
    private String isForce;

    //描述
    private String desrc;

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersions() {
        return versions;
    }

    public void setVersions(int versions) {
        this.versions = versions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getDesrc() {
        return desrc;
    }

    public void setDesrc(String desrc) {
        this.desrc = desrc;
    }
}
