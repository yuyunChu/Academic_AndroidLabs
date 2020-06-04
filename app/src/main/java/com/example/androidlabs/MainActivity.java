package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Load your screen
        setContentView(R.layout.activity_main_linear);
//        setContentView(R.layout.activity_main_grid);
//        setContentView(R.layout.activity_main_relative);
        Button bn = findViewById(R.id.buttonClickID);
        bn.setOnClickListener(v -> {
            Toast.makeText(this, getString(R.string.toast_message),
                    Toast.LENGTH_LONG).show();

        });

        CheckBox cb = findViewById(R.id.checkboxID);
        cb.setOnCheckedChangeListener((target, isChecked) -> {
            Snackbar sb = Snackbar.make(target,
                    getString(R.string.snackbar_text_The_switch_is_now) + " " +
                            (isChecked ? getString(R.string.snackbar_text_On) : getString(R.string.snackbar_text_Off)), 5000);
            sb.show();
            sb.setAction(getString(R.string.snackbar_text_Undo), click -> target.setChecked(!isChecked));
        });

        Switch sh = findViewById(R.id.switchID);
        sh.setOnCheckedChangeListener((target, isChecked) -> {
            Snackbar sb = Snackbar.make(target,
                    getString(R.string.snackbar_text_The_switch_is_now) + " " +
                            (isChecked ? getString(R.string.snackbar_text_On) : getString(R.string.snackbar_text_Off)), 5000);
            sb.show();
            sb.setAction( getString(R.string.snackbar_text_Undo), click -> target.setChecked(!isChecked));
        });

    }

}
