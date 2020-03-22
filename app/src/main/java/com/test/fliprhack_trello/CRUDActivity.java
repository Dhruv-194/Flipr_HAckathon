package com.test.fliprhack_trello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.helper.DateTimePickerEditText;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;


import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CRUDActivity extends AppCompatActivity {


        //we'll have several instance fields
        private EditText nameTxt, descriptionTxt;
        private TextView headerTxt;
        private DateTimePickerEditText dobTxt,dodTxt;
        private ProgressBar mProgressBar;

        private final Context c = CRUDActivity.this;
        private CardModelClass receivedScientist;
        private DatabaseACT_CRUD crudHelper=new DatabaseACT_CRUD();
        private DatabaseReference db= Utlis.getDatabaseRefence();

        private void initializeWidgets() {
            mProgressBar = findViewById(R.id.mProgressBarSave);

            headerTxt = findViewById(R.id.headerTxt);
            nameTxt = findViewById(R.id.nameTxt);
            descriptionTxt = findViewById(R.id.descriptionTxt);



            dodTxt = findViewById(R.id.dodTxt);
            dodTxt.setFormat(Utlis.DATE_FORMAT);
        }

        private void insertData() {
            String name, description, galaxy, star, dob,dod;
            if (Utlis.validate(nameTxt, descriptionTxt)) {
                name = nameTxt.getText().toString();
                description = descriptionTxt.getText().toString();


                if (dodTxt.getDate() != null) {
                    dod = dodTxt.getFormat().format(dodTxt.getDate());
                } else {
                    dodTxt.setError("Invalid Date");
                    dodTxt.requestFocus();
                    return;
                }

                CardModelClass newScientist=new CardModelClass(name,description,dod);
                crudHelper.insert(this,db,mProgressBar,newScientist);

            }
        }

        private void updateData() {
            String name, description,dod;
            if (Utlis.validate(nameTxt, descriptionTxt)) {
                name = nameTxt.getText().toString();
                description = descriptionTxt.getText().toString();

                if (dodTxt.getDate() != null) {
                    dod = dodTxt.getFormat().format(dodTxt.getDate());
                } else {
                    dodTxt.setError("Invalid Date");
                    dodTxt.requestFocus();
                    return;
                }

                CardModelClass newScientist=new CardModelClass(name,description,dod);
                crudHelper.update(this,db,mProgressBar,receivedScientist,newScientist);

            }
        }

        private void deleteData() {
            crudHelper.delete(this,db,mProgressBar,receivedScientist);
        }


        @Override
        public void onBackPressed() {
            Utlis.showInfoDialog(this, "Warning", "Are you sure you want to exit?");
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            if (receivedScientist == null) {
                getMenuInflater().inflate(R.menu.new_item_menu, menu);
                headerTxt.setText("Add New Scientist");
            } else {
                getMenuInflater().inflate(R.menu.edit_menu_item, menu);
                headerTxt.setText("Edit Existing Scientist");
            }
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.insertMenuItem:
                    insertData();
                    return true;
                case R.id.editMenuItem:
                    if (receivedScientist != null) {
                        updateData();
                    } else {
                        Utlis.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                    }
                    return true;
                case R.id.deleteMenuItem:
                    if (receivedScientist != null) {
                        deleteData();
                    } else {
                        Utlis.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                    }
                    return true;
                case R.id.viewAllMenuItem:
                    Utlis.openActivity(this, CardModel.class);
                    finish();
                    return true;
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        /**
         * Attach Base Context
         */
        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        }

        /**
         * When our activity is resumed we will receive our data and set them to their editing
         * widgets.
         */
        @Override
        protected void onResume() {
            super.onResume();
            CardModelClass o = Utlis.receiveCardModelClass(getIntent(), c);
            if (o != null) {
                receivedScientist = o;
                nameTxt.setText(receivedScientist.getName());
                descriptionTxt.setText(receivedScientist.getDesc());


                Object dod = receivedScientist.getDod();
                if (dod != null) {
                    dodTxt.setDate(Utlis.giveMeDate(dod.toString()));
                }
            } else {
                //Utils.show(c,"Received Scientist is Null");
            }
        }

        /**
         * Let's override our onCreate() method
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crud);

            this.initializeWidgets();

        }
    }
//end


