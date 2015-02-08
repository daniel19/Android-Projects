package com.dbi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.CPU;
import Model.Case;
import Model.Memory;
import Model.MotherBoard;
import Model.Parts;
import Model.Power;
import Model.Storage;
import Model.VideoCard;


public class SubList extends ActionBarActivity {
    private ListView subListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("Name").toUpperCase());
        JSONArray response = loadJSONArray(intent.getStringExtra("Name"));
        ArrayList<Parts> list = grabItems(intent.getStringExtra("Name"), response);
        subListView = (ListView) findViewById(R.id.sublist);
        PartsArrayAdapter adapter = new PartsArrayAdapter(this,R.layout.custom_listview,list);
        subListView.setAdapter(adapter);
    }

    private JSONArray loadJSONArray(String name) {
        JSONArray array = null;
        try {
            FileInputStream inputStream = openFileInput("list.json");
            byte[] result = new byte[inputStream.available()];
            inputStream.read(result,0,inputStream.available());
            JSONObject jsonObject = new JSONObject(new String(result));
            array = (JSONArray)jsonObject.get(name);
        } catch (IOException|JSONException e) {
            Log.e("PICKER", "No Array created: " + e.getMessage());
        }
        return array;
    }

    /**
     * Pares the items to create and use the correct class for the selected part.
     * @param title
     * @param itemArray
     * @return ArrayList<Parts>
     */
    private ArrayList<Parts> grabItems(String title, JSONArray itemArray) {
        ArrayList<Parts> partsArraylist = new ArrayList<>();

        try {
            switch (title){
                case "case":
                    for(int i=0; i<itemArray.length(); i++){
                        JSONArray localArray = (JSONArray)itemArray.get(i);
                        if(!localArray.getString(0).equals("null")) {
                            String name = localArray.get(0).toString();
                            String price = localArray.getString(localArray.length() - 2);
                            String type = localArray.get(1).toString();
                            String cext = localArray.get(2).toString();
                            String cint = localArray.get(3).toString();
                            String powerSupply = localArray.getString(4);
                            String imageUrl = localArray.getString(localArray.length()-1);
                            Case mCase = new Case(name, price, imageUrl, type, cext, cint, powerSupply);
                            partsArraylist.add(mCase);
                        }
                    }
                    break;
                case "cpu":
                    for(int i=0; i<itemArray.length(); i++){
                        JSONArray localArray = (JSONArray)itemArray.get(i);
                        if(!localArray.getString(0).equals("null")) {
                            String name = localArray.get(0).toString();
                            String price = localArray.getString(localArray.length() - 2);
                            String speed = localArray.getString(1);
                            String cores = localArray.getString(2);
                            String imageUrl = localArray.getString(localArray.length()-1);

                            CPU mCPU = new CPU(name, price, imageUrl, cores, speed);
                            partsArraylist.add(mCPU);
                        }
                    }
                    break;
                case "power":
                    for(int i=0; i<itemArray.length(); i++){
                        JSONArray localArray = (JSONArray)itemArray.get(i);
                        if(!localArray.getString(0).equals("null")) {
                            String name = localArray.get(0).toString();
                            String price = localArray.getString(localArray.length() - 2);
                            String series = localArray.get(1).toString();
                            String form = localArray.get(2).toString();
                            String efficeincy = localArray.get(3).toString();
                            String watts = localArray.getString(4);
                            String modular = localArray.get(5).toString();
                            String imageUrl = localArray.getString(localArray.length()-1);
                            Power mPower = new Power(name, price, imageUrl, series, form, efficeincy,
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
                            String price = localArray.getString(localArray.length() - 2);
                            String socket = localArray.get(1).toString();
                            String form = localArray.get(2).toString();
                            String ram = localArray.getString(3);
                            String maxRam = localArray.get(4).toString();
                            String imageUrl = localArray.getString(localArray.length()-1);
                            MotherBoard mMother = new MotherBoard(name, price, imageUrl, socket, form, ram,
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
                            String price = localArray.getString(localArray.length() - 2);
                            String series = localArray.get(1).toString();
                            String form = localArray.get(2).toString();
                            String type = localArray.get(3).toString();
                            String capacity = localArray.getString(4);
                            String cache = localArray.get(5).toString();
                            String ppg = localArray.getString(6);
                            String imageUrl = localArray.getString(localArray.length()-1);
                            Storage mStorage = new Storage(name, price, imageUrl, series, form, type,
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
                            String price = localArray.getString(localArray.length() - 3);
                            String speed = localArray.get(1).toString();
                            String type = localArray.get(2).toString();
                            String cas = localArray.getString(3);
                            String modules = localArray.get(4).toString();
                            String size = localArray.getString(5);
                            String ppg = localArray.getString(6);
                            String imageUrl = localArray.getString(localArray.length()-1);
                            Memory mMemory = new Memory(name, price, imageUrl, speed, type, cas,
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
                            String price = localArray.getString(localArray.length() - 2);
                            String series = localArray.get(1).toString();
                            String chipset = localArray.get(2).toString();
                            String memory = localArray.getString(3);
                            String speed = localArray.getString(4);
                            String imageUrl = localArray.getString(localArray.length()-1);
                            VideoCard mVideo = new VideoCard(name, price, imageUrl, series, chipset,
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

    /**
     * Subclass of ArraryAdapter that displays the Part information.
     */
    public class PartsArrayAdapter extends ArrayAdapter<Parts>{
        class PartsHolder{
            ImageView partImageView;
            TextView partNameView;
            TextView partCostView;
            String url;
            Bitmap map;
        }
        public PartsArrayAdapter(Context context, int resource, List<Parts> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PartsHolder holder = new PartsHolder();
            if(convertView == null){
                //inflate custom xml
                LayoutInflater inflater = (LayoutInflater) this.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_listview,parent,false);
                holder.partCostView = (TextView) convertView.findViewById(R.id.partCost);
                holder.partNameView = (TextView) convertView.findViewById(R.id.partName);
                holder.partImageView = (ImageView) convertView.findViewById(R.id.partsImg);
                convertView.setTag(holder);
            }else{
                holder = (PartsHolder) convertView.getTag();
            }
            Parts part = getItem(position);
            holder.partCostView.setText(part.cost);
            holder.partNameView.setText(part.name);
            holder.url = part.imageURL;
            new DownloadAsyncTask().execute(holder);
            //holder.partImageView.setImageResource(part.image);
            return convertView;
        }

        class DownloadAsyncTask extends AsyncTask<PartsHolder, Void, PartsHolder>{
            @Override
            protected PartsHolder doInBackground(PartsHolder... params) {
                PartsHolder parts = params[0];
                Bitmap bitmap = null;
                try{
                    URL imageURL = new URL(parts.url);
                    bitmap = BitmapFactory.decodeStream(imageURL.openStream());
                    parts.map = bitmap;
                }catch (IOException ex){
                    Log.d("PICKER", "URL Error: " + ex.getMessage());
                }
                return parts;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(PartsHolder holder) {
                if(holder.map == null){
                    holder.partImageView.setImageResource(R.drawable.iron);
                }else{
                    holder.partImageView.setImageBitmap(holder.map);
                }
            }
        }
    }
}
