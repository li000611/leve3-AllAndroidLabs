package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
    }
/*
    final CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
    if(checkBox.ischecked()){
        checkBox.setChecked(false);
    }


    Button button=(Button)findViewById(R.id.checkBox);
         button.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 StringBuffer result = new StringBuffer();
                 result.append("Here is more information").append(button.isSelected());
                 Toast.makeText(MainActivity.this, result.toString(),
                    Toast.LENGTH_LONG.show();

        }

    });

*/
    }
