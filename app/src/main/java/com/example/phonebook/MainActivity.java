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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//----------------------------------------------
    EditText lnameBox, phnNumBox, fnameBox;
//-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fnameBox = findViewById(R.id.firstName);
        lnameBox = findViewById(R.id.lastName);
        phnNumBox = findViewById(R.id.phoneNumber);
//        fnameBox.setEnabled(false);
    }
//------------------------------------------
    public void show(View view){
        fnameBox.setText("not-assigned");
        lnameBox.setText("");
        phnNumBox.setText("");
        lnameBox.requestFocus();
        Intent intent = new Intent(this,DisplayActivity.class);
        startActivity(intent);
    }
//-------------------------------------------------------
    public void newProduct(View view) {
        Product product = new Product(lnameBox.getText().toString(),
                Integer.parseInt(phnNumBox.getText().toString()));
        // class instance used to add values in the database
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.COLUMN_FNAME, product.getlastName());
        values.put(MyContentProvider.COLUMN_PHONENUM,product.getQuantity());
        // please note how we access our table- using ContentResolver instance
        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(MyContentProvider.CONTENT_URI,values );
        lnameBox.setText("");
        phnNumBox.setText("" );
    }
//--------------------------------------------------------
    public void lookupProduct(View view){
        String [] projection = {MyContentProvider.COLUMN_ID,
                MyContentProvider.COLUMN_FNAME, MyContentProvider.COLUMN_LNAME,
                MyContentProvider.COLUMN_PHONENUM};
        String selection = "NULL FIX HERE = \""+ lnameBox.getText().toString()+ "\"";
        // access our table through a ContentResolver instance
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MyContentProvider.CONTENT_URI,projection ,selection ,null,null );

        Product product = new Product();
        if(cursor.moveToFirst()){
            product.setId(cursor.getInt(0));
            product.setlastName(cursor.getString(1));
            product.setQuantity(cursor.getInt(2));
            cursor.close();
        }
        else
            product = null;
        if(product != null){
            fnameBox.setText(String.valueOf(product.getId()));
            phnNumBox.setText(String.valueOf(product.getQuantity()));
        }
        else
            fnameBox.setText("No match found");
    }
//------------------------------------------------------------------------
    public void removeProduct (View view){
        String selection = "fix this later = \"" + lnameBox.getText().toString() + "\"";
        ContentResolver contentResolver = getContentResolver();
        int result = contentResolver.delete(MyContentProvider.CONTENT_URI, selection,null );
        if(result > 0){
            fnameBox.setText("" + result + " record(s) deleted");
            lnameBox.setText("" );
            phnNumBox.setText("" );
        }
        else fnameBox.setText("No match found");
    }
