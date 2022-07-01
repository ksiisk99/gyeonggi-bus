package com.example.bus;

public class BusRouteInfo {
    String routeId;
    String routeTypeCd;
    String startStationId;
    String startStationName;
    String startMobileNo;
    String endStationId;
    String endStationName;
    String endMobileNo;
    String upFirstTime;
    String upLastTime;
    String downFirstTime;
    String downLastTime;
    String peekAlooc;
    String nPeekAlooc;
    String routeName;

    public BusRouteInfo(String routeId, String routeName,String startStationId, String startStationName, String startMobileNo, String endStationId, String endStationName, String endMobileNo, String upFirstTime, String upLastTime, String downFirstTime, String downLastTime, String peekAlooc, String nPeekAlooc,String routeTypeCd) {
        this.routeId = routeId;
        this.startStationId = startStationId;
        this.startStationName = startStationName;
        this.startMobileNo = startMobileNo;
        this.endStationId = endStationId;
        this.endStationName = endStationName;
        this.endMobileNo = endMobileNo;
        this.upFirstTime = upFirstTime;
        this.upLastTime = upLastTime;
        this.downFirstTime = downFirstTime;
        this.downLastTime = downLastTime;
        this.peekAlooc = peekAlooc;
        this.nPeekAlooc = nPeekAlooc;
        this.routeName = routeName;
        this.routeTypeCd=routeTypeCd;
    }

    public String getRouteTypeCd() {
        return routeTypeCd;
    }

    public void setRouteTypeCd(String routeTypeCd) {
        this.routeTypeCd = routeTypeCd;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getStartMobileNo() {
        return startMobileNo;
    }

    public void setStartMobileNo(String startMobileNo) {
        this.startMobileNo = startMobileNo;
    }

    public String getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(String endStationId) {
        this.endStationId = endStationId;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public String getEndMobileNo() {
        return endMobileNo;
    }

    public void setEndMobileNo(String endMobileNo) {
        this.endMobileNo = endMobileNo;
    }

    public String getUpFirstTime() {
        return upFirstTime;
    }

    public void setUpFirstTime(String upFirstTime) {
        this.upFirstTime = upFirstTime;
    }

    public String getUpLastTime() {
        return upLastTime;
    }

    public void setUpLastTime(String upLastTime) {
        this.upLastTime = upLastTime;
    }

    public String getDownFirstTime() {
        return downFirstTime;
    }

    public void setDownFirstTime(String downFirstTime) {
        this.downFirstTime = downFirstTime;
    }

    public String getDownLastTime() {
        return downLastTime;
    }

    public void setDownLastTime(String downLastTime) {
        this.downLastTime = downLastTime;
    }

    public String getPeekAlooc() {
        return peekAlooc;
    }

    public void setPeekAlooc(String peekAlooc) {
        this.peekAlooc = peekAlooc;
    }

    public String getnPeekAlooc() {
        return nPeekAlooc;
    }

    public void setnPeekAlooc(String nPeekAlooc) {
        this.nPeekAlooc = nPeekAlooc;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
