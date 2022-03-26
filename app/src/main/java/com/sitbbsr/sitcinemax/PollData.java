package com.sitbbsr.sitcinemax;

public class PollData {
    String mOption1, mOption2, mOption3, mOption4;
    int mOption1Votes, mOption2Votes, mOption3Votes, mOption4Votes;

    public PollData() {
    }

    public PollData(String mOption1, String mOption2, String mOption3, String mOption4, int mOption1Votes, int mOption2Votes, int mOption3Votes, int mOption4Votes) {
        this.mOption1 = mOption1;
        this.mOption2 = mOption2;
        this.mOption3 = mOption3;
        this.mOption4 = mOption4;
        this.mOption1Votes = mOption1Votes;
        this.mOption2Votes = mOption2Votes;
        this.mOption3Votes = mOption3Votes;
        this.mOption4Votes = mOption4Votes;
    }

    public String getmOption1() {
        return mOption1;
    }

    public void setmOption1(String mOption1) {
        this.mOption1 = mOption1;
    }

    public String getmOption2() {
        return mOption2;
    }

    public void setmOption2(String mOption2) {
        this.mOption2 = mOption2;
    }

    public String getmOption3() {
        return mOption3;
    }

    public void setmOption3(String mOption3) {
        this.mOption3 = mOption3;
    }

    public String getmOption4() {
        return mOption4;
    }

    public void setmOption4(String mOption4) {
        this.mOption4 = mOption4;
    }

    public int getmOption1Votes() {
        return mOption1Votes;
    }

    public void setmOption1Votes(int mOption1Votes) {
        this.mOption1Votes = mOption1Votes;
    }

    public int getmOption2Votes() {
        return mOption2Votes;
    }

    public void setmOption2Votes(int mOption2Votes) {
        this.mOption2Votes = mOption2Votes;
    }

    public int getmOption3Votes() {
        return mOption3Votes;
    }

    public void setmOption3Votes(int mOption3Votes) {
        this.mOption3Votes = mOption3Votes;
    }

    public int getmOption4Votes() {
        return mOption4Votes;
    }

    public void setmOption4Votes(int mOption4Votes) {
        this.mOption4Votes = mOption4Votes;
    }
}
