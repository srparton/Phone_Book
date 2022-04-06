package com.example.phonebook;
/*
   This app shows how to share stored data in this app with another app.
   Android provides an interface called ContentProvider to act as a bridge between two apps to enable them
   to share data which is demonstrated in today's apps.
   The app will uses a classes named MyContentProvider to server as the interface.
   The class that facilitates storage of data is an innerclass of MyContentProvider which uses
   SQLiteDatabase instance to store data.
   MyContentProvider class extends the class ContentProvider and it uses its six overriding
   methods to access the database( or table) created by the innerclass.
   Please take time to understand MyContentProvider class and how this class was added to the
   package com.example.contentprovider.
   author ralex
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
