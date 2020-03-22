package com.test.fliprhack_trello;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.test.fliprhack_trello.CardModelClass;
import com.test.fliprhack_trello.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Utlis {
public  static  final String DATE_FORMAT = "yyyy-MM-dd";
public static List<CardModelClass> DataCache = new ArrayList<>();

public static  String  searchString = "";


public static void show(Context c, String  message)
{
    Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
}
public static  boolean validate (EditText... editTexts){
    EditText NameTxt = editTexts[0];
    EditText DescTxt = editTexts[1];

    if (NameTxt.getText()==null || NameTxt.getText().toString().isEmpty()){
        NameTxt.setError("card name is mandatory");
        return false;
    }
    if (DescTxt.getText()==null || DescTxt.getText().toString().isEmpty()){
        DescTxt.setError("description is mandatory");
        return false;
    }
    return true;

}
public static void openActivity(Context c, Class clazz){
    Intent intent= new Intent(c, clazz);
    c.startActivity(intent);
}

public static void  showInfoDialog(final AppCompatActivity activity, String title, String message)
{
    new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
            //.setButtonsColor(R.color.indigo)
          //  .setButtonsColorRes(R.color.darkDeepOrange)
            //.setIcon(R.drawable.m_info)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Relax", v -> {})
            .setNeutralButton("Go Home", v -> openActivity(activity, DashboardActivity.class))
            .setNegativeButton("Go Back", v -> activity.finish())
            .show();
}

    public static Date giveMeDate(String stringDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    public static void sendCardModelClassToActivity(Context c, CardModelClass cardModelClass,
                                               Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("CARDM_KEY",cardModelClass);
        c.startActivity(i);
    }
    public  static CardModelClass receiveCardModelClass(Intent intent, Context c){
        try {
            return (CardModelClass) intent.getSerializableExtra("CARDM_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-CARDM ERROR: "+e.getMessage());
        }
        return null;
    }
    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }
    public static void hideProgressBar(ProgressBar pb){
        pb.setVisibility(View.GONE);
    }
    public static DatabaseReference getDatabaseRefence() {
        return FirebaseDatabase.getInstance().getReference();
    }

}
