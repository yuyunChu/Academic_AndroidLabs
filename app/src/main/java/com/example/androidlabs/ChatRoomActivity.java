package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    ArrayList<Message> messages = new ArrayList<>();
    Button sendBtn;
    Button receiveBtn;

    public static final String ACTIVITY_NAME = "CHAT_ROOM_ACTIVITY";

    Context context = this;

    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Log.e(ACTIVITY_NAME, "In function:" + (new Throwable().getStackTrace()[0].getMethodName()));

        listView = (ListView)findViewById(R.id.listItemID);
        editText = (EditText)findViewById(R.id.typeHereHint_text);
        sendBtn = (Button)findViewById(R.id.sendButtonID);
        receiveBtn = (Button)findViewById(R.id.receiveButtonID);

        chatAdapter = new ChatAdapter(context, messages);

        ListView listView = (ListView) findViewById(R.id.listItemID);
        listView.setAdapter(chatAdapter);

        sendBtn.setOnClickListener(c -> {
            buttonOnClick(true);
        });

        receiveBtn.setOnClickListener(c -> {
            buttonOnClick(false);
        });

    }

    public void buttonOnClick(boolean send){
        String messageText = editText.getText().toString();
        if(!messageText.equals("")) {
            Message message = new Message(messageText, send);
            editText.setText("");
            chatAdapter.add(message);
        }
    }

}