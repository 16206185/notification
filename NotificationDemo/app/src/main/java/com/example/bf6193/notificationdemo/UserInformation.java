package com.example.bf6193.notificationdemo;

import android.app.Application;

public class UserInformation  extends Application {
    private String Uid;

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }
}
