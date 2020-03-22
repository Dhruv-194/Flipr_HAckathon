package com.test.fliprhack_trello;

import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class CardModelClass implements Serializable {
    private String mID;
    private String Name;
    private String Desc;
    private String dod;
    private String key;

    public CardModelClass()
    {
        //empty class for firebase
    }
    public CardModelClass(String Name, String desc, String dod){
        if (Name.trim().equals("")){
            Name = "Card Name";
        }
        this.Name = Name;
        this.Desc=Desc;
        this.dod=dod;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

    @Override
    public String toString() {
        return getName();
    }
    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
