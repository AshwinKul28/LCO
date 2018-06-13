package com.iashwin28.lco;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainScreen extends AppCompatActivity {

    public ArrayList<String> questions;
    public ArrayList<String> answers;
    public float part, gap;
    public int count = 0;
    public String JSON_STRING = "https://learncodeonline.in/api/android/datastructure.json";
    public JSONObject jso;
    public JSONArray arr;
    String question, answer;
    public String line, test;
    int i, j;
    SweetAlertDialog pDialog;
    ImageView add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        questions = new ArrayList<String>();
        answers = new ArrayList<String>();


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("Thinking best Questions for you!");
        pDialog.setCancelable(false);
        pDialog.show();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //Your code goes here


                    try {
                        URL twitter = new URL(JSON_STRING);
                        URLConnection tc = twitter.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                tc.getInputStream()));


                        while ((line = in.readLine()) != null) {
                            test += line;
                        }
                        runOnUiThread(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), "res="+test, Toast.LENGTH_LONG).show();
                                Log.d("abc", "web=" + test);
                            }
                        }));

                        String crappyPrefix = "null";

                        if (test.startsWith(crappyPrefix)) {
                            test = test.substring(crappyPrefix.length(), test.length());
                        }


                        jso = new JSONObject(test);
                        arr = jso.getJSONArray("questions");
                        runOnUiThread(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), "res="+arr, Toast.LENGTH_LONG).show();
                                Log.d("abc", "res=" + arr);
                            }
                        }));


                        for (i = 0; i < arr.length(); i++) {
                            JSONObject jo = (JSONObject) arr.get(i);
                            Log.d("abc", "jo=" + jo);

                            question = jo.getString("question");
                            answer = jo.getString("Answer");


                            questions.add(question);
                            //Toast.makeText(MainScreen.this, "question="+question, Toast.LENGTH_SHORT).show();
                            answers.add(answer);


                            Log.d("abc", "q=" + question + " a=" + answer);

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (i = 0; i < questions.size(); i++) {
                                    Log.e("abc", "text=" + questions.get(i).toString());
                                }


                                int length = questions.size();
                                Log.d("abc", "length=" + length);
                                part = 100 / (length + 1);
                                gap = 0;

                                pDialog.dismissWithAnimation();

                                CardStackView cs = (CardStackView) findViewById(R.id.swipeStack);
                                cs.setVisibleCount(length);
                                cs.setAdapter(new SwipeAdapter(getApplicationContext(), questions, answers));

                            }
                        });


                    } catch (final Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                Log.d("abc", "res=" + e.toString());

                            }
                        }));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


//        for (i=0;i<questions.size();i++)
//        {
//            Log.e("abc","text="+questions.get(i).toString());
//        }
//
//
//
//


//
//        int length = questions.size();
//        Log.d("abc","length="+length);
//        part = 100 / (length + 1);
//        gap = 0;
//
//
//
//        CardStackView cs = (CardStackView) findViewById(R.id.swipeStack);
//        cs.setAdapter(new SwipeAdapter(this, questions, answers));
//


    }
    public void reset(View v)
    {
        new SweetAlertDialog(MainScreen.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Read Again")
                .setConfirmText("From Start")
                .setCancelText("Previous Q")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        CardStackView cs = (CardStackView) findViewById(R.id.swipeStack);
                        cs.reverse();
                        sweetAlertDialog.dismiss();

                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        CardStackView cs = (CardStackView) findViewById(R.id.swipeStack);
                        cs.setAdapter(new SwipeAdapter(getApplicationContext(),questions,answers));
                        sDialog.dismiss();
                    }
                })
                .show();



    }

    public void viewad(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://courses.learncodeonline.in"));
        startActivity(intent);
    }

}
