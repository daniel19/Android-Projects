package com.dbi.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import CloudRequest.PickerRequest;

/**
 * main class will be the front page controller.
 */
public class main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*final ListView listview = (ListView) findViewById(R.id.partsList);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);*/
        //Load Json file from GCS
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            //Create PickerRequest object
            PickerRequest pickerRequest = new PickerRequest(getApplicationContext(), this);
            //pickerRequest.execute(PickerRequest.MAIN_URL + PickerRequest.GET_URL + "?key=" +PickerRequest.API_KEY);
            pickerRequest.execute("https://storage.googleapis.com/json_parts/list.json");
        }else{
            Toast.makeText(getApplicationContext(), "Not connected to a network.", Toast.LENGTH_SHORT).show();
        }

    }

    public void setListView(JSONObject jsonResponse) {
        //Get CPU List
        try {
            if(jsonResponse != null) {
                //A dictionary with a JSONArray array of arrays.
               JSONArray cpuList = (JSONArray) jsonResponse.get("cpu");

            }else{
                Toast.makeText(getApplicationContext(), "No Data was returned.", Toast.LENGTH_SHORT).show();
            }

        }catch(JSONException ex){
            Toast.makeText(getApplicationContext(), "A parsing error occurred.", Toast.LENGTH_LONG).show();
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
