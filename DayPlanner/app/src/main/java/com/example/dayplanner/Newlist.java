package com.example.dayplanner;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Newlist extends BottomSheetDialogFragment {

    public static final String TAG="ActionBottomDialog";
    private EditText newlist;
    private Button newtextSavebtn;
    private DayPlannerDatabase db;
    private static boolean isUpdate;
    private static String oname;
    private static String username;

//    public void setBool()
//    {
//        this.isUpdate = false;
//    }
    public static Newlist newInstance(String uname){
        isUpdate = false;
        username = uname;
        return new Newlist();
    }

    public static Newlist newInstance(boolean mybool, String oldname, String uname){
        isUpdate = mybool;
        oname = oldname;
        username = uname;
        return new Newlist();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_task,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newlist=getView().findViewById(R.id.newtask);
        newtextSavebtn=getView().findViewById(R.id.btnNewTask);
        db=new DayPlannerDatabase(getActivity());
//        db.openDB();
//        isUpdate=false;
        final Bundle bundle=getArguments();
        boolean finalIsUpdate = isUpdate;
        newtextSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text =newlist.getText().toString();
                if(finalIsUpdate){
                    db.updateLists(oname,text,username);
                }
                else {
//                    todo task=new todo();
//                    task.setTask(text);
//                    task.setStatus(0);
                    Log.e(TAG, username + " + " + newlist.getText().toString());
                    db.insertList(username, newlist.getText().toString());
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity=getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
        super.onDismiss(dialog);
    }
}
