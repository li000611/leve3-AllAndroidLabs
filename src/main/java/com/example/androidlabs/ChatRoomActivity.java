package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> list = new ArrayList<Message>( Arrays.asList( "one", "Two"/*Empty*/ ) );
    private MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter(myAdapter = new MyListAdapter());

        myList.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("A title")

                    //What is the message:
                    .setMessage("Do you want to add stuff")

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        list.add("HELLO");
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> {
                    })

                    //You can add extra layout elements:
                    .setView(getLayoutInflater().inflate(R.layout.row_layout, null))

                    //Show the dialog
                    .create().show();
            return true;
        });

        //Whenever you swipe down on the list, do something:
        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> refresher.setRefreshing(false));
    }
        class MyListAdapter extends BaseAdapter {
            public int getCount() { return list.size(); }

            public Object getItem(int position) { return list.get(position); }

            public long getItemId(int position) { return (long) position; }

            public View getView(int position, View old, ViewGroup parent)
             {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();
            Message thisMss = getItem(position);

            //make a new row:
            if(thisMess.isSend()) {
                newView = inflater.inflate(R.layout.row_layout, parent, false);

            }
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;
        }
    }

    }

