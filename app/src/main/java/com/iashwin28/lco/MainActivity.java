package com.iashwin28.lco;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!isNetworkAvailable()) {
            Log.e("PV", "not connected");


            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Let's fix the satellites !")
                    .setCustomImage(R.drawable.no_internet)
                    .setConfirmText("FIX")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    })
                    .show();
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }

    public void testpage(View v)
    {


        if(!isNetworkAvailable())
        {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("No Internet")
                    .setContentText("Let's fix the satellites !")
                    .setCustomImage(R.drawable.no_internet)
                    .setConfirmText("FIX")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    })
                    .show();
        }
        else {

            EditText usr = (EditText) findViewById(R.id.username);
            EditText pass = (EditText) findViewById(R.id.pwd);
            if ((usr.getText().toString().trim()).equals("admin") && (pass.getText().toString().trim()).equals("test1234")) {
                Intent i = new Intent(MainActivity.this, MainScreen.class);
                startActivity(i);
                finish();
            } else {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Incorrect credentials")
                        .setContentText("You've entered incorrect Username/Password")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); //method check for network
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void viewad(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE); //method for advertise
        intent.setData(Uri.parse("https://courses.learncodeonline.in"));
        startActivity(intent);
    }
}
