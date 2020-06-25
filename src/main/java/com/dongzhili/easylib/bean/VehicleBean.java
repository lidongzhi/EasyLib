package com.dongzhili.easylib.bean;

import java.io.Serializable;

/**
 * Created by 李振强 on 2017/6/1.
 */

public class VehicleBean implements Serializable {
    private String numberPlate;
    private int vehicleId;
    private String rongId;
    private String token;

    public String getRongId() {
        return rongId;
    }

    public void setRongId(String rongId) {
        this.rongId = rongId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
