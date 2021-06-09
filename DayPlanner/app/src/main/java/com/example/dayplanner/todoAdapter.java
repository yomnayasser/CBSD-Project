package com.example.dayplanner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.ViewHolder> {
    private List<todo> todoList;
    private ToDoActivity activity;
    private DayPlannerDatabase db;

    public todoAdapter(DayPlannerDatabase db, ToDoActivity activity) {
        this.activity=activity;
        this.db=db;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_task,parent,false);

        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
//        db.openDB();
        todo item= todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(tobool(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(),1);
                }
                else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });
    }
    public int getItemCount(){
        try{
            return todoList.size();
        } catch (Exception ex)
        {
            return 0;
        }
    }

    private boolean tobool(int n){
        return n!=0;
    }
    public void setTasks(List<todo> todoList){
        this.todoList=todoList;
        notifyDataSetChanged();
    }
    public Context getContext(){
        return activity;
    }
    public void del_Item(int position){
        todo item=todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        todo item=todoList.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("TodoItem_ID",item.getId());
        bundle.putString("task",item.getTask());
        NewTask frag=new NewTask();
        frag.setArguments(bundle);
        frag.show(activity.getSupportFragmentManager(), NewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task=view.findViewById(R.id.todo_checkBox);
        }
    }
}
