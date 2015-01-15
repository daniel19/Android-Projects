package com.dbi.myapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Model.*;


public class SubList extends ActionBarActivity {
    ListView subListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("Name").toUpperCase());
        JSONArray response;
        try {
            response = new JSONArray(intent.getStringExtra("Object List"));
            ArrayList<Parts> list = new ArrayList<>();
            list = grabItems(intent.getStringExtra("Name"), response);
            subListView = (ListView) findViewById(R.id.sublist);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            subListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @param title
     * @param itemArray
     * @return ArrayList<Parts>
     */
    private ArrayList<Parts> grabItems(String title, JSONArray itemArray) {
        ArrayList<Parts> partsArraylist = new ArrayList<>();
        Drawable ironMan;
        AssetManager assetManager = getResources().getAssets();
        try{
            InputStream is = assetManager.open("iron.jpg");
            ironMan = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            switch (title){
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
                            String price = localArray.getString(localArray.length() - 2);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return partsArraylist;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_list, menu);
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
