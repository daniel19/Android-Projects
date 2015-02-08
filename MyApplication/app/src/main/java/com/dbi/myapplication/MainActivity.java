package com.dbi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private boolean filePresent = false;
    private ArrayList <String> keyList;
    private ListView listView = null;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }catch (IOException | JSONException ex){
            Log.e("PICKER", "Unable to process cache file." + ex.getMessage());
        }
        connectToJson(filePresent);

       mNavigationDrawerFragment = (NavigationDrawerFragment)
              getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        if(listView != null) {
            mTitle = (String) listView.getItemAtPosition(number);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
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

                //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mTitle = getTitle();
                listView = (ListView) findViewById(R.id.left_drawer);
                listView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, keyList));
                listView.setOnItemClickListener(new PartsListener());


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
            Intent subIntent = new Intent(MainActivity.this, SubList.class);
            subIntent.putExtra("Name", item);
            //drawerLayout.closeDrawer(listView);
            MainActivity.this.startActivity(subIntent);

        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            //((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

}
