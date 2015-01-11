package com.dbi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import CloudRequest.PickerRequest;

/**
 * main class will be the front page controller.
 */
public class main extends ActionBarActivity {
    ListView listView = null;
    boolean filePresent = false;
    ArrayList<String> keyList;
    JSONObject jsonResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.partsList);

        listView.setOnItemClickListener(new PartsListener());
        //Load Json file from GCS
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            //file is present
            try{
                FileInputStream inputStream = openFileInput("list.json");
                byte[] result = new byte[inputStream.available()];
                int bytesRead = inputStream.read(result,0,inputStream.available());
                setListView(new JSONObject(new String(result)));
                filePresent = true;
                Log.d("PICKER", "File Present");
            }catch (IOException| JSONException ex){
                Log.e("PICKER", "Unable to process cache file." + ex.getMessage());
            }
        if (networkInfo != null && networkInfo.isConnected() && !filePresent){
            //Create PickerRequest object
            PickerRequest pickerRequest = new PickerRequest(getApplicationContext(), this);
            //pickerRequest.execute(PickerRequest.MAIN_URL + PickerRequest.GET_URL + "?key=" +PickerRequest.API_KEY);
            pickerRequest.execute("https://storage.googleapis.com/json_parts/list.json");
        }else if(!filePresent) {
            Toast.makeText(getApplicationContext(), "Not connected to a network.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setListView(JSONObject jsonResponse) {
        this.jsonResponse = jsonResponse;
        try {
            if(jsonResponse != null) {
                //A dictionary with a JSONArray array of arrays.
               keyList = new ArrayList<String>();
               Iterator<String> keys = jsonResponse.keys();
               while(keys.hasNext()){
                    keyList.add(keys.next());
               }
                ArrayAdapter partsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, keyList);
                listView.setAdapter(partsAdapter);

            }else{
                Toast.makeText(getApplicationContext(), "No Data was returned.", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(), "A parsing error occurred.", Toast.LENGTH_LONG).show();
        }
    }

    private class PartsListener implements AdapterView.OnItemClickListener{
        /*
            Values are stored as a dictionary that contains an array of arrays;
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = keyList.get(position);
            try {
                JSONArray itemarray = (JSONArray)jsonResponse.get(item);
                Intent subIntent = new Intent(main.this, SubList.class);
                ArrayList<String> itemList = new ArrayList<String>();
                for(int i =0; i< itemarray.length(); i++){
                    if(itemarray.get(i) != null) {
                        itemList.add(itemarray.get(i).toString());
                    }
                }
                subIntent.putExtra("Name", item);
                subIntent.putExtra("Object List", itemList);
                main.this.startActivity(subIntent);
            }catch (JSONException ex){
                Log.e("PICKER", "Can't access inner objects. " + ex.getMessage());
            }
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

        return super.onOptionsItemSelected(item);
    }
}
