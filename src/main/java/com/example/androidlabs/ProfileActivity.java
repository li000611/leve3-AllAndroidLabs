package com.example.androidlabs;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton imageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra("email");
        EditText editText = findViewById(R.id.enteryouremail);

        editText.setText(email);
        imageButton.setOnClickListener(click -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        Button addButton = findViewById(R.id.goToChatRoomButton);
        if ( addButton != null )
        addButton.setOnClickListener(click -> {
                Intent intent = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivity(intent);
        });

        Button weatherForecastbutton = findViewById(R.id.weatherForecast);
        weatherForecastbutton.setOnClickListener(click ->{
            Intent weatherForecast = new Intent(ProfileActivity.this, WeatherForecast.class);
            startActivity(weatherForecast);
        });
        
        Button goToToolbar = findViewById(R.id.toolbar);
        goToToolbar.setOnClickListener(clk->{
            Intent toolbarIntent = new Intent(ProfileActivity.this,TestToolbar.class);
            startActivity(toolbarIntent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:onResume()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }


    private class MyListAdapter {
    }
}
