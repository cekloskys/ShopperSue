package com.example.shoppersue;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * The ShoppingLists class will map the data selected from the shoppinglist
 * table, in the Cursor, to the li_shopping_list layout resource.
 */
public class ShoppingLists extends CursorAdapter {

    /**
     * Initialize a ShoppingLists CursorAdapter.
     * @param context reference to the Activity that initializes the
     *              ShoppingLists CursorAdapter
     * @param c reference to the Cursor that contains the data selected
     *          from the shoppinglist table
     * @param flags determines special behavior of the CursorAdapter. We
     *              don't want any special behavior so we will always
     *              pass 0.
     */
    public ShoppingLists(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Make a new View to hold the data in the Cursor.
     * @param context reference to the Activity that initializes the
     *                   ShoppingLists CursorAdapter
     * @param cursor reference to the Cursor that contains the data selected
     *               from the shoppinglist table
     * @param parent reference to shopperListView that will contain the new
     *               View created by this method
     * @return reference to the new View
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list, parent, false);
    }

    /**
     * Bind new View to data in Cursor.
     * @param view reference to new View
     * @param context reference to the Activity that initializes the
     *                        ShoppingLists CursorAdapter
     * @param cursor reference to the Cursor that contains the data selected
     *                    from the shoppinglist table
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.storeTextView)).
                setText(cursor.getString(cursor.getColumnIndex("store")));
        ((TextView) view.findViewById(R.id.dateTextView)).
                setText(cursor.getString(cursor.getColumnIndex("date")));
    }
}
