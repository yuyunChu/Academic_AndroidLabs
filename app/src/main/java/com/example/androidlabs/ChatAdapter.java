package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

// here's our beautiful adapter
public class ChatAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Message> messages = new ArrayList<>();

    public ChatAdapter(Context mContext, ArrayList<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position){
        return messages.get(position);
    }

    public long getItemId(int position){
        return getItem(position).getId();
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    // SOURCE: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int messageView;

        Message message = getItem(position);

        // inflate the layout
        if(message.isSend())
            messageView = R.layout.activity_main_send;
        else
            messageView = R.layout.activity_main_receive;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        convertView = inflater.inflate(messageView, parent, false);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewMessage);
        textViewItem.setText(message.getMessage());

        return convertView;

    }

}