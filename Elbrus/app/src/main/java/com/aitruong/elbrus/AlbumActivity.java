package com.aitruong.elbrus;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ShareActionProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class AlbumActivity extends AppCompatActivity{

    final AlbumActivity thisActivity=this;
    public final static String MESSAGE_ALBUM_ID = "com.aitruong.elbrus.ElbrusActivity.MESSAGE_ALBUM_ID";
    private ShareActionProvider mShareActionProvider;
    private Data data;
    private GridView mGridView;
    private ArrayList<HashMap<String, Object>> lstImageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = (Data)getApplication();

        //show description
        TextView description = (TextView) findViewById(R.id.userDescription);
        description.setText(data.getUserName() + "'s Album");

        //show albums
        mGridView = (GridView) findViewById(R.id.albumGridView);

        //init data for test
        lstImageItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<10;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.ic_local_see_black_24dp);
            map.put("ItemText", "NO."+String.valueOf(i));
            lstImageItem.add(map);
        }
        SimpleAdapter saImageItems = new SimpleAdapter(this,
                lstImageItem,
                R.layout.grid_item,
                new String[] {"ItemImage","ItemText"},
                new int[] {R.id.ItemImage,R.id.ItemText});
        mGridView.setAdapter(saImageItems);

        //set listener
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1,
                                    int arg2,//position
                                    long arg3//row id
            ) {
                Intent intent = new Intent(thisActivity,AlbumDetailActivity.class);
                //for test
                String message = new String(arg2 + ":" + arg3);
                intent.putExtra(MESSAGE_ALBUM_ID,message);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album, menu);

        // Inflate the share button on the menu

        return true;
    }

    //Call to update the share intent

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_create_album:
                openCreateAlbum();
                return true;
            case R.id.action_invite:
                openInvite();
                return true;
            case R.id.action_share:
                openShare();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSearch(){
        Intent intent = new Intent(this,PhotoSearchActivity.class);
        startActivity(intent);
    }

    public void openCreateAlbum(){
        Intent intent = new Intent(this,AlbumCreateActivity.class);
        startActivity(intent);
    }

    public void openInvite(){
        Intent intent = new Intent(this,InviteActivity.class);
        startActivity(intent);
    }

    public void openShare(){
        Intent intent = new Intent(this,AlbumShareActivity.class);
        startActivity(intent);
    }
}