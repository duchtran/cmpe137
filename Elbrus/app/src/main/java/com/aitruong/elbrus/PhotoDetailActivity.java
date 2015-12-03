package com.aitruong.elbrus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import android.widget.Button;

public class PhotoDetailActivity extends AppCompatActivity {

    private Data data;
    ImageView image;
    EditText leaveCommentEditText;
    TextView leaveCommentTextView;
    Button addCommentButton;

//    TextView listOfCommentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        data = (Data)getApplication();

        toolbar.setTitle(data.getCurrentPhotoName());
        setSupportActionBar(toolbar);

        //show note
        TextView note = (TextView) findViewById(R.id.photoDescription);
        note.setText(data.getCurrentPhotoNote());

        //show image
        image = (ImageView) findViewById(R.id.photo_image);
        Bitmap bmp =  data.getParser().getSpecificPhotoFile(data.getUserID(), data.getCurrentAlbumID(), data.getCurrentPhotoName());

        if(bmp!=null){
            image.setImageBitmap(bmp);
        }

        leaveCommentEditText=(EditText)findViewById(R.id.photo_comment);
        final String comment = new String();
        leaveCommentTextView = (TextView)findViewById(R.id.list_of_comments);
        leaveCommentTextView.setText(comment);

        addCommentButton = (Button)findViewById(R.id.add_comment_button);

        addCommentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String newComment = leaveCommentEditText.getText().toString();
                boolean isCommentAdd = data.getParser().addComment(data.getCurrentPhotoID(),newComment);
                leaveCommentEditText.setText("");
                ArrayList<String> listCommendArray=data.getParser().getComments(data.getCurrentPhotoID());
                String listString = new String();
                for(int i=0;i<listCommendArray.size();i++){
                    listString+=listCommendArray.get(i);
                    listString+="\n";
                }
                leaveCommentTextView.setText(listString);




//

            }

        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
