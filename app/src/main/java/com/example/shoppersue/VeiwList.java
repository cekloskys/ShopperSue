package com.example.shoppersue;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class VeiwList extends AppCompatActivity {

    // declare a Bundle and a long used to get and store the data sent from
    // the MainActivity
    Bundle bundle;
    long id;

    // declare a DBHandler
    DBHandler dbHandler;

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
        String shoppingListName = dbHandler.getShoppingListName((int) id);

        // set the title of the ViewList Activity to the shopping list name
        this.setTitle(shoppingListName);
    }

    public void openAddItem(View view) {
    }
}