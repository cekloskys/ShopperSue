package com.example.shoppersue;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ViewItem extends AppCompatActivity {

    // declare a DBHandler
    DBHandler dbHandler;

    // declare an Intent
    Intent intent;

    // declare EditTexts
    EditText nameEditText;
    EditText priceEditText;
    EditText quantityEditText;

    // declare a Bundle and a long used to get and store the data sent from
    // the ViewList
    Bundle bundle;
    long id;
    long listId;

    // declare Strings to store the clicked shopping list item's name, price, and quantity
    String name;
    String price;
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize Bundle
        bundle = this.getIntent().getExtras();

        // get the shopping list item id in the bundle and store it in long
        id = bundle.getLong("_id");

        // get the shopping list id in the bundle and store it in long
        listId = bundle.getLong("_list_id");

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);

        // disable EditTexts
        nameEditText.setEnabled(false);
        priceEditText.setEnabled(false);
        quantityEditText.setEnabled(false);

        // call the DBHandler method getShoppingListItem
        Cursor cursor = dbHandler.getShoppingListItem((int) id);

        // move to the first row in the Cursor
        cursor.moveToFirst();

        // get the name, price, and quantity in the Cursor and store them in Strings
        name = cursor.getString(cursor.getColumnIndex("name"));
        price = cursor.getString(cursor.getColumnIndex("price"));
        quantity = cursor.getString(cursor.getColumnIndex("quantity"));

        // set the name, price, and quantity values in the EditTexts
        nameEditText.setText(name);
        priceEditText.setText(price);
        quantityEditText.setText(quantity);
    }

    /**
     * This method further initializes the Action Bar of the activity.
     * It gets the code (XML) in the menu resource file and incorporates it
     * into the Action Bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_item, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected.
     * @param item selected menu item in the overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of menu item selected
        switch (item.getItemId()) {
            case R.id.action_home :
                // initialize an Intent for the MainActivity and start it
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list :
                // initialize an Intent for the CreateList Activity and start it
                intent = new Intent(this, CreateList.class);
                startActivity(intent);
                return true;
            case R.id.action_add_item :
                // initialize an Intent for the AddItem Activity and start it
                intent = new Intent(this, AddItem.class);
                // put the database id in the Intent
                intent.putExtra("_id", listId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteItem(MenuItem menuItem) {

    }
}