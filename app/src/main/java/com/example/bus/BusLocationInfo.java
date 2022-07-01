package com.example.bus;

public class BusLocationInfo {
    String stationId,stationSeq;

    public BusLocationInfo(String stationId, String stationSeq) {
        this.stationId = stationId;
        this.stationSeq = stationSeq;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationSeq() {
        return stationSeq;
    }

    public void setStationSeq(String stationSeq) {
        this.stationSeq = stationSeq;
    }
}
