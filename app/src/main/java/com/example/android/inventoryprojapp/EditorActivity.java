package com.example.android.inventoryprojapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.android.inventoryprojapp.data.BookContract.BookEntry;
import com.example.android.inventoryprojapp.data.BookDbHelper;

/**
 * Allows user to create a new book or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the book's name
     */
    private EditText mTitleEditText;

    /**
     * EditText field to enter the book's price
     */
    private EditText mPriceEditText;


    /**
     * EditText field to enter the quantity of books available or not
     */
    private Spinner mQuantitySpinner;

    /**
     * EditText field to enter the vendor's name
     */
    private EditText mVendorNameEditText;

    /*
     * Quantity of book. The possible values are:
     * 0 for out of stock. 1 for in stock book.
     */
    private int mQuantity = BookEntry.QUANTITY_IN_STOCK;

    /* TO ENTER VENDOR'S PHONE NUMBER

     */
    private EditText mVendorPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mTitleEditText = (EditText) findViewById(R.id.edit_book_title);
        mPriceEditText = (EditText) findViewById(R.id.edit_book_price);
        mVendorNameEditText = (EditText) findViewById(R.id.edit_vendor_name);
        mVendorPhoneEditText = (EditText) findViewById(R.id.edit_vendor_phone);
        mQuantitySpinner = (Spinner) findViewById(R.id.spinner_quantity);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter quantitySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_quantity_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        quantitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mQuantitySpinner.setAdapter(quantitySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("In stock")) {
                        mQuantity = BookEntry.QUANTITY_IN_STOCK; // in stock book
                    } else if (selection.equals("Out of stock")) {
                        mQuantity = BookEntry.QUANTITY_OUT_OF_STOCK; // out of stock book

                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mQuantity = BookEntry.QUANTITY_OUT_OF_STOCK; // Unknown
            }
        });
    }

    private void insertBook() {

        String titleString = mTitleEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String vendorNameString = mVendorNameEditText.getText().toString().trim();
        String vendorPhoneString = mVendorPhoneEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);


        BookDbHelper mDbHelper = new BookDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_TITLE, titleString);
        values.put(BookEntry.COLUMN_BOOK_PRICE, priceString);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, mQuantity);
        values.put(BookEntry.COLUMN_VENDOR_NAME, vendorNameString);
        values.put(BookEntry.COLUMN_VENDOR_PHONE, vendorPhoneString);

        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving Book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book saved with row id;" + newRowId, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save book to db
                insertBook();
                //exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}







