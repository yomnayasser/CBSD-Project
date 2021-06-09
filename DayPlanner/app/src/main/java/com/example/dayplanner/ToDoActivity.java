package com.example.dayplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ToDoActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView tasksRec;
    private todoAdapter tasksAdapter;
    private FloatingActionButton fAct;
    private List<todo> taskList;
    private DayPlannerDatabase db;
    private String username, listname, listid;
    private int ftime;
//    private int oldsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
//        oldsize = 0;

        listname = getIntent().getExtras().getString("listName");
        listid = getIntent().getExtras().getString("listid");
        ftime = getIntent().getExtras().getInt("ftime");
        username = getIntent().getExtras().getString("username");
//        username="username";
        getSupportActionBar().hide();

        db=new DayPlannerDatabase(this);
//        db.openDB();
        taskList=new ArrayList<>();

        tasksRec=findViewById(R.id.recyclertasks);
        tasksRec.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter=new todoAdapter(db,this);
        tasksRec.setAdapter(tasksAdapter);

        fAct=findViewById(R.id.floatingActionButton4);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRec);
        try {
            taskList=db.getTasks(username, listname);
            Collections.reverse(taskList);
            tasksAdapter.setTasks(taskList);
        }
        catch (Exception ex)
        {

        }
        tasksRec.setAdapter(tasksAdapter);

        registerForContextMenu(tasksRec);
        fAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTask.newInstance(listid, ftime, username).show(getSupportFragmentManager(),NewTask.TAG);
            }
        });
    }
    @Override
    public void handleDialogClose(DialogInterface dialog) {
        ftime = 0;
        taskList=db.getTasks(username, listname);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
        registerForContextMenu(tasksRec);

    }
}