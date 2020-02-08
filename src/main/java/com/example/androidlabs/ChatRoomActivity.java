package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {
    ArrayList<Message> messageList = new ArrayList<>();
    private ChatRoomActivity.MyListAdapter myAdapter;
    private static int ACTIVITY_VIEW_MESSAGE = 33;
    int positionClicked = 0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.theListView);
        Button sendButton = findViewById(R.id.sendButton);
        Button receiveButton = findViewById(R.id.receiveButton);
        EditText userType = findViewById(R.id.typeHere);

        //get any previously saved Message objects
        loadDataFromDatabase();

        //This listener for items being clicked in the list view)
        myList.setOnItemClickListener(((parent, view, position, id) -> {
            showMessage(position);
        }));

        sendButton.setOnClickListener(click -> {

            //get the message that were typed
            String message = userType.getText().toString();

            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:

            //put string message in the EMAIL column:
            newRowValues.put(MyOpener.COL_MESSAGE, message);

            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Message object
            boolean isSend;
            Message newMe = new Message(newId, message, isSend);

            //add the new contact to the list:
            messageList.add(newMe);
            //update the listView:
            myAdapter.notifyDataSetChanged();

            //clear the EditText fields:
            userType.setText("");

            //Show the id of the inserted item:
            Toast.makeText(this, "Inserted item id:" + newId, Toast.LENGTH_LONG).show();
        });

        receiveButton.setOnClickListener(click -> {

            //get the message that were typed
            String message = userType.getText().toString();

            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:

            //put string message in the EMAIL column:
            newRowValues.put(MyOpener.COL_MESSAGE, message);

            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Message object
            boolean isReceived;
            Message newMe = new Message(newId, message, isReceived);

            //add the new contact to the list:
            messageList.add(newMe);
            //update the listView:
            myAdapter.notifyDataSetChanged();

            //clear the EditText fields:
            userType.setText("");

            //Show the id of the inserted item:
            Toast.makeText(this, "Inserted item id:" + newId, Toast.LENGTH_LONG).show();
        });
        //create an adapter ojbect and send it to the listView
        myList.setAdapter(myAdapter = new MyListAdapter());
    }

    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        //we want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE};

        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that math the query.
        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String message = results.getString(messageColumnIndex);
            long id = results.getLong(idColIndex);

            //add the new message to the array list
            messageList.add(new Message(message, id));
        }
    }

    protected void showMessage(int position) {
        Message selectedMessage = messageList.get(position);

        View message_view = getLayoutInflater().inflate(R.layout.message_eidt, root:null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You clicked on item #" + position)
                .setMessage("You can update the fields and then click update to save in the database")
                .setView(message_view) //add the 1 edit texts showing the message information
                .setPositiveButton("Update", (click, b) -> {
                    selectedMessage.update(rowMessage.getText().toString());
                    updateMessage(selectedMessage);
                    myAdapter.notifyDataSetChanged(); //the email and name have changed so rebuild the list
                })
                .setNegativeButton("Delete", (click, b) -> {
                    deleteMessage(selectedMessage); //remove the contact from database
                    messageList.remove(position); //remove the contact from contact list
                    myAdapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> {
                })
                .create().show();
    }

    protected void updateMessage(Message m) {
        //Create a ContentValues object to represent a database row:
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(MyOpener.COL_MESSAGE, m.getMessage());

        //now call the update function:
        db.update(MyOpener.TABLE_NAME, updatedValues, MyOpener.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
    }

    protected void deleteMessage(Message m) {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
    }


    class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return messageList.size();
        }

        public Object getItem(int position) {
            return messageList.get(position);
        }

        //  public long getItemId(int position) {
        //    return (long) position;
        //}

        public View getView(int position, View old, ViewGroup parent) {
            View newView = getLayoutInflater().inflate(R.layout.message_row, parent, false);
            Message thisRow = (Message) getItem(position);


            //get the TextViews
            TextView rowName = (TextView) newView.findViewById(R.id.row_message);
            TextView rowId = (TextView) newView.findViewById(R.id.row_id);

            //update the text fields:
            rowName.setText(thisRow.getMessage());
            rowId.setText("id:" + thisRow.getId());

            //return the row:
            return newView;
        }

        //last week we returned (long) position. Now we return the object's database id that we get from line 73
        public long getItemId(int position) {
            return position;
        }

    }


}
