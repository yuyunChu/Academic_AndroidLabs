package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<Message> listMessage = new ArrayList<>();
    Button sendBtn;
    Button receiveBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        listView = (ListView)findViewById(R.id.listItemID);
        editText = (EditText)findViewById(R.id.typeHereHint_text);
        sendBtn = (Button)findViewById(R.id.sendButtonID);
        receiveBtn = (Button)findViewById(R.id.receiveButtonID);


        sendBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            Message model = new Message(message, true);
            listMessage.add(model);
            editText.setText("");
            ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(adt);
        });

        receiveBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            Message model = new Message(message, false);
            listMessage.add(model);
            editText.setText("");
            ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(adt);
        });


        Log.d("ChatRoomActivity","onCreate");

    }


}