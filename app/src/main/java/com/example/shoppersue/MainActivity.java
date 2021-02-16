package com.example.shoppersue;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    // declare Intent
    Intent intent;

    // declare a DBHandler
    DBHandler dbHandler;

    // declare a ShoppingLists CursorAdapter
    CursorAdapter shoppingListsCursorAdapter;

    // declare a ListView
    ListView shopperListView;

    /**
     * This method initializes the Action Bar and View of the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // initialize the View and Action Bar of the MainActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize ListView
        shopperListView = (ListView) findViewById(R.id.shopperListView);

        // initialize ShoppingLists CursorAdapter
        shoppingListsCursorAdapter = new ShoppingLists(this,
                dbHandler.getShoppingLists(), 0);

        // set ShoppingLists CursorAdapter on the ListView
        shopperListView.setAdapter(shoppingListsCursorAdapter);

        // set OnItemClickListener on the ListView
        shopperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method gets called when a item in the ListView is clicked.
             * @param adapterView shopperListView
             * @param view MainActivity view
             * @param position position of the clicked item
             * @param id database id of the clicked item
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // initialize Intent for the ViewList Activity
                intent = new Intent(MainActivity.this, VeiwList.class);

                // put the database id in the Intent
                intent.putExtra("_id", id);

                // start the ViewList Activity
                startActivity(intent);

            }
        });

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add Floating Action Button is clicked.
     * It starts the CreateList Activity.
     * @param view MainActivity view
     */
    public void openCreateList(View view) {
        // initialize an Intent for the CreateList Activity and start it
        intent = new Intent(this, CreateList.class);
        startActivity(intent);
    }
}