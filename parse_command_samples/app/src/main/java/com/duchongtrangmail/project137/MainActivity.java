package com.duchongtrangmail.project137;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends Activity {
    parseHelper parser = new parseHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "98cD21Y6LEh4hQAPn67TwPQEHMI9vQUldxtOMWem", "leyfUbqBwE10KFsysTEQ9bRo3L4HAhyxcz0rRce8");

        Button ADD = (Button)findViewById(R.id.BAdd);
        Button UPLOAD = (Button)findViewById(R.id.BUpload);
        final Button BTest = (Button)findViewById(R.id.BTest);
        final EditText uName = (EditText)findViewById(R.id.ETName);
        final EditText pw = (EditText)findViewById(R.id.ETPassword);
        final TextView TVtest = (TextView)findViewById(R.id.textView);
        final EditText ETnumber = (EditText)findViewById(R.id.ETNumber);

        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //parser.addUser(uName.getText().toString(), pw.getText().toString());
                try {
                    String uid = parser.getUID(uName.getText().toString(), pw.getText().toString());
                    String albumName = ETnumber.getText().toString();
                    parser.addAlbum(uid, albumName);
                    Toast.makeText(MainActivity.this, "Added album " + albumName, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cannot add album", Toast.LENGTH_SHORT).show();
                }
            }
        });
        BTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = parser.getUID(uName.getText().toString(), pw.getText().toString());
                if (uid != null) {
                    String aid = parser.getAID(uid, ETnumber.getText().toString());
                    TVtest.setText(aid);
                }
                else {
                    Toast.makeText(MainActivity.this, "Cannot find UID", Toast.LENGTH_SHORT).show();
                }
            }
        });
        UPLOAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uid = parser.getUID(uName.getText().toString(), pw.getText().toString());
                    String aid = parser.getAID(uid, ETnumber.getText().toString());
                    System.out.println("UID "+uid);
                    System.out.println("AID "+aid);
                    Resources res = getResources();
                    String name = "avatar";
                    int pic = R.drawable.avatar;
                    parser.addPhoto(uid, aid,"avatar", res, pic);
                    Toast.makeText(MainActivity.this, "Added photo avatar", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cannot add photo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void onClickUpload (View v ) {
        if (v.getId() == R.id.BUpload) {
            // Locate the image in res > drawable-hdpi
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lanscape);
            // Convert it to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();

            // Create the ParseFile
            ParseFile file = new ParseFile("lanscape.png", image);
            // Upload the image into Parse Cloud
            file.saveInBackground();

            // Create a New Class (tables) called "ImageUpload" in Parse
            ParseObject imgupload = new ParseObject("ImageUpload");

            // Create a column named "ImageName" and set the string
            imgupload.put("ImageName", "My Avatar");

            // Create a column named "ImageFile" and insert the image
            imgupload.put("ImageFile", file);

            // Create the tables and the columns
            imgupload.saveInBackground();

            // Show a simple toast message
            Toast.makeText(MainActivity.this, "Image Uploaded",
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void onClickDownload(View v) {
        if (v.getId() == R.id.BDownload) {
            final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "",
                    "Downloading Image...", true);
            final EditText number = (EditText)findViewById(R.id.ETNumber);
            final TextView Tvtest = (TextView)findViewById(R.id.textView);

//            ParseQuery query1 = new ParseQuery("Users");
//            query1.whereEqualTo("username", "aaa");
//            query1.whereEqualTo("password", "yyy");
//            // Locate the objectId from the class
//            query1.getFirstInBackground(new GetCallback<ParseObject>() {
//                public void done(ParseObject object, ParseException e) {
//                    if (e == null) {
//                        Log.d("test", "Found UserID " + object.getObjectId());
//                        Tvtest.setText(object.getObjectId());
//                    } else {
//                        Log.d("test", "Cannot find UserID");
//                    }
//                }
//            });


            // Locate the class table named "ImageUpload" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ImageUpload");
            query.whereEqualTo("ImageName", "My Avatar");
            // Locate the objectId from the class
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> object, ParseException e) {
                    // TODO Auto-generated method stub
                    // Locate the column named "ImageName" and set
                    // the string
                    ParseFile fileObject = (ParseFile) object.get(Integer.parseInt(number.getText().toString())).get("ImageFile");
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Log.d("test", "We've got data in data.");
                                // Decode the Byte[] into
                                // Bitmap
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                // Get the ImageView from
                                // main.xml
                                ImageView image = (ImageView) findViewById(R.id.IVdownloadedImage);

                                // Set the Bitmap into the
                                // ImageView
                                image.setImageBitmap(bmp);
                                // Close progress dialog
                                progressDialog.dismiss();
                            } else {
                                Log.d("test", "There was a problem downloading the data.");
                            }
                        }
                    });
                }
            });
        }
    }
    public String testing;
    public void settesting(String value) {
        testing = value;
    }

    public void getUID(String username, String password) {
        // Locate the class table named "Users" in Parse.com
        ParseQuery query = new ParseQuery("Users");
        query.whereEqualTo("username", username);
        query.whereEqualTo("password", password);
        // Locate the objectId from the class
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("test", "Found UserID " + object.getObjectId());
                    settesting(object.getObjectId());
                } else {
                    Log.d("test", "Cannot find UserID");
                }
            }
        });
    }

    public void addPhoto(String UID, String AID, String PhotoName, String path) {
        // set bitmap for picture
        System.out.println(getResources().toString());
        System.out.println(String.valueOf(R.drawable.avatar));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.avatar);

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
