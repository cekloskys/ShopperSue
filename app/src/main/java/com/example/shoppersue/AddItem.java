package com.example.shoppersue;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare a Bundle and a long used to get and store the data sent from
    // the ViewList
    Bundle bundle;
    long id;

    // declare a DBHandler
    DBHandler dbHandler;

    // declare Intent
    Intent intent;

    // declare EditTexts
    EditText nameEditText;
    EditText priceEditText;

    // declare Spinner
    Spinner quantitySpinner;

    // declare a String to store quantity selected in Spinner
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the Bundle
        bundle = this.getIntent().getExtras();

        // use Bundle to get id and store it in id field
        id = bundle.getLong("_id");

        // initialize the DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditTetxt);

        // initialize Spinner
        quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);

        // initialize ArrayAdapter with values in quantities string-array
        // and stylize it with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item);

        // further stylize the ArrayAdapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set the ArrayAdapter on the Spinner
        quantitySpinner.setAdapter(adapter);

        // register an OnItemSelectedListener to Spinner
        quantitySpinner.setOnItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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
            case R.id.action_view_list :
                // initialize an Intent for the ViewList Activity and start it
                intent = new Intent(this, VeiwList.class);
                // put the database id in the Intent
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     * @param menuItem add item menu item
     */
    public void addItem(MenuItem menuItem) {

        // get data input in EditTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();

        // trim Strings and see if they're equal to empty Strings
        if (name.trim().equals("") || price.trim().equals("") ||
                quantity.trim().equals("")) {
            // display toast saying "Please enter a name, price, and quantity!"
            Toast.makeText(this, "Please enter a name, price, and quantity!",
                    Toast.LENGTH_LONG).show();
        } else {
            // add item into database
            dbHandler.addItemToList(name, Double.parseDouble(price),
                    Integer.parseInt(quantity), (int) id);

            // display toast saying "Item added!"
            Toast.makeText(this, "Item added!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method gets called when an item in the Spinner is selected.
     * @param parent Spinner AdapterView
     * @param view AddItem view
     * @param position position of item that was selected in the Spinner
     * @param id database id of item that was selected in the Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        quantity = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}