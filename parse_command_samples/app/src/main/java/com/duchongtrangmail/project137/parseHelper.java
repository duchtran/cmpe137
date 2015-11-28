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
import java.util.ArrayList;
import java.util.List;

public class parseHelper extends Activity{

    public void addUser(String username, String password) {
    // add a user to parse cloud given username and password, log the result to Log.d tag DATA

        final ParseObject user = new ParseObject("Users");
        user.put("username", username);
        user.put("password", password);
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
    // return user id given username and password, return null if cannot find

        ParseQuery query = new ParseQuery("Users");
        query.whereEqualTo("username", username);
        query.whereEqualTo("password", password);
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

    public void addAlbum(String UID, String AlbumName, String Description) {
    // add an album to parse given user id, album name, and description, log result to Log.d tag ALBUM

        ParseObject album = new ParseObject("Albums");
        album.put("UID", UID);
        album.put("AlbumName", AlbumName);
        album.put("Description", Description);
        album.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("ALBUMS", "Upload Successfully");
                } else {
                    Log.d("ALBUMS", "Cannot upload to Albums");
                }
            }
        });
    }

    public String getAID(String UID, String AlbumName) {
    // return album id given user id and album name, return null if cannot find

        ParseQuery query = new ParseQuery("Albums");
        query.whereEqualTo("UID", UID);
        query.whereEqualTo("AlbumName", AlbumName);
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
    public void addPhoto(String UID, String AID, String PhotoName, Resources res, int pic, String note) {
    // Upload a photo onto parse
    // For now, I can only allow upload from drawable folder in android
    // Resource res is found activity class, using command:
    //      Resources res = getResources();
    // pic is found in activity class, using command:
    //      int pic = R.drawable.<name>;

        Bitmap bitmap = BitmapFactory.decodeResource(res, pic);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] image = stream.toByteArray();
        ParseFile file = new ParseFile(PhotoName, image);
        try  {
            file.save();
        } catch (Exception e) {
            System.out.println("cannot save file");
        }
        ParseObject photo = new ParseObject("Photos");
        photo.put("UID", UID);
        photo.put("AID", AID);
        photo.put("PhotoName", PhotoName);
        photo.put("Note", note);
        photo.put("File", file);

        photo.saveInBackground();
    }

    public ArrayList<String> getListAlbum(String UID, int type) {
    //  return the list from Album table, type determine what column to get
    //  type = 0: Name
    //  type = 1: Description

        try {
            ParseQuery<ParseObject> query = new ParseQuery<>("Albums");
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

    public String getPID(String UID, String AID, String PhotoName) {
    // return photo id given user id, aid, and photo name, return null if cannot find
        ParseQuery query = new ParseQuery("Photos");
        query.whereEqualTo("UID", UID);
        query.whereEqualTo("AID", AID);
        query.whereEqualTo("PhotoName", PhotoName);
        try {
            String PID;
            ParseObject object = query.getFirst();
            PID = object.getObjectId();
            return PID;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFID(String UID, String Name) {
    // return friend id given user id and friend's name, return null if cannot find

        ParseQuery query = new ParseQuery("Friend");
        query.whereEqualTo("UID", UID);
        query.whereEqualTo("Name", Name);
        try {
            String FID;
            ParseObject object = query.getFirst();
            FID = object.getObjectId();
            return FID;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteObject(String Table, String ID) {
    //  delete an object, can be anything such as pictures, album, user... give the object's table name and object ID

        ParseObject.createWithoutData(Table, ID).deleteEventually();
    }


    public ArrayList<Bitmap> getListPhotoFiles(String UID, String AID) {
    //  return the list of photo file that match UID and AID in bitmap format
    //  to display on view, use .setImageBitmap(Bitmap bmp) from ImageView

        try {
            ParseQuery<ParseObject> query = new ParseQuery<>("Photos");
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
    //  return the specific photo file that match UID, AID, and PhotoName in bitmap format
    //  to display on view, use .setImageBitmap(Bitmap bmp) from ImageView

        try {
            ParseQuery query = new ParseQuery("Photos");
            query.whereEqualTo("UID", UID);
            query.whereEqualTo("AID", AID);
            query.whereEqualTo("PhotoName", PhotoName);
            ParseFile file = query.getFirst().getParseFile("File");
            byte[] data = file.getData();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bmp;
        }
        catch (ParseException e) {
            return null;
        }
    }

    public void addFriend(String UID, String Name) {
    //  add a friend name on to parse server given the UID

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
    //  return the list of Friends’ names given the UID

        try {
            ParseQuery<ParseObject> query = new ParseQuery<>("Friend");
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
    //  return the list of photo names given the UID, regardless of Album (AID)

        try {
            ParseQuery<ParseObject> query = new ParseQuery<>("Photos");
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
    //  return the list of photo names that matches UID and AID

        try {
            ParseQuery<ParseObject> query = new ParseQuery<>("Photos");
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

    public String getPhotoNote(String PID ) {
    //  return the note of a photo given the photo id, return null if cannot find
        try {
            ParseQuery query = new ParseQuery("Photos");
            query.whereEqualTo("objectId", PID);
            String Note;
            ParseObject object = query.getFirst();
            Note = object.getString("Note");
            return Note;
        } catch (ParseException e) {
            return null;
        }
    }
}
