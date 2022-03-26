package com.sitbbsr.sitcinemax;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Logcat {
    String mNewName, mOldName, mNewSIC, mOldSIC, mOldCourse, mOldYear, mNewCourse, mNewYear, mMobileNo;
    @ServerTimestamp
    Date mUpdatedTime;

    public Logcat() {
    }

    public Logcat(String mNewName, String mOldName, String mNewSIC, String mOldSIC, String mOldCourse, String mOldYear, String mNewCourse, String mNewYear, String mMobileNo, Date mUpdatedTime) {
        this.mNewName = mNewName;
        this.mOldName = mOldName;
        this.mNewSIC = mNewSIC;
        this.mOldSIC = mOldSIC;
        this.mOldCourse = mOldCourse;
        this.mOldYear = mOldYear;
        this.mNewCourse = mNewCourse;
        this.mNewYear = mNewYear;
        this.mMobileNo = mMobileNo;
        this.mUpdatedTime = mUpdatedTime;
    }

    public String getmNewName() {
        return mNewName;
    }

    public void setmNewName(String mNewName) {
        this.mNewName = mNewName;
    }

    public String getmOldName() {
        return mOldName;
    }

    public void setmOldName(String mOldName) {
        this.mOldName = mOldName;
    }

    public String getmNewSIC() {
        return mNewSIC;
    }

    public void setmNewSIC(String mNewSIC) {
        this.mNewSIC = mNewSIC;
    }

    public String getmOldSIC() {
        return mOldSIC;
    }

    public void setmOldSIC(String mOldSIC) {
        this.mOldSIC = mOldSIC;
    }

    public String getmOldCourse() {
        return mOldCourse;
    }

    public void setmOldCourse(String mOldCourse) {
        this.mOldCourse = mOldCourse;
    }

    public String getmOldYear() {
        return mOldYear;
    }

    public void setmOldYear(String mOldYear) {
        this.mOldYear = mOldYear;
    }

    public String getmNewCourse() {
        return mNewCourse;
    }

    public void setmNewCourse(String mNewCourse) {
        this.mNewCourse = mNewCourse;
    }

    public String getmNewYear() {
        return mNewYear;
    }

    public void setmNewYear(String mNewYear) {
        this.mNewYear = mNewYear;
    }

    public String getmMobileNo() {
        return mMobileNo;
    }

    public void setmMobileNo(String mMobileNo) {
        this.mMobileNo = mMobileNo;
    }

    public Date getmUpdatedTime() {
        return mUpdatedTime;
    }

    public void setmUpdatedTime(Date mUpdatedTime) {
        this.mUpdatedTime = mUpdatedTime;
    }
}
