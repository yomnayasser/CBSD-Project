package com.example.dayplanner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CalenderAdapter extends ArrayAdapter<CalenderEventsClass>
{
    public CalenderAdapter(Context context, ArrayList<CalenderEventsClass> calender) {
        super(context, 0, calender);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CalenderEventsClass c = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calender_list_item, parent, false);
        }


        TextView Type = (TextView) convertView.findViewById(R.id.EventType);
        TextView Time = (TextView) convertView.findViewById(R.id.CalenderEvent_Time);
        TextView Name = (TextView) convertView.findViewById(R.id.CalenderEvent_Name);
        TextView Price = (TextView) convertView.findViewById(R.id.CalenderEvent_Money);
        Price.setVisibility(View.INVISIBLE);



        Type.setText(c.getType());
        if(Type.getText().toString().equals("Expenses"))
        {
            Type.setTextColor(Color.BLUE);
            Price.setVisibility(View.VISIBLE);
        }
        else if(Type.getText().toString().equals("Event"))
        {
            Type.setTextColor(Color.GREEN);
        }
        Time.setText(c.getTime());
        Name.setText("Name: "+c.getName());
        Price.setText("Price: "+String.valueOf(c.getExpenssesPrice()));

        return convertView;
    }

    public interface myOnClickListener {
        public void myOnClick(int position);
    }
}
