package com.sitbbsr.sitcinemax;

public class TeamData {
    String name, email, phoneNumber, insta, imgUrl, descTitle, desc;

    public TeamData() {
    }

    public TeamData(String name, String email, String phoneNumber, String insta, String imgUrl, String descTitle, String desc) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.insta = insta;
        this.imgUrl = imgUrl;
        this.descTitle = descTitle;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescTitle() {
        return descTitle;
    }

    public void setDescTitle(String descTitle) {
        this.descTitle = descTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
