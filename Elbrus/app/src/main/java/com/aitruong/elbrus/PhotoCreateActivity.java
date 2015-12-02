package com.aitruong.elbrus;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import android.graphics.Bitmap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoCreateActivity extends AppCompatActivity {
    //private Uri fileUri;
    //public static final int MEDIA_TYPE_VIDEO = 2;
    //private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    //public static PhotoCreateActivity ActivityContext = null;
    //public static TextView output;
//
    //Button btntakephoto, btnsave, btnshare, btnvid,btnvidshare;
    //ImageView ivdisplayphoto;
//    SeekBar sbSeekBar;
//
//    private ColorMatrix colorMatrix;
//    private ColorMatrixColorFilter filter;
//    private Paint paint;
//    private Canvas cv;

    //String fotoname;
    //int progress;
//
    //private File photofile, filevid;
    //private int TAKENPHOTO = 0;
    //Bitmap photo, canvasBitmap;
    //ContentValues value;

    final PhotoCreateActivity thisActivity = this;
    private Data data;
    private Bitmap image;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = (Data)getApplication();

        final EditText name = (EditText) findViewById(R.id.photoCreate_edit_name);
        final EditText note = (EditText) findViewById(R.id.photoCreate_edit_note);
        Button btn = (Button) findViewById(R.id.choose_photo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().length()>0&&image!=null) {
                    boolean result = data.getParser().addPhoto(data.getUserID(),
                            data.getCurrentAlbumID(), name.getText().toString(), image,
                            note.getText().toString());
                    if (result) {
                        Snackbar.make(view, "Create Photo Success!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, "Create Photo Fail!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    thisActivity.finish();
                }
                else{
                    Snackbar.make(view, "Uncomplete data!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void choosePhoto(View tager){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 0x3001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x3001 && resultCode == RESULT_OK && null != data) {
            try {
                Uri originalUri = data.getData();
                ContentResolver resolver = getContentResolver();
                image = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                //String[] proj = {MediaStore.Images.Media.DATA};
                //Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //cursor.moveToFirst();
                //String path = cursor.getString(column_index);
                //btn.setText(btn.getText().toString() + "(selected)");
            }catch (IOException e) {
            }
        }
    }
}
