package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    EditText editText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.editText1);
        loginButton = findViewById(R.id.button);

        if (loginButton != null)
            loginButton.setOnClickListener(click -> {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("email", editText.getText().toString());
                startActivity(intent);
            });
    }

        @Override
        protected void onPause () {
            super.onPause();
            prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
            String previous = prefs.getString("email", " ");
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", editText.getText().toString());
            editor.commit();
        }
}


