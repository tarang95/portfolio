package com.assign.tarang.inventory;

import java.io.Serializable;


public class DataInventoryLogs implements Serializable {
    //initailize variables
    String st_Name;
    String st_DateTime;
    String st_InvoiceNumber;
    String st_Quality;
    String st_Longitude;
    String st_Latitude;

    //constructor call
    public DataInventoryLogs() {
    }

    //constructor with parameters
    public DataInventoryLogs(String st_Name1, String st_DateTime1,String st_Longitude1,String st_Latitude1, String st_InvoiceNumber1, String st_Quality1) {

        st_Name = st_Name1;
        st_DateTime = st_DateTime1;
        st_Latitude=st_Latitude1;
        st_Longitude=st_Longitude1;
        st_InvoiceNumber = st_InvoiceNumber1;
        st_Quality = st_Quality1;

    }

    //getter methods
    public String getName() {
        return st_Name;
    }

    public String getDateTime() {
        return st_DateTime;
    }

    public String getLong() {
        return st_Longitude;
    }

    public String getLat()
    {
        return st_Latitude;
    }

    public String getInvoiceNo() {
        return st_InvoiceNumber;
    }

    public String getQuality() {
        return st_Quality;
    }
}
