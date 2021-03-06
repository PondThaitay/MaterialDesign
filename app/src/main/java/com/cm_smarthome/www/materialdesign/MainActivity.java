package com.cm_smarthome.www.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.AbstractCollection;


public class MainActivity extends ActionBarActivity {

    Context context = this;
    private Toolbar toolbar;

    private String username;
    private String name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appber);

        getUsernameToFragment();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setLogo(R.drawable.up_logo);
        toolbar.setTitle("มหาวิทยาลัยพะเยา");
        toolbar.setSubtitle("ระบบการบริการการศึกษา");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.NavigationDrawer);
        navigationDrawerFragment.setUp(R.id.NavigationDrawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getUsernameToFragment(){
        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        return username;
    }

    public String getNameToFragment(){
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        return name;
    }

    public String getEmailToFragment(){
        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        return email;
    }

}