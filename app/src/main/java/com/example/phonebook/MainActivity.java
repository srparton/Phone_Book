package com.example.phonebook;
/*
    Phone Book: This app will create a address book for phone numbers. It will take in first name, last name,
    and phone number. It should display the contact info (which it wont right now for some reason)
    clear text fields, add new contact and delete contact. All fucntions except displaying the
    address book work.

    Make Call: This app will query data from database and show the phone number associated with name
    then the app will make a call to that number.

    Learning - What i learned was how to get a content provider working as well as how to test a
    database on a mobile device. I didnt think to delete the app and thus clear the database until
    a long while of debugging had taken place. It was frustrating to test as well becasue in some
    devices my app worked but on my brothers newer android app the make call app did not work.
   author Spencer Parton and Nick Gallegos
   version 03/28/2022
*/
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //----------------------------------------------
    EditText fnameBox, lnameBox, phnNumBox;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fnameBox = findViewById(R.id.firstName);
        lnameBox = findViewById(R.id.lastName);
        phnNumBox = findViewById(R.id.phoneNumber);
    }

    //------------------------------------------
    public void show(View view) {
        fnameBox.setText("");
        lnameBox.setText("");
        phnNumBox.setText("");
        fnameBox.requestFocus();
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    //-------------------------------------------------------
    public void newContacts(View view) {
        int pNum =
        Log.d("parseInt", "phoneNum: "+Long.parseLong(phnNumBox.getText().toString()));
        Contacts contacts = new Contacts(fnameBox.getText().toString(), lnameBox.getText().toString(),
                Long.parseLong(phnNumBox.getText().toString()));
        // class instance used to add values in the database
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.COLUMN_FNAME, contacts.getFirstName());
        values.put(MyContentProvider.COLUMN_LNAME, contacts.getLastName());
        values.put(MyContentProvider.COLUMN_PHONENUM,contacts.getPhoneNum());
        // please note how we access our table- using ContentResolver instance
        ContentResolver contentResolver = getContentResolver();
        Log.d("values", "values = "+values);
        Uri uri = contentResolver.insert(MyContentProvider.CONTENT_URI, values);
        fnameBox.setText("");
        lnameBox.setText("");
        phnNumBox.setText("");
    }
    //--------------------------------------------------------
    public void clear(View view){
        fnameBox.setText("");
        lnameBox.setText("");
        phnNumBox.setText("");
        fnameBox.requestFocus();
    }

//    public void lookupContacts(View view) {
//        String[] projection = {MyContentProvider.COLUMN_FNAME, MyContentProvider.COLUMN_LNAME,
//                MyContentProvider.COLUMN_PHONENUM};
//        String selection = "Contact Phone Number = \"" + phnNumBox.getText().toString() + "\"";
//        // access our table through a ContentResolver instance
//        ContentResolver contentResolver = getContentResolver();
//        Cursor cursor = contentResolver.query(MyContentProvider.CONTENT_URI, projection, selection, null, null);
//
//        Contacts contacts = new Contacts();
//        if (cursor.moveToFirst()) {
//            contacts.setId(cursor.getInt(0));
//            contacts.setFirstName(cursor.getString(1));
//            contacts.setLastName(cursor.getString(3));
//            contacts.setPhoneNum(cursor.getInt(4));
//            cursor.close();
//        } else
//            contacts = null;
//        if (contacts != null) {
//            fnameBox.setText(String.valueOf(contacts.getFirstName()));
//            lnameBox.setText(String.valueOf(contacts.getLastName()));
//            phnNumBox.setText(String.valueOf(contacts.getPhoneNum()));
//        } else
//            phnNumBox.setText("No match found");
//    }

    //------------------------------------------------------------------------
    public void removeContacts(View view) {
        String selection = "fName = \"" + fnameBox.getText().toString() + "\"";
        ContentResolver contentResolver = getContentResolver();
        int result = contentResolver.delete(MyContentProvider.CONTENT_URI, selection, null);
        if (result > 0) {
            fnameBox.setText("");
            lnameBox.setText("");
            phnNumBox.setText("" + result + " record(s) deleted");
        } else phnNumBox.setText("No match found");
    }
}
