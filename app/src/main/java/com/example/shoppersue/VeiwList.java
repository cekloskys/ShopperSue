package com.example.shoppersue;

import android.app.Notification;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static com.example.shoppersue.App.CHANNEL_SHOPPER_ID;

public class VeiwList extends AppCompatActivity {

    // declare a Bundle and a long used to get and store the data sent from
    // the MainActivity
    Bundle bundle;
    long id;

    // declare a DBHandler
    DBHandler dbHandler;

    // declare Intent
    Intent intent;

    // declare a ShoppingListItems Cursor Adapter
    ShoppingListItems shoppingListItemsAdapter;

    // declare a ListView
    ListView itemListView;

    // declare Notification Manager used to show (display) the notification
    NotificationManagerCompat notificationManagerCompat;

    // declare String that will store the shopping list name
    String shoppingListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the Bundle
        bundle = this.getIntent().getExtras();

        // use Bundle to get id and store it in id field
        id = bundle.getLong("_id");

        // initialize the DBHandler
        dbHandler = new DBHandler(this, null);

        // call getShoppingListName method and store its return in String
        shoppingListName = dbHandler.getShoppingListName((int) id);

        // set the title of the ViewList Activity to the shopping list name
        this.setTitle(shoppingListName);

        // initialize the ListView
        itemListView = (ListView) findViewById(R.id.itemsListView);

        // initialize the ShoppingListItems Cursor Adapter
        shoppingListItemsAdapter = new ShoppingListItems(this,
                dbHandler.getShoppingListItems((int) id), 0);

        // set the ShoppingListItems Cursor Adapter on the ListView
        itemListView.setAdapter(shoppingListItemsAdapter);

        // register an OnItemClickListener on the ListView
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method gets called when an item in the ListView is clicked.
             * @param parent itemListView
             * @param view ViewList activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // call method that updates the clicked items item_has to true
                // if it's false
                updateItem(id);

                // initialize Intent for ViewItem Activity
                intent = new Intent(VeiwList.this, ViewItem.class);

                // put the database id of the clicked item in the intent
                intent.putExtra("_id", id);

                // put the database id of the clicked shopping list in the intent
                intent.putExtra("_list_id", VeiwList.this.id);

                // start the Activity
                startActivity(intent);
            }
        });

        // set the sub-title to the total cost of the shopping list
        toolbar.setSubtitle("Total Cost: $" + dbHandler.getShoppingListTotalCost((int) id));

        // initialize the Notification Manager
        notificationManagerCompat = NotificationManagerCompat.from(this);
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
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
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
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add Floating Action Button is clicked.
     * It starts the AddItem Activity.
     * @param view ViewList view
     */
    public void openAddItem(View view) {
        // initialize an Intent for the AddItem Activity and start it
        intent = new Intent(this, AddItem.class);
        // put the database id in the Intent
        intent.putExtra("_id", id);
        startActivity(intent);
    }

    /**
     * This method gets called when an item is clicked in the ListView.
     * It updates the clicked item's item_has to true if it's false.
     * @param id database id of the clicked item
     */
    public void updateItem(long id) {

        // checking if the clicked item is unpurchased
        if (dbHandler.isItemUnpurchased((int) id) == 1){
            // make clicked item purchased
            dbHandler.updateItem((int) id);

            // refresh ListView with updated data
            shoppingListItemsAdapter.swapCursor(dbHandler.getShoppingListItems((int) this.id));
            shoppingListItemsAdapter.notifyDataSetChanged();

            // display Toast indicating item is purchased
            Toast.makeText(this, "Item purchased!", Toast.LENGTH_LONG).show();
        }

        // if all shopping list items have been purchased
        if (dbHandler.getUnpurchasedItems((int) this.id) == 0){
            // initialize Notification
            Notification notification = new NotificationCompat.Builder(this,
                    CHANNEL_SHOPPER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Shopper")
                    .setContentText(shoppingListName + " completed!").build();

            // show Notification
            notificationManagerCompat.notify(1, notification);
        }
    }

    /**
     * This method gets called when the delete button in the Action Bar of the
     * View List activity gets clicked.  It deletes a row in the shoppinglistitem
     * and shoppinglist tables.
     * @param menuItem delete list menu item
     */
    public void deleteList(MenuItem menuItem) {

        // delete shopping list from database
        dbHandler.deleteShoppingList((int) id);

        // display "List deleted!" toast
        Toast.makeText(this, "List deleted!", Toast.LENGTH_LONG).show();
    }
}