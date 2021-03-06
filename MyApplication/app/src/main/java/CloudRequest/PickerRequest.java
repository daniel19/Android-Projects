package CloudRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dbi.myapplication.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * This class will use the Google Cloud Json API to request
 * objects stored in the cloud.
 */
public class PickerRequest extends AsyncTask<String, Void, String> {
    public static final String API_KEY = "";
    public static final String MAIN_URL = "https://www.googleapis.com/storage/v1";
    public static final String GET_URL = "";

    public JSONObject jsonResponse;
    public final MainActivity activity;
    private ProgressDialog progressDialog;
    public PickerRequest(MainActivity activity){
        jsonResponse = null;
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected String doInBackground(String... params) {
       String data = "";
       try{
           data = download(params[0]);
       }catch(IOException ex){
           Log.d("PICKER", ex.getMessage());
       }
        return data;
    }
    @Override
    protected void onPostExecute(String result){
        activity.setListView(jsonResponse);
        FileOutputStream out;
        try{
            //Save downloaded file to directory
            out = activity.openFileOutput("list.json", Context.MODE_PRIVATE);
            byte[] bytes = result.getBytes();
            out.write(bytes, 0, bytes.length);
            out.close();
        }catch(IOException ex) {
            Log.e("PICKER", "Error writing to file. " + ex.getMessage());
        }
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Gathering Parts Data.");
        progressDialog.show();
    }


    private String download(String url) throws IOException{
        InputStream inputStream = null;
        String result = "FAILED";
        URL local_url = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) local_url.openConnection();
        try{
            connection.setReadTimeout(10000); //10 second timeout
            connection.setConnectTimeout(15000); //15 second timeout
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();

            inputStream = connection.getInputStream();

            jsonResponse = convertData(inputStream);
            result = jsonResponse.toString();
        }catch(IOException|JSONException ex) {
            result += ex.getMessage();
        }finally{
            connection.disconnect();
        }
        return result;
    }

    private JSONObject convertData(InputStream inputStream) throws IOException, JSONException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuilder builder = new StringBuilder();
        String input;
        while((input = reader.readLine()) != null){
            builder.append(input);
        }
        return new JSONObject(builder.toString());
    }

}
