package com.example.dayplanner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHelper extends ItemTouchHelper.SimpleCallback {
    private todoAdapter adapter;

    public RecyclerHelper(todoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position =viewHolder.getAdapterPosition();
        if (direction==ItemTouchHelper.LEFT){
            adapter.del_Item(position);
        }
        else {
            adapter.editItem(position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable back;
        View itemView=viewHolder.itemView;
        int backoffset=20;
        if(dX>0){
            icon= ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit);
            back=new ColorDrawable(Color.parseColor("#3f72af"));
        }else{
            icon= ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete_forever);
            back=new ColorDrawable(Color.RED);
        }
        int iconMargin=(itemView.getHeight()-icon.getIntrinsicHeight())/2;
        int icontop=itemView.getTop()+(itemView.getHeight()-icon.getIntrinsicHeight())/2;
        int iconBottom=icontop+icon.getIntrinsicHeight();

        if (dX>0){
            int iconleft=itemView.getLeft()+iconMargin;
            int iconRight=itemView.getLeft()+iconMargin+icon.getIntrinsicWidth();

            icon.setBounds(iconleft,icontop,iconRight,iconBottom);
            back.setBounds(itemView.getLeft(),itemView.getTop(),itemView.getLeft()+((int)dX)+backoffset,itemView.getBottom());
        }
        else if(dX<0){
            int iconleft=itemView.getRight()-iconMargin-icon.getIntrinsicWidth();
            int iconRight=itemView.getRight()-iconMargin;

            icon.setBounds(iconleft,icontop,iconRight,iconBottom);
            back.setBounds(itemView.getRight()+((int)dX)-backoffset,itemView.getTop(),itemView.getRight(),itemView.getBottom());
        }else {
            back.setBounds(0,0,0,0);
        }
        back.draw(c);
        icon.draw(c);
    }
}

