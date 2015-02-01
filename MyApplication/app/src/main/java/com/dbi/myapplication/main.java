package com.dbi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import CloudRequest.PickerRequest;


/**
 * main class will be the front page controller.
 */
public class main extends ActionBarActivity {
    private ListView listView = null;
    private boolean filePresent = false;
    private ArrayList<String> keyList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence mTitle, mDrawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meun_drawer);

        //Check to see if file is present
        try{
            FileInputStream inputStream = openFileInput("list.json");
            byte[] result = new byte[inputStream.available()];
            inputStream.read(result,0,inputStream.available());

            setListView(new JSONObject(new String(result)));
            filePresent = true;

            //Check to see if file is more than 24hrs old
            File json = getFileStreamPath("list.json");
            Date lastMod = new Date(json.lastModified());
            long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L; //24 hours in milliseconds
            if(Math.abs(lastMod.getTime() - new Date().getTime()) >= MILLIS_PER_DAY){
                filePresent = false;
            }
        }catch (IOException| JSONException ex){
            Log.e("PICKER", "Unable to process cache file." + ex.getMessage());
        }
        connectToJson(filePresent);

    }

    private void connectToJson(boolean filePresent) {
        //Check for network connectivity
        //Load Json file from GCS
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && !filePresent){
            //Create PickerRequest object
            PickerRequest pickerRequest = new PickerRequest(this);
            //pickerRequest.execute(PickerRequest.MAIN_URL + PickerRequest.GET_URL + "?key=" +PickerRequest.API_KEY);
            //pickerRequest.execute("https://storage.googleapis.com/json_parts/list.json");
            pickerRequest.execute("https://storage.googleapis.com/json_parts/newList.json");
        }else if(!filePresent) {
            Toast.makeText(getApplicationContext(), "Not connected to a network.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setListView(JSONObject jsonResponse) {
        try {
            if(jsonResponse != null) {
                //A dictionary with a JSONArray array of arrays.
                keyList = new ArrayList<String>();
                Iterator<String> keys = jsonResponse.keys();
                while(keys.hasNext()){
                    keyList.add(keys.next());
                }
                /*listView = (ListView) findViewById(R.id.partsList);
                listView.setOnItemClickListener(new PartsListener());
                ArrayAdapter partsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, keyList);
                listView.setAdapter(partsAdapter);*/

                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mTitle = mDrawerTitle = getTitle();
                listView = (ListView) findViewById(R.id.left_drawer);
                listView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, keyList));
                listView.setOnItemClickListener(new PartsListener());

                drawerToggle = new ActionBarDrawerToggle(
                        this,                  /* host Activity */
                        drawerLayout,         /* DrawerLayout object */
                        (android.support.v7.widget.Toolbar)findViewById(R.id.my_awesome_toolbar),  /* nav drawer icon to replace 'Up' caret */
                        R.string.drawer_open,  /* "open drawer" description */
                        R.string.drawer_close  /* "close drawer" description */
                ) {

                    /** Called when a drawer has settled in a completely closed state. */
                    public void onDrawerClosed(View view) {
                        super.onDrawerClosed(view);
                        //getActionBar().setTitle(mTitle);
                    }

                    /** Called when a drawer has settled in a completely open state. */
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                        //getActionBar().setTitle(mDrawerTitle);
                    }
                };

                // Set the drawer toggle as the DrawerListener
                drawerLayout.setDrawerListener(drawerToggle);

            }else{
                Toast.makeText(getApplicationContext(), "No Data was returned.", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(), "A parsing error occurred.", Toast.LENGTH_LONG).show();
            Log.e("PICKER",ex.getMessage());
        }
    }

    private class PartsListener implements AdapterView.OnItemClickListener{
        /*
            Values are stored as a dictionary that contains an array of arrays;
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = keyList.get(position);
            Intent subIntent = new Intent(main.this, SubList.class);
            subIntent.putExtra("Name", item);
            drawerLayout.closeDrawer(listView);
            main.this.startActivity(subIntent);

        }
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
            return true;
        }
        if(id == R.id.refresh){
            connectToJson(false);
        }

        return super.onOptionsItemSelected(item);
    }
}
