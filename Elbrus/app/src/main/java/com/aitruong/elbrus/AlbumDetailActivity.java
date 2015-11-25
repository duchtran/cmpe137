package com.aitruong.elbrus;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AlbumDetailActivity extends AppCompatActivity {

    final AlbumDetailActivity thisActivity=this;
    public final static String MESSAGE_PHOTO_ID = "com.aitruong.elbrus.ElbrusActivity.MESSAGE_PHOTO_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String albumID = intent.getStringExtra(AlbumActivity.MESSAGE_ALBUM_ID);
        TextView description = (TextView) findViewById(R.id.albumDescription);
        description.setText(albumID + "'s photo: description");

        GridView gridview = (GridView) findViewById(R.id.photoGridView);

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<10;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.ic_photo_black_24dp);
            map.put("ItemText", "NO."+String.valueOf(i));
            lstImageItem.add(map);
        }
        SimpleAdapter saImageItems = new SimpleAdapter(this,
                lstImageItem,
                R.layout.grid_item,
                new String[] {"ItemImage","ItemText"},
                new int[] {R.id.ItemImage,R.id.ItemText});
        gridview.setAdapter(saImageItems);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1,
                                    int arg2,//position
                                    long arg3//row id
            ) {
                Intent intent = new Intent(thisActivity, PhotoDetailActivity.class);
                String message = new String("No." + arg2+ " ");
                intent.putExtra(MESSAGE_PHOTO_ID,message);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_create_photo:
                openCreatePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void openCreatePhoto(){
        Intent intent = new Intent(this,PhotoCreateActivity.class);
        startActivity(intent);
    }
}
