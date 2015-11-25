package com.aitruong.elbrus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

public class InviteActivity extends AppCompatActivity {
    private CallbackManager CallbackManager;
    private AppInviteDialog inviteDialog;
    private AppInviteContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_invite);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
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


    public static void openDialogInvite(final InviteActivity activity)
    {
        String appLinkUrl, previewImageUrl;

        appLinkUrl = " https://fb.me/878949265557048";
        previewImageUrl = "https://www.example.com/my_invite_image.jpg";

        if (AppInviteDialog.canShow())
        {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();

            AppInviteDialog appInviteDialog = new AppInviteDialog(activity);


            appInviteDialog.show(content);
        }
    }

}
