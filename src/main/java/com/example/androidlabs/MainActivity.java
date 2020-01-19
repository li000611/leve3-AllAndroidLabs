package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import static com.example.androidlabs.R.id.checkBox;
import static com.example.androidlabs.R.id.switch2;
import static com.example.androidlabs.R.id.editText;

public class MainActivity<compoundButton> extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    Button mySecondButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
/*
       // Button mySecondButton = null;

      //  mySecondButton = (Button) findViewById(R.id.checkBox);

      CheckBox cb = findViewById(R.id.checkBox);
        mySecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toastMessage = MainActivity.this.getResources().getString(R.string.toast_message);
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
*/


  /*The second way to add a click listener

       public void myFirstMethod(View v){
        Toast.makeText(getApplicationContext(),"Here is more information",Toast.LENGTH_SHORT).show();
       }
*/


        EditText theEdit = findViewById(R.id.editText);
        Switch sb = findViewById(R.id.switch2);


        sb.setOnCheckedChangeListener((compoundButton, b) -> {
            Snackbar.make(theEdit, "Switch is on " + b, Snackbar.LENGTH_LONG)
                    .setAction("Undo", click -> compoundButton.setChecked(b))
                    .show();
        });

        CheckBox cb = findViewById(R.id.checkBox);

        cb.setOnCheckedChangeListener((compoundButton, b) -> {
            Snackbar.make(theEdit, "Checkbox is checked " + b, Snackbar.LENGTH_LONG)
                    .setAction("Undo", click -> compoundButton.setChecked(b))
                    .show();
        });

    }

    public void BasicToast(View view ){
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(R.string.toast_message);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }
}




