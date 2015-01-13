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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import CloudRequest.PickerRequest;
import Model.*;

/**
 * main class will be the front page controller.
 */
public class main extends ActionBarActivity {
    private ListView listView = null;
    private boolean filePresent = false;
    private ArrayList<String> keyList;
    private JSONObject jsonResponse;
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
            long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
            if(Math.abs(lastMod.getTime() - new Date().getTime()) >= MILLIS_PER_DAY){
                filePresent = false;
            }
        }catch (IOException| JSONException ex){
            Log.e("PICKER", "Unable to process cache file." + ex.getMessage());
        }
        //Check for network connectivity
        if (networkInfo != null && networkInfo.isConnected() && !filePresent){
            //Create PickerRequest object
            PickerRequest pickerRequest = new PickerRequest(this);
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
            ArrayList<Parts> partsArraylist = new ArrayList<>();
            String item = keyList.get(position);
            try {
                JSONArray itemArray = (JSONArray)jsonResponse.get(item);
                Intent subIntent = new Intent(main.this, SubList.class);
                switch (item){
                    case "case":
                        for(int i=0; i<itemArray.length(); i++){
                            JSONArray localArray = (JSONArray)itemArray.get(i);
                            if(!localArray.getString(0).equals("null")) {
                                String name = localArray.get(0).toString();
                                String price = localArray.getString(localArray.length() - 1);
                                String type = localArray.get(1).toString();
                                String cext = localArray.get(2).toString();
                                String cint = localArray.get(3).toString();
                                String powerSupply = localArray.getString(4);
                                Case mCase = new Case(name, price, null, type, cext, cint, powerSupply);
                                partsArraylist.add(mCase);
                            }
                        }
                        break;
                    case "cpu":
                        for(int i=0; i<itemArray.length(); i++){
                            JSONArray localArray = (JSONArray)itemArray.get(i);
                            if(!localArray.getString(0).equals("null")) {
                                String name = localArray.get(0).toString();
                                String price = localArray.getString(localArray.length() - 1);
                                String speed = localArray.getString(1);
                                String cores = localArray.getString(2);

                                CPU mCPU = new CPU(name, price, null, cores, speed);
                                partsArraylist.add(mCPU);
                            }
                        }
                        break;
                    case "power":
                        for(int i=0; i<itemArray.length(); i++){
                            JSONArray localArray = (JSONArray)itemArray.get(i);
                            if(!localArray.getString(0).equals("null")) {
                                String name = localArray.get(0).toString();
                                String price = localArray.getString(localArray.length() - 1);
                                String series = localArray.get(1).toString();
                                String form = localArray.get(2).toString();
                                String efficeincy = localArray.get(3).toString();
                                String watts = localArray.getString(4);
                                String modular = localArray.get(5).toString();
                                Power mPower = new Power(name, price, null, series, form, efficeincy,
                                        watts, modular);
                                partsArraylist.add(mPower);
                            }
                        }
                        break;
                    case "motherboard":
                        for(int i=0; i<itemArray.length(); i++){
                            JSONArray localArray = (JSONArray)itemArray.get(i);
                            if(!localArray.getString(0).equals("null")) {
                                String name = localArray.get(0).toString();
                                String price = localArray.getString(localArray.length() - 1);
                                String socket = localArray.get(1).toString();
                                String form = localArray.get(2).toString();
                                String ram = localArray.getString(3);
                                String maxRam = localArray.get(4).toString();
                                MotherBoard mMother = new MotherBoard(name, price, null, socket, form, ram,
                                        maxRam);
                                partsArraylist.add(mMother);
                            }
                        }
                        break;
                    case "storage":
                        for(int i=0; i<itemArray.length(); i++){
                            JSONArray localArray = (JSONArray)itemArray.get(i);
                            if(!localArray.getString(0).equals("null")) {
                                String name = localArray.get(0).toString();
                                String price = localArray.getString(localArray.length() - 1);
                                String series = localArray.get(1).toString();
                                String form = localArray.get(2).toString();
                                String type = localArray.get(3).toString();
                                String capacity = localArray.getString(4);
                                String cache = localArray.get(5).toString();
                                String ppg = localArray.getString(6);
                                Storage mStorage = new Storage(name, price, null, series, form, type,
                                        capacity, cache, ppg);
                                partsArraylist.add(mStorage);
                            }
                        }
                        break;
                    case "memory":
                        for(int i=0; i<itemArray.length(); i++){
                            JSONArray localArray = (JSONArray)itemArray.get(i);
                            if(!localArray.getString(0).equals("null")) {
                                String name = localArray.get(0).toString();
                                String price = localArray.getString(localArray.length() - 1);
                                String speed = localArray.get(1).toString();
                                String type = localArray.get(2).toString();
                                String cas = localArray.getString(3);
                                String modules = localArray.get(4).toString();
                                String size = localArray.getString(5);
                                String ppg = localArray.getString(6);
                                Memory mMemory = new Memory(name, price, null, speed, type, cas,
                                        modules, size, ppg);
                                partsArraylist.add(mMemory);
                            }
                        }
                        break;
                    case "video":
                        for(int i=0; i<itemArray.length(); i++){
                            JSONArray localArray = (JSONArray)itemArray.get(i);
                            if(!localArray.getString(0).equals("null")) {
                                String name = localArray.get(0).toString();
                                String price = localArray.getString(localArray.length() - 1);
                                String series = localArray.get(1).toString();
                                String chipset = localArray.get(2).toString();
                                String memory = localArray.getString(3);
                                String speed = localArray.getString(4);
                                VideoCard mVideo = new VideoCard(name, price, null, series, chipset,
                                        memory, speed);
                                partsArraylist.add(mVideo);
                            }
                        }
                        break;
                }

                subIntent.putExtra("Name", item);
                subIntent.putExtra("Object List", partsArraylist);
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
