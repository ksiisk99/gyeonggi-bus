package com.example.bus;

public class BusTimeInfo {
    String stationId, routeId, predictTime1, predictTime2;

    public BusTimeInfo(String stationId, String routeId, String predictTime1, String predictTime2) {
        this.stationId = stationId;
        this.routeId = routeId;
        this.predictTime1 = predictTime1;
        this.predictTime2 = predictTime2;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
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
}
