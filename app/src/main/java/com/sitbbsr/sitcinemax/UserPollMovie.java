package com.sitbbsr.sitcinemax;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserPollMovie {
    String mOptionChoosed;
    String mSicUser;
    String mUserMobile;
    @ServerTimestamp
    Date mUpdatedTime;

    public UserPollMovie() {
    }

    public UserPollMovie(String mOptionChoosed, String mSicUser, String mUserMobile, Date mUpdatedTime) {
        this.mOptionChoosed = mOptionChoosed;
        this.mSicUser = mSicUser;
        this.mUserMobile = mUserMobile;
        this.mUpdatedTime = mUpdatedTime;
    }

    public String getmOptionChoosed() {
        return mOptionChoosed;
    }

    public void setmOptionChoosed(String mOptionChoosed) {
        this.mOptionChoosed = mOptionChoosed;
    }

    public String getmSicUser() {
        return mSicUser;
    }

    public void setmSicUser(String mSicUser) {
        this.mSicUser = mSicUser;
    }

    public String getmUserMobile() {
        return mUserMobile;
    }

    public void setmUserMobile(String mUserMobile) {
        this.mUserMobile = mUserMobile;
    }

    public Date getmUpdatedTime() {
        return mUpdatedTime;
    }

    public void setmUpdatedTime(Date mUpdatedTime) {
        this.mUpdatedTime = mUpdatedTime;
    }
}
