package com.test.fliprhack_trello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CardModel extends AppCompatActivity implements SearchView.OnQueryTextListener,MenuItem.OnActionExpandListener {

    private RecyclerView rv;
    public ProgressBar mProgressBar;
    private DatabaseACT_CRUD crudHelper=new DatabaseACT_CRUD();
    private LinearLayoutManager layoutManager;
    MyAdapter adapter;

    private void initializeViews(){
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        Utlis.showProgressBar(mProgressBar);
        rv = findViewById(R.id.mRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                layoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        adapter=new MyAdapter(this,Utlis.DataCache);
        rv.setAdapter(adapter);

    }

    private void bindData(){
        crudHelper.select(this,Utlis.getDatabaseRefence(),mProgressBar,rv,adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.card_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_new:
                Utlis.sendCardModelClassToActivity(this,null,CRUDActivity.class);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        Utlis.searchString=query;
        MyAdapter adapter=new MyAdapter(this,Utlis.DataCache);
        adapter.getFilter().filter(query);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_model);

        initializeViews();
        bindData();
    }

}
