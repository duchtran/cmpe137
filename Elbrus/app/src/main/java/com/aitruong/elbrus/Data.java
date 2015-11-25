package com.aitruong.elbrus;

import android.app.Application;

/**
 * Created by yunfan on 2015/11/24.
 */
public class Data extends Application{
    private String userID;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public void onCreate(){
        userID = "ID";
        userName = "Name";
        super.onCreate();
    }
}
