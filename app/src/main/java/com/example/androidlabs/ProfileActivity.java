package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {


    private static final String TAG = "myLog";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    ImageButton activateChatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.e(ACTIVITY_NAME, "In function:" + (new Throwable().getStackTrace()[0].getMethodName()));

        Intent loadfromMainActivity = getIntent();
        String userEmail = loadfromMainActivity.getStringExtra("pass_user_email");

        EditText emailEditText = findViewById(R.id.enterYourEmailID);
        emailEditText.setText(userEmail);

     //   Log.d(TAG, userEmail);
        activateChatBtn = findViewById(R.id.imageButtonID);

        activateChatBtn.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        ImageButton activateChatBtn = (ImageButton) findViewById(R.id.activateChatBtnID);
        activateChatBtn.setOnClickListener(c -> {
            Intent chatRoomPage = new Intent(ProfileActivity.this, ChatRoomActivity.class);

            startActivityForResult(chatRoomPage, 345);

        });
/**** https://stackoverflow.com/questions/24610527/how-do-i-get-a-button-to-open-another-activity
      Hit the button to go to the WeatherForecast.class
*****/
        Button btn = (Button)findViewById(R.id.weatherBtnID);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, WeatherForecast.class));
            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + (new Throwable().getStackTrace()[0].getMethodName()) );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" +  (new Throwable().getStackTrace()[0].getMethodName()) );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" +  (new Throwable().getStackTrace()[0].getMethodName()) );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + (new Throwable().getStackTrace()[0].getMethodName()) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" +  (new Throwable().getStackTrace()[0].getMethodName()) );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            activateChatBtn.setImageBitmap(imageBitmap);
        }
    }

}