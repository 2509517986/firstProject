package com.senxu.omserver.service.domain;

public class RealtimeData {

    //光伏阵列电流
    private String energyA;

    //光伏阵列电压
    private String energyV;

    //蓄电池电流
    private String powerA;

    //蓄电池电压
    private String powerV;

    //负载电流
    private String loadA;

    //负载电压
    private String loadV;

    //电池电量
    private String batteryPower;

    //电池温度
    private String BatteryTemperature;

    //剩余流量
    private String RemainingFlow;

    //信号强度
    private String signalStrength;

    //室外温度
    private String outdoorTemperature;

    //光辐射
    private String lightRadiation;

    //相对湿度
    private String relativeHumidity;

    //大气压强
    private String atmosphericPressure;

    //风向
    private String windDirection;

    //风速
    private String windSpeed;

    //降雨强度
    private String rainfallIntensity;

//    //杆塔震动
//    private String towerVibration;
//
//    //杆塔倾斜
//    private String towerTilt;
//
//    //导线垂弧
//    private String wireSag;
//
//    //导线振动
//    private String wireVibration;
//
//    //舞动振幅
//    private String gallopingAmplitude;
//
//    //导线温度
//    private String wireTemperature;
//
//    //设备报警
//    private String deviceAlarm;
//
//    //山火报警
//    private String fireAlarm;


    public String getEnergyA() {
        return energyA;
    }

    public void setEnergyA(String energyA) {
        this.energyA = energyA;
    }

    public String getEnergyV() {
        return energyV;
    }

    public void setEnergyV(String energyV) {
        this.energyV = energyV;
    }

    public String getPowerA() {
        return powerA;
    }

    public void setPowerA(String powerA) {
        this.powerA = powerA;
    }

    public String getPowerV() {
        return powerV;
    }

    public void setPowerV(String powerV) {
        this.powerV = powerV;
    }

    public String getLoadA() {
        return loadA;
    }

    public void setLoadA(String loadA) {
        this.loadA = loadA;
    }

    public String getLoadV() {
        return loadV;
    }

    public void setLoadV(String loadV) {
        this.loadV = loadV;
    }

    public String getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(String batteryPower) {
        this.batteryPower = batteryPower;
    }

    public String getBatteryTemperature() {
        return BatteryTemperature;
    }

    public void setBatteryTemperature(String batteryTemperature) {
        BatteryTemperature = batteryTemperature;
    }

    public String getRemainingFlow() {
        return RemainingFlow;
    }

    public void setRemainingFlow(String remainingFlow) {
        RemainingFlow = remainingFlow;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getOutdoorTemperature() {
        return outdoorTemperature;
    }

    public void setOutdoorTemperature(String outdoorTemperature) {
        this.outdoorTemperature = outdoorTemperature;
    }

    public String getLightRadiation() {
        return lightRadiation;
    }

    public void setLightRadiation(String lightRadiation) {
        this.lightRadiation = lightRadiation;
    }

    public String getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(String relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public void setAtmosphericPressure(String atmosphericPressure) {
        this.atmosphericPressure = atmosphericPressure;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getRainfallIntensity() {
        return rainfallIntensity;
    }

    public void setRainfallIntensity(String rainfallIntensity) {
        this.rainfallIntensity = rainfallIntensity;
    }
}
