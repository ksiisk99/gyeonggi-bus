package com.example.bus;

public class VisitBusInfo {
    String regionName,routeId,routeName,routeType,staOrder,predictTime1, predictTime2,stationId;

    public VisitBusInfo(String regionName, String routeId, String routeName, String routeType, String staOrder) {
        this.regionName = regionName;
        this.routeId = routeId;
        this.routeName = routeName;
        this.routeType = routeType;
        this.staOrder = staOrder;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getStaOrder() {
        return staOrder;
    }

    public void setStaOrder(String staOrder) {
        this.staOrder = staOrder;
    }

    public String getPredictTime1() {
        return predictTime1;
    }

    public void setPredictTime1(String predictTime1) {
        this.predictTime1 = predictTime1;
    }

    public String getPredictTime2() {
        return predictTime2;
    }

    public void setPredictTime2(String predictTime2) {
        this.predictTime2 = predictTime2;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
