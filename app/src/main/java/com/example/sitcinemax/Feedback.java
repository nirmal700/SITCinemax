package com.example.sitcinemax;

public class Feedback {
    String RatingStars, Feedback, SIC, PhoneNumber, RatingText,mUserName;
    boolean IsResolved,IsAcknowledged;

    public Feedback() {
    }

    public Feedback(String ratingStars, String feedback, String SIC, String phoneNumber,String mUserName, String ratingText, boolean isResolved, boolean isAcknowledged) {
        RatingStars = ratingStars;
        Feedback = feedback;
        this.SIC = SIC;
        PhoneNumber = phoneNumber;
        RatingText = ratingText;
        this.mUserName = mUserName;
        IsResolved = isResolved;
        IsAcknowledged = isAcknowledged;
    }

    public String getRatingStars() {
        return RatingStars;
    }

    public void setRatingStars(String ratingStars) {
        RatingStars = ratingStars;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getSIC() {
        return SIC;
    }

    public void setSIC(String SIC) {
        this.SIC = SIC;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getRatingText() {
        return RatingText;
    }

    public void setRatingText(String ratingText) {
        RatingText = ratingText;
    }

    public boolean isResolved() {
        return IsResolved;
    }

    public void setResolved(boolean resolved) {
        IsResolved = resolved;
    }

    public boolean isAcknowledged() {
        return IsAcknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        IsAcknowledged = acknowledged;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }
}
