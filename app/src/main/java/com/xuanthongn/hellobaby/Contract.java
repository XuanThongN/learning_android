package com.xuanthongn.hellobaby;

public class Contract {
    private String title;
    private String parties;
    private String purpose;

    public Contract(String title, String parties, String purpose) {
        this.title = title;
        this.parties = parties;
        this.purpose = purpose;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParties() {
        return parties;
    }

    public void setParties(String parties) {
        this.parties = parties;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
