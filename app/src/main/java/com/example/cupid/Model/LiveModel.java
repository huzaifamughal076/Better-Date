package com.example.cupid.Model;

public class LiveModel {
    String userid;
    String broadId;
    String dp;

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getDp() {
        return dp;
    }

    public LiveModel(String userid, String broadId, String dp) {
        this.userid = userid;
        this.broadId = broadId;
        this.dp = dp;
    }

    public LiveModel(String userid, String broadId) {
        this.userid = userid;
        this.broadId = broadId;
    }

    public String getUserid() {
        return userid;
    }

    public String getBroadId() {
        return broadId;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setBroadId(String broadId) {
        this.broadId = broadId;
    }
}
