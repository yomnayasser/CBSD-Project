package com.example.dayplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ToDoLists extends AppCompatActivity implements DialogCloseListener {

    ListView todoList;
    String username;
//    private RecyclerView tasksRec;
//    private todoAdapter tasksAdapter;
    private FloatingActionButton fAct;
//    private List<todo> taskList;
    private List<lists_todo> MyLists;
    private DayPlannerDatabase db;
    ArrayAdapter<String> Adapter;
//    ArrayList<Boolean> ftimes;

    @Override
    protected void onResume() {
        Adapter.clear();
        MyLists=db.fetchAllLists(username);
        for(int i=0; i<MyLists.size(); i++)
        {
            Adapter.add(MyLists.get(i).name);
//            ftimes.add(false)
        }
        Adapter.notifyDataSetChanged();

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_lists);

//        ftimes = new ArrayList<Boolean>();

        fAct = (FloatingActionButton)findViewById(R.id.newListBtn);

        username = getIntent().getExtras().getString("username");
//username="username";
        getSupportActionBar().hide();

        db=new DayPlannerDatabase(this);
//        db.openDB();

        todoList=(ListView)findViewById(R.id.listview);
        Adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        todoList.setAdapter(Adapter);
        registerForContextMenu(todoList);
        db=new DayPlannerDatabase(getApplicationContext());
        MyLists=db.fetchAllLists(username);
        for(int i=0; i<MyLists.size(); i++)
        {
            Adapter.add(MyLists.get(i).name);
//            ftimes.add(false)
        }
        Adapter.notifyDataSetChanged();

        fAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getMenuInflater().inflate(R.menu.floatingmenu,menu);
                Log.e(TAG, username);
                Newlist.newInstance(username).show(getSupportFragmentManager(), Newlist.TAG);
            }
        });

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ToDoLists.this, ToDoActivity.class);
                String name=((TextView) view).getText().toString();
                i.putExtra("listName", name);

                for(int f=0; f<MyLists.size(); f++)
                {
                    if(MyLists.get(f).getName() == name)
                    {
                        i.putExtra("listid", String.valueOf(MyLists.get(f).getId()));
                        i.putExtra("ftime", MyLists.get(f).getFtime());
                        break;
                    }
                }

//                i.putExtra("listID", MyLists.ge)
                i.putExtra("username", username);
                startActivity(i);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.floatingmenu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String selected=((TextView)info.targetView).getText().toString();
        int id=item.getItemId();
        if(id== R.id.item_delete)
        {
            Adapter.remove(selected);
            db.deleteList(selected, username);

            return true;
        }
        if (id== R.id.item_update)
        {
            Log.e(TAG, username);
            Newlist.newInstance(true,selected, username).show(getSupportFragmentManager(), Newlist.TAG);
            return true;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void handleDialogClose(DialogInterface dialog) {
        Adapter.clear();
        MyLists=db.fetchAllLists(username);
        Collections.reverse(MyLists);
        for(int i=0; i<MyLists.size(); i++)
            Adapter.add(MyLists.get(i).name);
        Adapter.notifyDataSetChanged();
    }

}