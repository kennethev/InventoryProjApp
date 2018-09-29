package com.example.android.inventoryprojapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.util.Log;


import com.example.android.inventoryprojapp.data.BookContract.BookEntry;
import com.example.android.inventoryprojapp.data.BookDbHelper;


/**
 * import java.sql.RowId;
 * <p>
 * <p>
 * /**
 * Displays list of books that were entered and stored in the app.
 */


public class MainActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new BookDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the books database.
     */


    private void displayDatabaseInfo() {

        // To access our database, we instantiate our subclass of SQLiteOpenHelper

        // and pass the context, which is the current activity.


        // Create and/or open a database to read from it

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_TITLE,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_VENDOR_NAME,
                BookEntry.COLUMN_VENDOR_PHONE};

        // Perform this raw SQL query "SELECT * FROM books"

        // to get a Cursor that contains all rows from the books table.

        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = findViewById(R.id.text_view_book);


        try {

            // Display the number of rows in the Cursor (which reflects the number of rows in the

            // books table in the database).


            displayView.setText("Number of rows in books database table: " + cursor.getCount() + " books.\n\n");

            displayView.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_BOOK_TITLE + " - " +
                    BookEntry.COLUMN_BOOK_PRICE + " - " +
                    BookEntry.COLUMN_BOOK_QUANTITY + " - " +
                    BookEntry.COLUMN_VENDOR_NAME + " - " +
                    BookEntry.COLUMN_VENDOR_PHONE + "\n");

            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_TITLE);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int vendorNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_VENDOR_NAME);
            int vendorPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_VENDOR_PHONE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentVendorName = cursor.getString(vendorNameColumnIndex);
                int currentVendorPhone = cursor.getInt(vendorPhoneColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentTitle + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentVendorName + " - " +
                        currentVendorPhone));

            }

        } finally {

            // Always close the cursor when you're done reading from it. This releases all its

            // resources and makes it invalid.

            cursor.close();

        }

    }

    private void insertBook() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //create a content values object, columns are keys and traits are values.


        ContentValues values = new ContentValues();

        values.put(BookEntry.COLUMN_BOOK_TITLE, "Goodnight,Planet");
        values.put(BookEntry.COLUMN_BOOK_PRICE, 18);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, BookEntry.QUANTITY_IN_STOCK);
        values.put(BookEntry.COLUMN_VENDOR_NAME, "Scholastic");
        values.put(BookEntry.COLUMN_VENDOR_PHONE, "800-555-5555");
        //INSERT a rowl for Goodnight, Planet in db
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        //if the ID is-1, the insert failed.Log error message
        Log.e(TAG, "insert failed");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






