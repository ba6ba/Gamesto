package com.example.sarwan.weather.model;

import java.util.List;
import java.util.Set;

public  class FilteredData {

    public  String Date;
    public  String Status;
    public  String max_temp;
    public  String min_temp;

    public FilteredData(String date, String status, String min_temp, String max_temp) {
        Date = date;
        Status = status;
        this.max_temp = max_temp;
        this.min_temp = min_temp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }
}
