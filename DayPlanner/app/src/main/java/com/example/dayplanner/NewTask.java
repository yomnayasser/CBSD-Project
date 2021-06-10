package com.example.dayplanner;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewTask extends BottomSheetDialogFragment {
    public static final String TAG="ActionBottomDialog";
    private EditText newtext;
    private Button newtextSavebtn;
    private DayPlannerDatabase db;
    private static String listid;
    private static String username;
    private static int firstTime;


    public static NewTask newInstance(String lid, int ftime, String uname){
        listid = lid;
        firstTime = ftime;
        username = uname;
        return new NewTask();
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
        newtext=getView().findViewById(R.id.newtask);
        newtextSavebtn=getView().findViewById(R.id.btnNewTask);
        db=new DayPlannerDatabase(getActivity());

        boolean isUpdate=false;
        final Bundle bundle=getArguments();
        if (bundle !=null){
            isUpdate=true;
            String task =bundle.getString("task");
            newtext.setText(task);
//            if(task.length()>0)
//                newtextSavebtn.setTextColor(Color.parseColor("#3f72af"));

        }

        boolean finalIsUpdate = isUpdate;
        newtextSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text =newtext.getText().toString();
                if(finalIsUpdate){
//                    db.openDB();
                    db.updateTask(bundle.getInt("TodoItem_ID"),text);
                }
                else {
                    todo task=new todo();
                    task.setTask(text);
                    task.setStatus(0);

                    long dbstat = db.insertTask(task, listid);
                    if(dbstat != -1 && firstTime == 1)
                    {
                        db.insertTask(task, listid);
                        db.updatefTime(listid, username,0);

                        // SET firstTime = 0
                    }

//                    Log.e(TAG, String.valueOf(listid) + " + " + task.getTask().toString());
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












