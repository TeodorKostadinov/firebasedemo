package com.softuni.softunidemo.data;

public class Tweet {
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "image";

    public String userEmail;
    public String type;
    public String content;
    public long timestamp;

    public Tweet() {
    }

    public Tweet(String tweetText, String type, String email) {
        this.content = tweetText;
        this.type = type;
        this.userEmail = email;
    }
}
