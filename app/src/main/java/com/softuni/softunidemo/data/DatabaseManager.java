package com.softuni.softunidemo.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.softuni.softunidemo.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance;
    private final DatabaseReference tweetsRef;
    private StorageReference mStorageRef;

    public static DatabaseManager getInstance(){
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private DatabaseManager() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        tweetsRef = database.getReference("tweets");
        mStorageRef = FirebaseStorage.getInstance().getReference("photos");
    }

    public void getTweets(final DataListener<List<Tweet>> listener) {
        tweetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Tweet> tweets = new ArrayList<>();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    Tweet tweet = childSnapshot.getValue(Tweet.class);
                    tweets.add(tweet);
                }
                listener.onDataReady(tweets);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                listener.onError();
            }
        });
    }

    public void sendTextTweet(String tweetText, String email, final DataListener<String> listener) {
        sendTweet(tweetText, "text", email, listener);
    }

    public void sendTweet(String tweetText, String type, String email, final DataListener<String> listener) {
        Tweet tweet = new Tweet(tweetText, type, email);
        tweetsRef.push().setValue(tweet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onDataReady(null);
            }
        });
    }

    public void sendPhotoTweet(Context context, Bitmap imageBitmap, final String userEmail, final DataListener<String> listener) {
        Uri file = Uri.fromFile(FileUtils.bitmapToTempFile(context, imageBitmap));

        mStorageRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        sendTweet(downloadUrl.toString(), "image", userEmail, listener);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        listener.onError();
                    }
                });
    }

    public interface DataListener<T> {
        void onDataReady(T data);
        void onError();
    }
}
