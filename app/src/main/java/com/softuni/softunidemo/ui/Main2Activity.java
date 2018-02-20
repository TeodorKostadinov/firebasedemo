package com.softuni.softunidemo.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.softuni.softunidemo.R;
import com.softuni.softunidemo.data.DatabaseManager;
import com.softuni.softunidemo.data.LoginManager;
import com.softuni.softunidemo.data.Tweet;
import com.softuni.softunidemo.ui.adapter.TweetAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Main2Activity extends AppCompatActivity {

    public static final int REQUEST_CODE = 123;
    private TweetAdapter adapter;

    @BindView(R.id.rec_view) RecyclerView recyclerView;
    @BindView(R.id.edt_tweet) EditText edtTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2main);
        ButterKnife.bind(this);
        initViews();
        requestPermissionIfNeeded();
        getAndSetupData();
    }

    private void initViews() {
        adapter = new TweetAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getAndSetupData() {
        DatabaseManager.getInstance().getTweets(new DatabaseManager.DataListener<List<Tweet>>() {
            @Override
            public void onDataReady(List<Tweet> data) {
                setupData(data);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void setupData(List<Tweet> data) {
        adapter.setData(data);
    }

    @OnClick(R.id.btn_tweet)
    public void onTweet() {
        String tweet = edtTweet.getText().toString();
        String email = LoginManager.getInstance().getUserEmail();
        DatabaseManager.getInstance().sendTextTweet(tweet, email, new DatabaseManager.DataListener<String>() {
            @Override
            public void onDataReady(String data) {
                Toast.makeText(Main2Activity.this, "Tweet sent!", Toast.LENGTH_SHORT).show();
                getAndSetupData();
            }

            @Override
            public void onError() {

            }
        });
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClicked() {
        LoginManager.getInstance().logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.btn_tweet_photo)
    public void onTweetPhotoClicked() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE);
    }

    @OnClick(R.id.btn_crash)
    public void onCrashClicked() {
        startActivity(new Intent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            DatabaseManager.getInstance().sendPhotoTweet(this, imageBitmap, LoginManager.getInstance().getUserEmail(), new DatabaseManager.DataListener<String>() {
                @Override
                public void onDataReady(String data) {
                    Toast.makeText(Main2Activity.this, "Photo tweeted", Toast.LENGTH_SHORT).show();
                    getAndSetupData();
                }

                @Override
                public void onError() {
                    Toast.makeText(Main2Activity.this, "Photo failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void requestPermissionIfNeeded() {
        if (ContextCompat.checkSelfPermission(this,
                WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{WRITE_EXTERNAL_STORAGE},
                    12);
        }
    }
}
