package com.aitruong.elbrus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AlbumCreateActivity extends AppCompatActivity {

    final AlbumCreateActivity thisActivity = this;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = (Data)getApplication();

        final EditText title = (EditText) findViewById(R.id.albumCreate_edit_title);
        final EditText description = (EditText) findViewById(R.id.albumCreate_edit_description);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = data.getParser().addAlbum(data.getUserID(), title.getText().toString(), description.getText().toString());
                if(result){
                    Snackbar.make(view, "Create Album Success!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    Snackbar.make(view, "Create Album Fail!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                thisActivity.finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
