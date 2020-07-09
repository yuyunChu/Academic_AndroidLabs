package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    Button sendBtn;
    Button receiveBtn;

    ArrayList<Message> messages = new ArrayList<>();

    public static final String ACTIVITY_NAME = "CHAT_ROOM_ACTIVITY";

    Context context = this;

    ChatAdapter chatAdapter;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Message targetItem = chatAdapter.getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Do you want to delete this?");
                builder.setMessage("The selected row is: " + position + "\n" + "The database id is: " + chatAdapter.getItemId(position));

                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(ChatDB.TABLE_MESSAGES, ChatDB.COL_ID + "= ?", new String[]{Long.toString(chatAdapter.getItemId(position))});
                        messages.remove(position);
                        chatAdapter.notifyDataSetChanged();
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

                return true;
            }

        });


        loadDataFromDatabase();

    }

    private void loadDataFromDatabase() {
        //get a database connection:
        ChatDB dbOpener = new ChatDB(this);

        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at ChatDB.java for the definitions:
        String [] columns = {ChatDB.COL_ID, ChatDB.COL_MESSAGE, ChatDB.COL_SENT};
        //query all the results from the database:

        Cursor results = db.query(false, ChatDB.TABLE_MESSAGES, columns,
                null, null, null, null, null, null);
        printCursor(results, db.getVersion());

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageColIndex = results.getColumnIndex(ChatDB.COL_MESSAGE);
        int sentColIndex = results.getColumnIndex(ChatDB.COL_SENT);
        int idColIndex = results.getColumnIndex(ChatDB.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String messageText = results.getString(messageColIndex);
            Boolean isSend = (results.getInt(sentColIndex) == 1 ? true : false);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            messages.add(new Message(id, messageText, isSend));
            chatAdapter.notifyDataSetChanged();
        }

        results.close();

        //At this point, the contactsList array has loaded every row from the cursor.
    }

    public void buttonOnClick(boolean isSend){
        String messageText = editText.getText().toString();
        if(!messageText.equals("")) {
            addMessage(messageText, isSend);
            editText.setText("");

        }
    }

    private void addMessage(String messageText, boolean isSend){
        int sent = isSend ? 1 : 0;

        //add to the database and get the new ID
        ContentValues newRowValues = new ContentValues();

        //Now provide a value for every database column defined in MyOpener.java:
        //put string name in the NAME column:
        newRowValues.put(ChatDB.COL_MESSAGE, messageText);
        //put string email in the EMAIL column:
        newRowValues.put(ChatDB.COL_SENT, sent);

        //Now insert in the database:
        long newID = db.insert(ChatDB.TABLE_MESSAGES, null, newRowValues);

        Message message = new Message(newID, messageText, isSend);
        messages.add(message);
        chatAdapter.notifyDataSetChanged();
    }

    public void printCursor(Cursor c, int version){
//        The database version number, use db.getVersion() for the version number.
        Log.d("CURSOR", "Version: " + version);
//        The number of columns in the cursor
        Log.d("CURSOR", "Number of columns: " + c.getColumnCount());
//        The name of the columns in the cursor
        Log.d("CURSOR", "Column names: " + TextUtils.join(", ", c.getColumnNames()));
//        The number of rows in the cursor
        Log.d("CURSOR", "Number of rows: " + c.getCount());
//        Print out each row of results in the cursor.
        int i = 1;
        String output[];
        while(c.moveToNext()) {
            output = new String[c.getColumnCount()];
            for(int j = 0; j < c.getColumnCount(); j++) {
                output[j] = c.getString(j);
            }
            Log.d("CURSOR", "Row " + (i++) + ": " + TextUtils.join(", ", output));
        }

        c.moveToFirst();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

}