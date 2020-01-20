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
    CheckBox cb=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);

        cb = (CheckBox) findViewById(R.id.checkBox);
        EditText theEdit = findViewById(R.id.editText);
        Switch sb = findViewById(R.id.switch2);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toastMessage = MainActivity.this.getResources().getString(R.string.toast_message);
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        sb.setOnCheckedChangeListener((compoundButton, b) -> {
            Snackbar.make(theEdit, "Switch is on " + b, Snackbar.LENGTH_LONG)
                    .setAction("Undo", click -> compoundButton.setChecked(b))
                    .show();
        });


        cb.setOnCheckedChangeListener((compoundButton, b) -> {
            Snackbar.make(theEdit, "Checkbox is checked " + b, Snackbar.LENGTH_LONG)
                    .setAction("Undo", click -> compoundButton.setChecked(b))
                    .show();
        });
    }

    public void BasicToast(View view ){
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(R.string.toast_message);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }
}




