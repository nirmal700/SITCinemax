package com.sitbbsr.sitcinemax;

public class UserData {
    String name, SIC, Course, password, phoneNumber, Year;

    public UserData() {
    }

    public UserData(String name, String SIC, String course, String password, String phoneNumber, String year) {
        this.name = name;
        this.SIC = SIC;
        Course = course;
        this.password = password;
        this.phoneNumber = phoneNumber;
        Year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSIC() {
        return SIC;
    }

    public void setSIC(String SIC) {
        this.SIC = SIC;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
