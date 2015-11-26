package com.duchongtrangmail.project137;

/**
 * Created by ductran on 11/24/15.
 */

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;

public class parseHelper extends Activity{
    public void addUser(String username, String password) {
        final ParseObject user = new ParseObject("Users");
        // Create a column named "ImageName" and set the string
        user.put("username", username);

        // Create a column named "ImageFile" and insert the image
        user.put("password", password);

        // Create the tables and the columns
        user.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("DATA", "Successful ");
                } else {
                    Log.d("DATA", "Cannot add user");
                }
            }
        });
    }

    public String getUID (String username, String password) {
        // Locate the class table named "Users" in Parse.com
        ParseQuery query = new ParseQuery("Users");
        query.whereEqualTo("username", username);
        query.whereEqualTo("password", password);
        // Locate the objectId from the class
        try {
            String UID;
            ParseObject object = query.getFirst();
            UID = object.getObjectId();
            return UID;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteUser(String username, String password) {

    }

    public void addAlbum(String UID, String AlbumName) {
        ParseObject album = new ParseObject("Albums");
        album.put("UID", UID);
        album.put("AlbumName", AlbumName);
        album.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("ALBUMS", "Upload Successfully");
                }
                else {
                    Log.d("ALBUMS", "Cannot upload to Albums");
                }
            }
        });
    }

    public String getAID(String UID, String AlbumName) {
        ParseQuery query = new ParseQuery("Albums");
        query.whereEqualTo("UID", UID);
        query.whereEqualTo("AlbumName", AlbumName);
        // Locate the objectId from the class
        try {
            String AID;
            ParseObject object = query.getFirst();
            AID = object.getObjectId();
            return AID;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void addPhoto(String UID, String AID, String PhotoName, Resources res, int pic) {
//      For now, I can only allow upload from drawable folder in android
//      Resource res is found activity class, using command:
//              Resources res = getResources();
//      pic is found in activity class, using command:
//        int pic = R.drawable.<name>;

        // set bitmap for picture
        Bitmap bitmap = BitmapFactory.decodeResource(res, pic);

        // create a new output stream
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // compress bitmap file (1-100), max is 100, put to stream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        // put output stream to byte array
        byte [] image = stream.toByteArray();

        // create new Parsefile to contain the bytestream
        ParseFile file = new ParseFile(PhotoName, image);

        // upload file to parse in background still haven't put in any table yet
        try  {
            file.save();
        } catch (Exception e) {
            System.out.println("cannot save file");
        }

        // create new ParseObject
        ParseObject photo = new ParseObject("Photos");
        photo.put("UID", UID);
        photo.put("AID", AID);
        photo.put("PhotoName", PhotoName);
        photo.put("File", file);

        photo.saveInBackground();
    }

}
