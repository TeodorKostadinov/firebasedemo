package com.softuni.softunidemo;

public class Tweet {
    public String userEmail;
    public String type;
    public String content;

    public Tweet() {
    }

    public Tweet(String tweetText, String type, String email) {
        this.content = tweetText;
        this.type = type;
        this.userEmail = email;
    }
}
