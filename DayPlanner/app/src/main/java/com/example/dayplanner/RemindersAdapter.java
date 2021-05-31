package com.example.dayplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder>
{
    private myOnClickListener ocl;

    private List<Reminder> remindersList;
    private RemindersActivity activity;

    public RemindersAdapter(RemindersActivity activity, myOnClickListener ocl)
    {
        this.activity = activity;
        this.ocl = ocl;
    }

//    final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            int itemPosition = recyclerView.getChildLayoutPosition(v);
//            Reminder item = reminders.get(itemPosition);
//            Toast.makeText(getApplicationContext(), item.getrName(), Toast.LENGTH_LONG).show();
//        }
//    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_layout,parent,false);
//        itemView.setOnClickListener(mOnClickListener);
        return new ViewHolder(itemView, ocl);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder item= remindersList.get(position);

        holder.rname.setText(item.getrName());
        holder.rdate.setText(item.getrDate());
        holder.rtime.setText(item.getrTime());
    }

    @Override
    public int getItemCount() {
        return remindersList.size();
    }

    public void setReminder(List<Reminder> remindersList){
        this.remindersList=remindersList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView rname, rdate, rtime;
        myOnClickListener ocl;

        ViewHolder(View view, myOnClickListener ocl) {
            super(view);
            rname = view.findViewById(R.id.reminderName);
            rdate = view.findViewById(R.id.reminderDate);
            rtime = view.findViewById(R.id.reminderTime);
            this.ocl = ocl;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ocl.myOnClick(getAdapterPosition());
        }
    }

    public interface myOnClickListener{
        public void myOnClick(int position);
    }

}
