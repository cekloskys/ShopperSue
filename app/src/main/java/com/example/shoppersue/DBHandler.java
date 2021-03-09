package com.example.shoppersue;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for the DB name and version
    public static final String DATABASE_NAME = "shopper.db";
    public static final int DATABASE_VERSION = 2;

    // initialize constants for the shoppinglist table
    public static final String TABLE_SHOPPING_LIST = "shoppinglist";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_NAME = "name";
    public static final String COLUMN_LIST_STORE = "store";
    public static final String COLUMN_LIST_DATE = "date";

    // initialize constants for the shoppinglistitem table
    public static final String TABLE_SHOPPING_LIST_ITEM = "shoppinglistitem";
    public static final String COLUMN_ITEM_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_PRICE = "price";
    public static final String COLUMN_ITEM_QUANTITY = "quantity";
    public static final String COLUMN_ITEM_HAS = "item_has";
    public static final String COLUMN_ITEM_LIST_ID = "list_id";

    /**
     * Creates a version of the Shopper database.
     * @param context reference to the activity that initializes a DBHandler
     * @param factory null
     */
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates Shopper database tables.
     * @param sqLiteDatabase reference to Shopper database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // define create statement for shoppinglist table and store it
        // in a String
        String query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT);";

        // execute the statement
        sqLiteDatabase.execSQL(query);

        // define create statement for shoppinglistitem table and store it
        // in a String
        String query2 = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER);";

        // execute the statement
        sqLiteDatabase.execSQL(query2);
    }

    /**
     * Creates a new version of the Shopper database.
     * @param sqLiteDatabase reference to Shopper database
     * @param oldVersion old version of the Shopper database
     * @param newVersion new version of the Shopper database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // define drop statement and store it in a String
        String query = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST;

        // execute the drop statement
        sqLiteDatabase.execSQL(query);

        // define drop statement and store it in a String
        String query2 = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM;

        // execute the drop statement
        sqLiteDatabase.execSQL(query2);

        // call method that creates the tables
        onCreate(sqLiteDatabase);
    }

    /**
     * This method gets called when the add button in the Action Bar of the
     * CreateList Activity gets clicked.  It inserts a new row into the shopping
     * list table.
     * @param name shopping list name
     * @param store shopping list store
     * @param date shopping list date
     */
    public void addShoppingList(String name, String store, String date) {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data into ContentValues object
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_STORE, store);
        values.put(COLUMN_LIST_DATE, date);

        // insert data in ContentValues object into shoppinglist table
        db.insert(TABLE_SHOPPING_LIST, null, values);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when the MainActivity is created.  It will
     * select and return all of the data in the shoppinglist table.
     * @return Cursor that contains all data in the shoppinglist table
     */
    public Cursor getShoppingLists() {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement and store it in a String
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST;

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null);
    }

    /**
     * This method gets called when the ViewList Activity is started.
     * @param id shopping list id
     * @return shopping list name
     */
    public String getShoppingListName(int id) {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // declare and initialize the String that will be returned
        String name = "";

        // define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        // execute select statement and store it in a Cursor
        Cursor cursor = db.rawQuery(query, null);

        // move to the first row in the Cursor
        cursor.moveToFirst();

        // check that name component of Cursor isn't equal to null
        if ((cursor.getString(cursor.getColumnIndex("name")) != null)){
            // get the name component of the Cursor and store it in String
            name = cursor.getString(cursor.getColumnIndex("name"));
        }

        // close reference to shopper database
        db.close();

        // return shopping list name
        return name;
    }

    /**
     * This method gets called when the add button in the Action Bar of the AddItem
     * Activity gets clicked.  It inserts a new row in the shoppinglistitem table.
     * @param name item name
     * @param price item price
     * @param quantity item quantity
     * @param listId id of the shopping list to which the item is being added
     */
    public void addItemToList(String name, Double price, Integer quantity,
                              Integer listId) {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data into ContentValues object
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_ITEM_HAS, "false");
        values.put(COLUMN_ITEM_LIST_ID, listId);

        // insert data in ContentValues object into shoppinglistitem table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when the ViewList Activity is launched.
     * @param listId shopping list id
     * @return Cursor that contains all of the items associated with
     * the specified shopping list id
     */
    public Cursor getShoppingListItems(Integer listId) {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement and store it in a String
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId;

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null);
    }

    /**
     * This method gets called when an item in the ViewList activity is clicked.
     * @param itemId database id of the clicked item
     * @return 1 if the clicked item is unpurchased, else 0
     */
    public int isItemUnpurchased(Integer itemId) {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement and store it in a String
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_ID + " = " + itemId;

        // execute select statement and store result in a Cursor
        Cursor cursor = db.rawQuery(query, null);

        // return a count of the number of rows in the Cursor
        return (cursor.getCount());
    }

    /**
     * This method gets called when an item in the ViewList activity is clicked.
     * It sets the clicked item's item_has value to true.
     * @param itemId database id of the clicked item
     */
    public void updateItem(Integer itemId) {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define update statement and store it in a String
        String query = "UPDATE " + TABLE_SHOPPING_LIST_ITEM + " SET " +
                COLUMN_ITEM_HAS + " = \"true\" " + " WHERE " +
                COLUMN_ITEM_ID + " = " + itemId;

        // execute the update statement
        db.execSQL(query);

        // close db connection
        db.close();
    }

    /**
     * This method is going to get called when the ViewItem activity is started.
     * @param itemId database id of clicked shopping list item
     * @return Curso that contains all of the data associated with the clicked
     * shopping list item
     */
    public Cursor getShoppingListItem (Integer itemId) {

        // get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement and store it in a String
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId;

        // execute select statement and return it as a Cursor
        return db.rawQuery(query, null);
    }
}
