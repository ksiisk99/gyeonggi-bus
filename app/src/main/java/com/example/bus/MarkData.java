package com.example.bus;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "MarkBus")
public class MarkData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int position;
    private String routeName,routeId,startStationName,endStationName,peekAlooc;
    private String NpeekAlooc,upFirstTime,upLastTime,downFirstTime,downLastTime,routeTypeCd,stationId,stationName,stationSeq;

    //Constructor

    public MarkData(){}
    public MarkData(int position, String routeName, String routeId, String startStationName, String endStationName, String peekAlooc, String npeekAlooc, String upFirstTime, String upLastTime, String downFirstTime, String downLastTime,String routeTypeCd, String stationId, String stationName, String stationSeq) {
        this.position = position;
        this.routeName = routeName;
        this.routeId = routeId;
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.peekAlooc = peekAlooc;
        NpeekAlooc = npeekAlooc;
        this.upFirstTime = upFirstTime;
        this.upLastTime = upLastTime;
        this.downFirstTime = downFirstTime;
        this.downLastTime=downLastTime;
        this.routeTypeCd = routeTypeCd;
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationSeq = stationSeq;
    }

    //getter & setter


    public String getDownLastTime() {
        return downLastTime;
    }

    public void setDownLastTime(String downLastTime) {
        this.downLastTime = downLastTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public String getPeekAlooc() {
        return peekAlooc;
    }

    public void setPeekAlooc(String peekAlooc) {
        this.peekAlooc = peekAlooc;
    }

    public String getNpeekAlooc() {
        return NpeekAlooc;
    }

    public void setNpeekAlooc(String NpeekAlooc) {
        this.NpeekAlooc = NpeekAlooc;
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

    public String getRouteTypeCd() {
        return routeTypeCd;
    }

    public void setRouteTypeCd(String routeTypeCd) {
        this.routeTypeCd = routeTypeCd;
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

    public String getStationSeq() {
        return stationSeq;
    }

    public void setStationSeq(String stationSeq) {
        this.stationSeq = stationSeq;
    }
}
