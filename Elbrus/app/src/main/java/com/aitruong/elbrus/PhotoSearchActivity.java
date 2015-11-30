package com.aitruong.elbrus;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;

public class PhotoSearchActivity extends AppCompatActivity {
    final PhotoSearchActivity thisActivity = this;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (Data) getApplication();

        Button Search = (Button) findViewById(R.id.bSearch);
        setContentView(R.layout.activity_photo_search);
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

    public void onSearchClick(View v) {
        if (v.getId() == R.id.bSearch) {
            final EditText AlbumName = (EditText) findViewById(R.id.etAname);
            final EditText PhotoName = (EditText) findViewById(R.id.etPname);
            String uid = data.getUserID();
            String aid = data.getParser().getAID(uid, AlbumName.getText().toString());
            Bitmap image = data.getParser().getSpecificPhotoFile(uid, aid, PhotoName.getText().toString());
            if (image != null) {
                ImageView display = (ImageView) findViewById(R.id.ivImage);
                display.setImageBitmap(image);
            }
            else
                Toast.makeText(thisActivity, "cannot find picture",Toast.LENGTH_SHORT).show();
        }
    }
}


