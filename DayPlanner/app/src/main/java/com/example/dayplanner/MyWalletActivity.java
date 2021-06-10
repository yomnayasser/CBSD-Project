package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyWalletActivity extends AppCompatActivity {

    public static Activity a;
    String username;
    String budgetStr;
    String date;
    TextView BudgetAmount;
    String repeat;
    float Currentbudget;
    float Totalbudget;
    int Walletid;
    boolean new_wallet;
    ArrayList<Expenses> UserExpenses=new ArrayList<Expenses>();
    ExpensesAdapter adapter;
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        a=this;
        username=getIntent().getExtras().getString("username");
        Walletid=getIntent().getExtras().getInt("id");
        Currentbudget=getIntent().getExtras().getFloat("current_budget");
        Totalbudget=getIntent().getExtras().getFloat("total_budget");
        repeat=getIntent().getExtras().getString("repeat");
        date=getIntent().getExtras().getString("date");
        new_wallet=getIntent().getExtras().getBoolean("new_wallet");

        ImageButton addBudget=(ImageButton)findViewById(R.id.AddBtn);
        ImageButton editBudget=(ImageButton)findViewById(R.id.EditBtn);
        FloatingActionButton addExpense=(FloatingActionButton)findViewById(R.id.AddExpenseBtn);
        BudgetAmount=(TextView)findViewById(R.id.textView5);
        budgetStr=Float.toString(Currentbudget);
        BudgetAmount.setText(budgetStr);
        ListView listView = (ListView) findViewById(R.id.ExpensesList);
        registerForContextMenu(listView);

        Expenses e=new Expenses();
        e.getExpenses(Walletid,MyWalletActivity.this,UserExpenses);
        TextView empty=(TextView)findViewById(R.id.messageTxt);
        adapter = new ExpensesAdapter(MyWalletActivity.this, UserExpenses);

        if(UserExpenses.isEmpty())
        {
            empty.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.INVISIBLE);
            listView.setAdapter(adapter);
        }
        editBudget.setOnClickListener(v -> {
            Intent i = new Intent(MyWalletActivity.this, AddBudgetActivity.class);
            i.putExtra("username",username);
            i.putExtra("new_wallet",new_wallet);
            i.putExtra("id",Walletid);
            i.putExtra("total_budget",Totalbudget);
            i.putExtra("current_budget",Currentbudget);
            i.putExtra("repeat",repeat);
            i.putExtra("date",date);
            startActivity(i);
        });

        addExpense.setOnClickListener(v -> {
            Intent i = new Intent(MyWalletActivity.this, AddExpenseActivity.class);
            i.putExtra("username",username);
            i.putExtra("new_wallet",new_wallet);
            i.putExtra("id",Walletid);
            i.putExtra("total_budget",Totalbudget);
            i.putExtra("current_budget",Currentbudget);
            i.putExtra("repeat",repeat);
            i.putExtra("date",date);
            i.putExtra("Editmode",false);
            startActivity(i);
        });
        View v=LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dialog, null, false);

        final PopupWindow pw = new PopupWindow(v,1000,1000, true);
        //pw.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        final ImageButton button = (ImageButton)findViewById(R.id.AddBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f; //0.0-1.0
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                        WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            }
        });
        final Button popup_btn=v.findViewById(R.id.addToBudget);
        EditText amount=v.findViewById(R.id.editTextNumberDecimal2);
        popup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! (amount.getText().toString().matches("")))
                {
                    Wallet w=new Wallet();
                    float new_budget=Currentbudget+Float.parseFloat(amount.getText().toString());
                    w.EditWallet(username,new_budget,Totalbudget,repeat,date,MyWalletActivity.this);
                    BudgetAmount.setText(String.valueOf(new_budget));
                    Currentbudget=new_budget;
                }
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
                pw.dismiss();
                amount.setText("");
            }
        });
//        addBudget.setOnClickListener(v->{
//            EditText amount=(EditText)findViewById(R.id.editTextNumberDecimal2);
//            Button add=(Button)findViewById(R.id.addToBudget);
//
////            add.setOnClickListener(view->{
////            Wallet w=new Wallet();
////            float new_budget=Currentbudget+Float.parseFloat(amount.toString());
////            w.EditWallet(username,new_budget,Totalbudget,repeat,date,MyWalletActivity.this);
////            });
//        });
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Expenses e=new Expenses();

        int index=(int)adapter.getItemId(info.position);
        int Exp_id=UserExpenses.get(index).getExpense_ID();
        float Exp_price=UserExpenses.get(index).getPrice();

        if(id==R.id.item_delete)
        {
            e.DeleteExpense(Exp_id,MyWalletActivity.this);
            UserExpenses.remove(index);
            adapter.notifyDataSetChanged();
            return true;
        }
        if(id==R.id.item_edit)
        {
            Intent i= new Intent(MyWalletActivity.this,AddExpenseActivity.class);
            i.putExtra("username",username);
            i.putExtra("new_wallet",new_wallet);
            i.putExtra("id",Walletid);
            i.putExtra("total_budget",Totalbudget);
            i.putExtra("current_budget",Currentbudget);
            i.putExtra("repeat",repeat);
            i.putExtra("date",date);
            i.putExtra("ExpensesID",UserExpenses.get(index).getExpense_ID());
            i.putExtra("Expensename",UserExpenses.get(index).getName());
            i.putExtra("ExpenseCategory",UserExpenses.get(index).getCategory());
            i.putExtra("ExpensePrice",UserExpenses.get(index).getPrice());
            i.putExtra("ExpenseDate",UserExpenses.get(index).getTime());
            i.putExtra("Editmode",true);
            startActivity(i);
        }
        if(id==R.id.item_delete_return)
        {
            Wallet w =new Wallet();
            w.getWallet(username,MyWalletActivity.this);
            w.EditCurrentBudget(w.getId(),w.getCurrentBudget(),Exp_price,true,MyWalletActivity.this);
            w.getWallet(username,MyWalletActivity.this);
            e.DeleteExpense(Exp_id,MyWalletActivity.this);
            UserExpenses.remove(index);
            adapter.notifyDataSetChanged();
            Currentbudget=w.getCurrentBudget();
            budgetStr=Float.toString(Currentbudget);
            BudgetAmount.setText(budgetStr);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}