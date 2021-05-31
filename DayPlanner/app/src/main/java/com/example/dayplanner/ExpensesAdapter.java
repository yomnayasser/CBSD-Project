package com.example.dayplanner;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpensesAdapter extends ArrayAdapter<Expenses> {

    public ExpensesAdapter(Context context, ArrayList<Expenses> expenses) {
        super(context, 0, expenses);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Expenses e = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_style, parent, false);
        }

        // Lookup view for data population
        TextView Name = (TextView) convertView.findViewById(R.id.ExpenseName);
        TextView Category = (TextView) convertView.findViewById(R.id.ExpenseCategory);
        TextView Price = (TextView) convertView.findViewById(R.id.ExpensePrice);
        TextView Date= (TextView) convertView.findViewById(R.id.ExpenseDate);

        // Populate the data into the template view using the data object
        Name.setText(e.getName());
        Category.setText("Category: "+e.getCategory());
        Price.setText("Price: "+String.valueOf(e.getPrice()));
        Date.setText("Time: "+e.getTime());

        // Return the completed view to render on screen
        return convertView;
    }

}