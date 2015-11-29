package com.aitruong.elbrus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ShareActionProvider;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.HashMap;

public class AlbumActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        //refreshList();
    }

    final AlbumActivity thisActivity=this;
    public final static String MESSAGE_ALBUM_ID = "com.aitruong.elbrus.ElbrusActivity.MESSAGE_ALBUM_ID";
    private ShareActionProvider mShareActionProvider;
    private Data data;
    private GridView mGridView;
    private GridItemAdapter mAdapter;
    private boolean isShowDelete = false;
    private ArrayList<HashMap<String, Object>> myList = new ArrayList<HashMap<String, Object>>();
    private String UID;

    private parseHelper parser = new parseHelper();
    ArrayList<String> albumNames;
    ArrayList<String> albumID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = (Data)getApplication();
        UID = data.getUserID();

        //Parse init
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "98cD21Y6LEh4hQAPn67TwPQEHMI9vQUldxtOMWem", "leyfUbqBwE10KFsysTEQ9bRo3L4HAhyxcz0rRce8");

        //show description
        TextView description = (TextView) findViewById(R.id.userDescription);
        description.setText(data.getUserName() + "'s Album");

        //show albums
        mGridView = (GridView) findViewById(R.id.albumGridView);

        refreshList();

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

    @Override
    public void onItemClick(AdapterView<?> arg0,
                            View arg1,
                            int arg2,//position
                            long arg3//row id
    ) {
        if (isShowDelete){
            delete(arg2);
            refreshList();
        }
        else{
            Intent intent = new Intent(thisActivity,AlbumDetailActivity.class);
            //for test
            String message = String.valueOf(arg2);
            intent.putExtra(MESSAGE_ALBUM_ID,message);
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        if (isShowDelete) {
            isShowDelete = false;

        } else {
            isShowDelete = true;
            mAdapter.setIsShowDelete(isShowDelete);
            View convertView = LayoutInflater.from(AlbumActivity.this).inflate(R.layout.grid_item,null);
        }

        mAdapter.setIsShowDelete(isShowDelete);

        return true;
    }


    private void delete(int position) {
        ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
        String AID = parser.getAID(UID, albumNames.get(position));
        parser.deleteObject("Albums",AID);
        isShowDelete = false;
    }

    private void refreshList(){
        myList.clear();
        albumNames = parser.getListAlbum(UID,0);
        if(albumNames != null){
            for(int i=0;i<albumNames.size();i++){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemImage", R.drawable.ic_local_see_black_24dp);
                map.put("ItemText",albumNames.get(i));
                myList.add(map);
            }
        }
        //set listener
        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);
        mAdapter = new GridItemAdapter(AlbumActivity.this, myList);
        mGridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}