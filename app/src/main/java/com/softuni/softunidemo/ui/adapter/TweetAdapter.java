package com.softuni.softunidemo.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softuni.softunidemo.R;
import com.softuni.softunidemo.data.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetHolder> {

    private List<Tweet> data = new ArrayList<>();

    @Override
    public TweetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);
        TweetHolder vh = new TweetHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TweetHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Tweet> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class TweetHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_user_email) TextView txtUserEmail;
        @BindView(R.id.txt_date) TextView txtDate;
        @BindView(R.id.txt_tweet_text) TextView txtTweetText;
        @BindView(R.id.img_tweet) ImageView imgTweet;

        private Context context;

        public TweetHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
        }

        public void setData(Tweet tweet) {
            txtUserEmail.setText(tweet.userEmail);
//            txtDate.setText(new SimpleDateFormat("MM dd").format(new Date(tweet.timestamp).toString()));
            if(Objects.equals(tweet.type, Tweet.TYPE_TEXT)) {
                txtTweetText.setText(tweet.content);
                imgTweet.setVisibility(View.GONE);
                txtTweetText.setVisibility(View.VISIBLE);
            } else if(Objects.equals(tweet.type, Tweet.TYPE_IMAGE)) {
                txtTweetText.setVisibility(View.GONE);
                Picasso.with(context).load(tweet.content).into(imgTweet);
                imgTweet.setVisibility(View.VISIBLE);
            }
        }
    }
}
