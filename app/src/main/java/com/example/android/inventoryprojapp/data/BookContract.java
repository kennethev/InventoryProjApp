package com.example.android.inventoryprojapp.data;

import android.provider.BaseColumns;

public class BookContract {


    // To prevent someone from accidentally instantiating the contract class,

    // give it an empty constructor.

    private BookContract() {
    }

    /**
     * Inner class that defines constant values for the books database table.
     * <p>
     * Each entry in the table represents a single book.
     */

    public static final class BookEntry implements BaseColumns {


        /**
         * Name of database table for books
         */

        public final static String TABLE_NAME = "books";

        /**
         * Unique ID number for the book (only for use in the database table).
         * <p>
         * <p>
         * <p>
         * Type: INTEGER
         */

        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the book.
         * <p>
         * <p>
         * <p>
         * Type: TEXT
         */

        public final static String COLUMN_BOOK_TITLE = "title";

        /**
         * Price of book.
         * <p>
         * <p>
         * <p>
         * Type: INTEGER
         */

        public final static String COLUMN_BOOK_PRICE = "price";

        /**
         * Quantity of specific book in store.
         * <p>
         * <p>
         * <p>
         * The only possible values are {@link #QUANTITY_IN_STOCK, {@link #QUANTITY_OUT_OF_STOCK;
         * <p>
         * Type: INTEGER
         */

        public final static String COLUMN_BOOK_QUANTITY = "quantity";

        /**
         * Possible values for quantity of books.
         */

        public static final int QUANTITY_IN_STOCK = 1;

        public static final int QUANTITY_OUT_OF_STOCK = 0;


        /**
         * Vendor Information
         * <p>
         * Type: TEXT
         */

        public final static String COLUMN_VENDOR_NAME = "name";

        /**
         * Vendor Phone Information
         * Type: INTEGER
         */

        public static final String COLUMN_VENDOR_PHONE = "phone";

    }

}











