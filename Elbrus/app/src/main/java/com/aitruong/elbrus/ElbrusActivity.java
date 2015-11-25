package com.aitruong.elbrus;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class ElbrusActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ActionBar actionBar = getActionBar();
        //actionBar.hide();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_elbrus);


        fbLoginButton = (LoginButton)findViewById(R.id.login_button);
        fbLoginButton.setReadPermissions("user_friends");

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {


                Toast.makeText(ElbrusActivity.this, "Login successfull!", Toast.LENGTH_LONG).show();



//                System.out.println("Facebook Login Successful!");
//                System.out.println("Logged in user Details : ");
//                System.out.println("--------------------------");
//                System.out.println("User ID  : " + loginResult.getAccessToken().getUserId());
//                System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
//                Toast.makeText(ElbrusActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(ElbrusActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(ElbrusActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent i) {

        callbackManager.onActivityResult(reqCode, resCode, i);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_elbrus, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void openAlbum(View view){
        Intent intent = new Intent(this,AlbumActivity.class);
        startActivity(intent);
    }


}
