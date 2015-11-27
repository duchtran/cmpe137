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

import java.util.ArrayList;
import java.util.HashMap;

public class AlbumActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    final AlbumActivity thisActivity=this;
    public final static String MESSAGE_ALBUM_ID = "com.aitruong.elbrus.ElbrusActivity.MESSAGE_ALBUM_ID";

    private Data data;
    private GridView mGridView;
    private GridItemAdapter mAdapter;
    private boolean isShowDelete = false;
    private ArrayList<HashMap<String, Object>> myList;

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
        myList = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<10;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.ic_local_see_black_24dp);
            map.put("ItemText", "NO."+String.valueOf(i));
            myList.add(map);
        }

        //set listener
        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);
        mAdapter = new GridItemAdapter(AlbumActivity.this, myList);
        mGridView.setAdapter(mAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album, menu);
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
        Intent intent = new Intent(thisActivity,AlbumDetailActivity.class);
        //for test
        String message = String.valueOf(arg2);
        intent.putExtra(MESSAGE_ALBUM_ID,message);
        startActivity(intent);
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
            View deleteView = convertView.findViewById(R.id.delete_markView);
            //deleteView.setOnClickListener();
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    delete(position);
                    mAdapter = new GridItemAdapter(AlbumActivity.this, myList);
                    mGridView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }

            });
        }

        mAdapter.setIsShowDelete(isShowDelete);

        return true;
    }


    private void delete(int position) {
        ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
        if (isShowDelete) {
            myList.remove(position);
            isShowDelete = false;
        }
        newList.addAll(myList);
        myList.clear();
        myList.addAll(newList);
    }

    private ArrayList<HashMap<String, Object>> getMenuAdapter(
                                                              int[] menuImageArray, String[] menuNameArray) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < menuImageArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", menuImageArray[i]);
            map.put("name", menuNameArray[i]);
            data.add(map);
        }
        return data;
    }
}