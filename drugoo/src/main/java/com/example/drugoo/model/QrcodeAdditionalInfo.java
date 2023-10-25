package com.example.drugoo.model;

public class QrcodeAdditionalInfo {
    private String expiredDate;

    private String description;

    QrcodeAdditionalInfo(){

    }

    QrcodeAdditionalInfo(String expiredDate, String description){
        this.expiredDate = expiredDate;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}
