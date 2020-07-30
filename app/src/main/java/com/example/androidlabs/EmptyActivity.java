package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class EmptyActivity extends AppCompatActivity {

    private static final String MESSAGE = "message",
            IS_SEND = "isSend",
            ITEM_ID = "itemId",
            IS_PHONE = "isPhone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_empty);
        Intent loadfromChatActivity = getIntent();
        Bundle args = loadfromChatActivity.getExtras();

        DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
        dFragment.setArguments(args); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentDetailsID, dFragment) //Add the fragment in FrameLayout
                .commit();

    }

}