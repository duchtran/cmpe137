package com.aitruong.elbrus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import android.graphics.Bitmap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private Uri fileUri;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static PhotoCreateActivity ActivityContext = null;
    public static TextView output;

    Button btntakephoto, btnsave, btnshare, btnvid,btnvidshare;
    ImageView ivdisplayphoto;
//    SeekBar sbSeekBar;
//
//    private ColorMatrix colorMatrix;
//    private ColorMatrixColorFilter filter;
//    private Paint paint;
//    private Canvas cv;

    String fotoname;
    int progress;

    private File photofile, filevid;
    private int TAKENPHOTO = 0;
    Bitmap photo, canvasBitmap;
    ContentValues value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
