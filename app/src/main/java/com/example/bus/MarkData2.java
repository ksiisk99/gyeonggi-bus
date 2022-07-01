package com.example.bus;

public class MarkData2 {
    private String predictTime1,predictTime2;

    public MarkData2(){}

    public MarkData2(String predictTime1, String predictTime2) {
        this.predictTime1 = predictTime1;
        this.predictTime2 = predictTime2;
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
