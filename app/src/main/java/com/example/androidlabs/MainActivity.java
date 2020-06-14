package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
//    private TextView mTextViewResult;
//    private EditText mEditTextNumber1;
//    private EditText mEditTextNumber2;

    private SharedPreferences mPrefs;

    private static final String TAG = "myLog";

    protected void onPause() {
        super.onPause();
        mPrefs = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        EditText editEmailText = findViewById(R.id.enterYourNameID);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("pref_user_email", editEmailText.getText().toString());
        ed.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
        mPrefs = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        // Load your screen


        String userEmail = mPrefs.getString("pref_user_email", "");

        EditText editEmailText = findViewById(R.id.enterYourNameID);
        editEmailText.setText(userEmail);

        Button bn = findViewById(R.id.buttonLoginID);

        bn.setOnClickListener(v -> {

            String userEmail2 = editEmailText.getText().toString();

            Intent goToProfileActivity = new Intent( MainActivity.this, ProfileActivity.class);
            goToProfileActivity.putExtra("pass_user_email", userEmail2);
            startActivityForResult(goToProfileActivity, 1);
            Log.d(TAG, "loading screen 2");
        });

//b = findViewById(R.id.checkboxID);
//        cb.setOnCheckedChangeListener((target, isChecked) -> {
//            Snackbar sb = Snackbar.make(target,
//                    getString(R.string.snackbar_text_The_switch_is_now) + " " +
//                            (isChecked ? getString(R.string.snackbar_text_On) : getString(R.string.snackbar_text_Off)), 5000);
//            sb.show();
//            sb.setAction(getString(R.string.snackbar_text_Undo), click -> target.setChecked(!isChecked));
//        });
//
//        Switch sh = findViewById(R.id.switchID);
//        sh.setOnCheckedChangeListener((target, isChecked) -> {
//            Snackbar sb = Snackbar.make(target,
//                    getString(R.string.snackbar_text_The_switch_is_now) + " " +
//                            (isChecked ? getString(R.string.snackbar_text_On) : getString(R.string.snackbar_text_Off)), 5000);
//            sb.show();
//            sb.setAction( getString(R.string.snackbar_text_Undo), click -> target.setChecked(!isChecked));
//        });
//
    }

}
