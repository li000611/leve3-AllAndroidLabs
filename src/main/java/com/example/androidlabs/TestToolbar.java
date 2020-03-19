package com.example.androidlabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;



public class TestToolbar extends AppCompatActivity {
    Toolbar myToolbar;
    String message="Toolbar test!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }  });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                alertExample();
            case R.id.item3:
                 Snackbar sb = Snackbar.make(myToolbar, "Go Back?", Snackbar.LENGTH_LONG)
                         .setAction("Finish", e-> finish());
                 sb.show();
                 break;
            case R.id.item4:
                Toast.makeText(this,"You clicked on the overflow menu",Toast.LENGTH_LONG).show();
            }
            return true;
    }

    private void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.item2_dialogbox, null);
        EditText et = (EditText)middle.findViewById(R.id.textView13);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Message")
                .setPositiveButton("Positive",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        message=et.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id){
                        }
                }).setView(middle);
        builder.create().show();
    }


}
