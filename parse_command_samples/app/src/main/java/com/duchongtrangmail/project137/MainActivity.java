package com.duchongtrangmail.project137;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.parse.Parse;
import com.parse.ParseObject;

import java.lang.reflect.Field;
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

        Button ADD = (Button) findViewById(R.id.BAdd);
        Button UPLOAD = (Button) findViewById(R.id.BUpload);
        Button DOWNLOAD = (Button) findViewById(R.id.BDownload);
        Button LIST = (Button)findViewById(R.id.BList);
        final Button BTest = (Button) findViewById(R.id.BTest);
        final EditText uName = (EditText) findViewById(R.id.ETName);
        final EditText pw = (EditText) findViewById(R.id.ETPassword);
        final TextView TVtest = (TextView) findViewById(R.id.textView);
        final EditText ETnumber = (EditText) findViewById(R.id.ETNumber);
        final ImageView IVdownload = (ImageView) findViewById(R.id.IVdownloadedImage);
        final EditText photoname = (EditText)findViewById(R.id.ETPName);
//        final ListView elv = (ListView)findViewById(R.id.ELV);
//        final ParseImageView pIV = (ParseImageView)findViewById(R.id.pIV);

        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //parser.addUser(uName.getText().toString(), pw.getText().toString());
                try {
                    String uid = parser.getUID(uName.getText().toString(), pw.getText().toString());
                    //String albumName = ETnumber.getText().toString();
                    String friendName = ETnumber.getText().toString();

                    parser.addFriend(uid, friendName);
                    Toast.makeText(MainActivity.this, "Added  " + friendName + "to friendlist", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cannot add ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = parser.getUID(uName.getText().toString(), pw.getText().toString());
                if (uid != null) {
                   // String aid = parser.getAID(uid, ETnumber.getText().toString());
                   List<Bitmap> list = parser.getListPhotoFiles(uid, "MfQGGgCRw4");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                    }
                    IVdownload.setImageBitmap(list.get(0));
                } else {
                    Toast.makeText(MainActivity.this, "Cannot find UID", Toast.LENGTH_SHORT).show();
                }
//
            }
        });

        UPLOAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uid = parser.getUID(uName.getText().toString(), pw.getText().toString());
                    String aid = parser.getAID(uid, ETnumber.getText().toString());
                    System.out.println("UID " + uid);
                    System.out.println("AID " + aid);
                    Resources res = getResources();
                    String name = "avatar";
                    int pic = R.drawable.avatar;
                    String notephoto = "testing";
                    parser.addPhoto(uid, aid, "avatar", res, pic, notephoto);
                    Toast.makeText(MainActivity.this, "Added photo avatar", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cannot add photo", Toast.LENGTH_SHORT).show();
                }

            }
        });

        DOWNLOAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uid = parser.getUID(uName.getText().toString(), pw.getText().toString());
                    String aid = parser.getAID(uid, ETnumber.getText().toString());
                    String photoName = photoname.getText().toString();
                    Bitmap image = parser.getSpecificPhotoFile(uid, aid, photoName);
                    IVdownload.setImageBitmap(image);
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cannot download photo", Toast.LENGTH_SHORT).show();
                }
            }
        });


        LIST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("TEST", parser.getFID("LADk7Toi9u", "65431"));
//                Log.d("TEST1", parser.getPID("LADk7Toi9u", "MfQGGgCRw4", "avatar"));
               // parser.deleteObject("Photos", "aG4jPs6F5w");
                Log.d("TEST3", parser.getPhotoNote("gD6NgK43js"));
            }
        });
    }
}
