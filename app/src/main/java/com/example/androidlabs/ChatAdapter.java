package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

// here's our beautiful adapter
public class ChatAdapter extends ArrayAdapter<Message> {

    Context mContext;
    ArrayList<Message> messages = new ArrayList<>();

    public ChatAdapter(Context mContext, ArrayList<Message> messages) {
//        https://developer.android.com/reference/android/widget/ArrayAdapter
        super(mContext, 0, messages);

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
        return position + 1;
    }

    // SOURCE: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int messageView;

        Message message = getItem(position);
        Long id = getItemId(position);

        if(convertView == null){
            // inflate the layout
            if(message.isSend)
                messageView = R.layout.activity_chat_send;
            else
                messageView = R.layout.activity_chat_receive;

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(messageView, parent, false);

            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewMessage);
            textViewItem.setText(message.message);

            RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.messageContainer);
            rl.setOnLongClickListener(new RelativeLayout.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setCancelable(true);
                    builder.setTitle("Do you want to delete this?");
                    builder.setMessage("The selected row is: " + position + "\n" + "The database id is: " + id);

                    builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("DELETE", "DELETING " + position + " OF " + messages.size());
                            remove(getItem(position));
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }

                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return false;
                }

            });
        }
        return convertView;
    }

}