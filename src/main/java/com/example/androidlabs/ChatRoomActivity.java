package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> list = new ArrayList<>();
    private MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.theListView);
        Button sendButton = findViewById(R.id.sendButton);
        Button receiveButton = findViewById(R.id.receiveButton);
        EditText userType = findViewById(R.id.typeHere);

        myList.setAdapter(myAdapter = new MyListAdapter());

        sendButton.setOnClickListener(click -> {
            Message newMessage = new Message(1, userType.getText().toString(), false);
            list.add(newMessage);
            myAdapter.notifyDataSetChanged();
            userType.setText("");
        });


        receiveButton.setOnClickListener(click -> {
            Message receiveMessage = new Message(2, userType.getText().toString(), true);
            list.add(receiveMessage);
            myAdapter.notifyDataSetChanged();
            userType.setText("");
        });


        myList.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("A title")

                    //What is the message:
                    .setMessage("Do you want to delete this? " +
                            "The selected row is: "+id+
                            " The database id id" +id)

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        list.remove(pos);
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
        //  SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        //  refresher.setOnRefreshListener(() -> refresher.setRefreshing(false));
    }

    class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            if (list.get(position).isSend()) {
                newView = inflater.inflate(R.layout.sendbutton_layout, parent, false);
            } else {
                newView = inflater.inflate(R.layout.receivebutton_layout, parent, false);
            }
            //return it to be put in the table

            //set what the text should be for this row:
            TextView tView = (TextView) newView.findViewById(R.id.msgText);
            tView.setText(((Message) getItem(position)).getMessage());
            return newView;

        }
    }

}

