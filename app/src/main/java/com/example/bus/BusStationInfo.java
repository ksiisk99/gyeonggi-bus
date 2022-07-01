package com.example.bus;

public class BusStationInfo {
    String stationId,stationName,x,y,stationSeq,turnYn, Time,routeTypeCd,mobileNo,plateNo;
    int viewType;
    boolean bus,mark;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public BusStationInfo(String stationId, String stationName, String x, String y, String stationSeq, String turnYn, String mobileNo) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
        this.stationSeq = stationSeq;
        this.turnYn = turnYn;
        this.mobileNo=mobileNo;
        Time="0";
        bus=false;
        mark=false;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public boolean getMark(){
        return mark;
    }

    public void setMark(boolean mark){
        this.mark=mark;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRouteTypeCd() {
        return routeTypeCd;
    }

    public void setRouteTypeCd(String routeTypeCd) {
        this.routeTypeCd = routeTypeCd;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public boolean isBus() {
        return bus;
    }

    public void setBus(boolean bus) {
        this.bus = bus;
    }

    public String getStationSeq() {
        return stationSeq;
    }

    public void setStationSeq(String stationSeq) {
        this.stationSeq = stationSeq;
    }

    public String getTurnYn() {
        return turnYn;
    }

    public void setTurnYn(String turnYn) {
        this.turnYn = turnYn;
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

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
