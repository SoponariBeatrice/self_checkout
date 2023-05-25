package com.example.selfcheckout2.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupermarketData {
    public String name;
    public String collaborationStartDate;
    public String emailContact;
    public String address;
    public String account;
    public String dataFormat;

    public double latitude;

    public double longitude;
    public ScheduleData scheduleData;
    Long id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupermarketData(String name, String collaborationStartDate, String emailContact, String address, String account, String dataFormat, double latitude, double longitude, ScheduleData scheduleData) {
        this.name = name;
        this.collaborationStartDate = collaborationStartDate;
        this.emailContact = emailContact;
        this.address = address;
        this.account = account;
        this.dataFormat = dataFormat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scheduleData = scheduleData;
    }

    public SupermarketData(String name, String collaborationStartDate, String emailContact, String address, String account, String dataFormat, double latitude, double longitude, ScheduleData scheduleData, Long id) {
        this.name = name;
        this.collaborationStartDate = collaborationStartDate;
        this.emailContact = emailContact;
        this.address = address;
        this.account = account;
        this.dataFormat = dataFormat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scheduleData = scheduleData;
        this.id = id;
    }
    public SupermarketData() {
    }

    public String getCollaborationStartDate() {
        return collaborationStartDate;
    }

    public void setCollaborationStartDate(String collaborationStartDate) {
        this.collaborationStartDate = collaborationStartDate;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }


}
