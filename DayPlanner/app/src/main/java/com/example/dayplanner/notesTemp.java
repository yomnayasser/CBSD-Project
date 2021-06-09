package com.example.dayplanner;

import java.util.ArrayList;
import java.util.List;

public class notesTemp {

    private String[] note;

    public notesTemp(){
        note = new String[]{"this is my diary!", "don't read it, it's a secret",
                "ok, for the sake of the project", "how are you?"};
    }

    public String[] getNote() {
        return note;
    }

    public void add(String newNote){
        String[] temp = new String[note.length + 1];
        System.arraycopy(note, 0, temp, 0, note.length);
        temp[note.length] = newNote;
        note = temp;
    }

}
