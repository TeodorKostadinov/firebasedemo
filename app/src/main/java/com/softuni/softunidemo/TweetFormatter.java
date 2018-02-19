package com.softuni.softunidemo;

import java.util.List;

public class TweetFormatter {

    public static String formatTweets(List<Tweet>tweets) {
        StringBuilder sb = new StringBuilder();
        for (Tweet tweet : tweets             ) {
            sb.append(tweet.userEmail);
            sb.append(": ");
            sb.append(tweet.content);
            sb.append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}
