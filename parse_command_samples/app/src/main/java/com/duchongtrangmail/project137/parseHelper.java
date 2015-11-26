package com.duchongtrangmail.project137;

/**
 * Created by ductran on 11/24/15.
 */

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<String> getListAlbum(String UID, int type) {
        // type = 0: Name
        // type = 1: Description
        try {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Albums");
            query.whereEqualTo("UID", UID);
            List<ParseObject> objectList = query.find();
            ArrayList<String> list = new ArrayList<>();
            String column = "";
            switch (type) {
                case 0:
                    column = "AlbumName";
                    break;
                case 1:
                    column = "Description";
                    break;
                default:
                    break;
            }
            for (int i = 0; i < objectList.size(); i++) {
                list.add(objectList.get(i).getString(column));
            }
            Log.d("ALBUM", "Successfully get list of "+column);
            return list;
        }
        catch (ParseException e) {
            Log.d("ALBUM", "Cannot get list");
            return null;
        }
    }

    public ArrayList<Bitmap> getListPhotoFiles(String UID, String AID) {
        try {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Photos");
            query.whereEqualTo("UID", UID);
            query.whereEqualTo("AID", AID);
            List<ParseObject> objectList = query.find();
            ArrayList<Bitmap> list = new ArrayList<>();
            for (int i = 0; i < objectList.size(); i++) {
                ParseFile file = objectList.get(i).getParseFile("File");
                byte [] data = file.getData();
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
                list.add(bmp);
            }
            Log.d("FRIEND", "Successfully get list of Friends' Names");
            return list;
        }
        catch (ParseException e) {
            Log.d("FRIEND", "Cannot get list of Friends' Names");
            return null;
        }
    }

    public Bitmap getSpecificPhotoFile(String UID, String AID, String PhotoName) {
        try {
            ParseQuery query = new ParseQuery("Photos");
            query.whereEqualTo("UID", UID);
            query.whereEqualTo("AID", AID);
            query.whereEqualTo("PhotoName", PhotoName);
            ParseFile file = query.getFirst().getParseFile("File");
            byte [] data = file.getData();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bmp;
        }
        catch (ParseException e) {
            return null;
        }
    }

    public void addFriend(String UID, String Name) {
        ParseObject friend = new ParseObject("Friend");
        friend.put("UID", UID);
        friend.put("Name", Name);
        friend.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("FRIEND", "Upload Successfully");
                }
                else {
                    Log.d("FRIEND", "Cannot upload to Friend table");
                }
            }
        });
    }
    public ArrayList<String> getListFriendNames(String UID) {
        try {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Friend");
            query.whereEqualTo("UID", UID);
            List<ParseObject> objectList = query.find();
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < objectList.size(); i++) {
                list.add(objectList.get(i).getString("Name"));
            }
            Log.d("FRIEND", "Successfully get list of Friends' Names");
            return list;
        }
        catch (ParseException e) {
            Log.d("FRIEND", "Cannot get list of Friends' Names");
            return null;
        }
    }

    public ArrayList<String> getListPhotoNamesFromUser(String UID) {
        try {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Photos");
            query.whereEqualTo("UID", UID);
            List<ParseObject> objectList = query.find();
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < objectList.size(); i++) {
                list.add(objectList.get(i).getString("PhotoName"));
            }
            Log.d("PHOTO", "Successfully get list of Photos");
            return list;
        }
        catch (ParseException e) {
            Log.d("PHOTO", "Cannot get list");
            return null;
        }
    }

    public ArrayList<String> getListPhotoNamesFromUser_Album(String UID, String AID) {
        try {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Photos");
            query.whereEqualTo("UID", UID);
            query.whereEqualTo("AID", AID);
            List<ParseObject> objectList = query.find();
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < objectList.size(); i++) {
                list.add(objectList.get(i).getString("PhotoName"));
            }
            Log.d("PHOTO", "Successfully get list of Photos");
            return list;
        }
        catch (ParseException e) {
            Log.d("PHOTO", "Cannot get list");
            return null;
        }
    }
}
