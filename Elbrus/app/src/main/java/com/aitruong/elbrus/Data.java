package com.aitruong.elbrus;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by yunfan on 2015/11/24.
 */
public class Data extends Application{
    private String userID;
    private String userName;
    private parseHelper parser = new parseHelper();
    private String currentAlbumID;
    private String currentAlbumName;
    private String currentAlbumDescription;
    private String currentPhotoID;
    private String currentPhotoName;
    private String currentPhotoNote;

    public String getCurrentPhotoNote() {
        return currentPhotoNote;
    }

    public void setCurrentPhotoNote(String currentPhotoNote) {
        this.currentPhotoNote = currentPhotoNote;
    }

    public String getCurrentPhotoName() {
        return currentPhotoName;
    }

    public void setCurrentPhotoName(String currentPhotoName) {
        this.currentPhotoName = currentPhotoName;
    }

    public String getCurrentAlbumDescription() {
        return currentAlbumDescription;
    }

    public void setCurrentAlbumDescription(String currentAlbumDescription) {
        this.currentAlbumDescription = currentAlbumDescription;
    }

    public String getCurrentAlbumName() {
        return currentAlbumName;
    }

    public void setCurrentAlbumName(String currentAlbumName) {
        this.currentAlbumName = currentAlbumName;
    }

    public String getCurrentPhotoID() {
        return currentPhotoID;
    }

    public void setCurrentPhotoID(String currentPhotoID) {
        this.currentPhotoID = currentPhotoID;
    }

    public String getCurrentAlbumID() {
        return currentAlbumID;
    }

    public void setCurrentAlbumID(String currentAlbumID) {
        this.currentAlbumID = currentAlbumID;
    }

    public parseHelper getParser() {
        return parser;
    }

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
        userID = "NoID";
        userName = "UserName";



        //Parse init
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "98cD21Y6LEh4hQAPn67TwPQEHMI9vQUldxtOMWem", "leyfUbqBwE10KFsysTEQ9bRo3L4HAhyxcz0rRce8");


        //for test
        //for(int i=1;i<=10;i++){
        //    parser.addPhoto(userID,parser.getAID(userID,parser.getListAlbum(userID,0).get(0)),"PName"+i,
        //            getResources(),R.drawable.ic_local_see_black_24dp,"Note"+i);
        //}

        super.onCreate();
    }
}
