package com.aitruong.elbrus;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.EditText;

public class AlbumDetailActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{

    final AlbumDetailActivity thisActivity=this;

    private Data data;
    private GridView mGridView;
    private GridItemAdapter mAdapter;
    private boolean isShowDelete = false;
    private ArrayList<HashMap<String, Object>> myList = new ArrayList<HashMap<String, Object>>();


    ArrayList<String> photoNames;
    ArrayList<Bitmap> photoImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        data = (Data)getApplication();

        //show title
        toolbar.setTitle(data.getCurrentAlbumName());
        setSupportActionBar(toolbar);

        //show description
        TextView description = (TextView) findViewById(R.id.albumDescription);
        description.setText(data.getCurrentAlbumDescription());

        //show photos
        mGridView = (GridView) findViewById(R.id.photoGridView);

        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);



        refreshList();

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
            case R.id.action_share:
                openShare();
                return true;
            case R.id.action_photo_refresh:
                refreshList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void openCreatePhoto(){
        Intent intent = new Intent(this,PhotoCreateActivity.class);
        startActivity(intent);
    }

    public void openShare(){
        String url = new String();

        data = (Data)getApplication();
        List<String> photosName = data.getParser().getListPhotoNamesFromUser_Album(data.getUserID(),data.getCurrentAlbumID());

        for(int i=0;i<photosName.size();i++){
            url+=data.getParser().getPhotoUrl(data.getUserID(),data.getCurrentAlbumID(),photosName.get(i));
            url+="\n";
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_my_photo)));
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
            Intent intent = new Intent(thisActivity,PhotoDetailActivity.class);
            String PID = data.getParser().getPID(data.getUserID(),data.getCurrentAlbumID(),photoNames.get(arg2));
            data.setCurrentPhotoID(PID);
            data.setCurrentPhotoName(photoNames.get(arg2));
            data.setCurrentPhotoNote(data.getParser().getPhotoNote(PID));
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
        }

        //mAdapter.setIsShowDelete(isShowDelete);

        return true;
    }


    private void delete(int position) {
        ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
        String PID = data.getParser().getPID(data.getUserID(),data.getCurrentAlbumID(), photoNames.get(position));
        data.getParser().deleteObject("Photos", PID);
        isShowDelete = false;
    }

    private void refreshList(){
        myList = new ArrayList<HashMap<String, Object>>();
        photoNames = data.getParser().getListPhotoNamesFromUser_Album(data.getUserID(),data.getCurrentAlbumID());
        photoImages = data.getParser().getListPhotoFiles(data.getUserID(),data.getCurrentAlbumID());
        if(photoNames != null){
            for(int i=0;i<photoNames.size();i++){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemImage", photoImages.get(i));
                map.put("ItemText",photoNames.get(i));
                myList.add(map);
            }
        }
        //set listener
        mAdapter = new GridItemAdapter(AlbumDetailActivity.this, myList);
        mGridView.setAdapter(mAdapter);

        //mAdapter.setMyList(myList);
        mAdapter.notifyDataSetChanged();
        //mGridView.invalidate();
        //mGridView;
        //ListAdapter la = mGridView.getAdapter();

        //((SimpleAdapter)la).notifyDataSetChanged();
    }
}
