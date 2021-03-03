package com.example.shoppersue;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ShoppingListItems extends CursorAdapter {
    /**
     * Initialize a ShoppingListItems CursorAdapter.
     * @param context reference to the Activity that initializes the
     *              ShoppingListItems CursorAdapter
     * @param c reference to the Cursor that contains the data selected
     *          from the shoppinglistitem table
     * @param flags determines special behavior of the CursorAdapter. We
     *              don't want any special behavior so we will always
     *              pass 0.
     */
    public ShoppingListItems(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Make a new View to hold the data in the Cursor.
     * @param context reference to the Activity that initializes the
     *                   ShoppingListItems CursorAdapter
     * @param cursor reference to the Cursor that contains the data selected
     *               from the shoppinglistitem table
     * @param parent reference to shopperListView that will contain the new
     *               View created by this method
     * @return reference to the new View
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_item_list, parent, false);
    }

    /**
     * Bind new View to data in Cursor.
     * @param view reference to new View
     * @param context reference to the Activity that initializes the
     *                        ShoppingListItems CursorAdapter
     * @param cursor reference to the Cursor that contains the data selected
     *                    from the shoppinglistitem table
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.priceTextView)).
                setText(cursor.getString(cursor.getColumnIndex("price")));
        ((TextView) view.findViewById(R.id.quantityTextView)).
                setText(cursor.getString(cursor.getColumnIndex("quantity")));
        ((TextView) view.findViewById(R.id.hasTextView)).
                setText("Item Purchased? " +
                        cursor.getString(cursor.getColumnIndex("item_has")));
    }
}
