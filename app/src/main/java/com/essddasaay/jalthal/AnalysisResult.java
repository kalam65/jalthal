package com.essddasaay.jalthal;

public class AnalysisResult {
    private String result;
    private String timestamp;

    public AnalysisResult(String result, String timestamp) {
        this.result = result;
        this.timestamp = timestamp;
    }

    public String getResult() {
        return result;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
