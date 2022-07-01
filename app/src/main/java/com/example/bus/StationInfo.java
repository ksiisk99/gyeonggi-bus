package com.example.bus;

public class StationInfo {
    private String stationId,stationName,mobileNo,regionName;

    public StationInfo(String stationId, String stationName, String mobileNo, String regionName) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.mobileNo = mobileNo;
        this.regionName = regionName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
