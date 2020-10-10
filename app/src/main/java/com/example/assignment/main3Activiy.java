package com.example.assignment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class main3Activiy extends AppCompatActivity {
    private GridView gridView;
    private JSONArray sampleJsonarray;

    String url = "https://api.imgur.com/3/gallery/search/1?q=shapes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        gridView = (GridView) findViewById( R.id.grid_game );

        GridAsyncTask asyncTask = new GridAsyncTask();
        asyncTask.execute( url );
    }

    private void createGridView() {
        // Create grid adapter
        GridAdapter productAdapter = new GridAdapter( this, sampleJsonarray );
        // Set grid adapter into GridView
        gridView.setAdapter( productAdapter );
    }


    private class GridAsyncTask extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result = null;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL( stringUrl );
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod( REQUEST_METHOD );
                connection.setReadTimeout( READ_TIMEOUT );
                connection.setConnectTimeout( CONNECTION_TIMEOUT );
                connection.addRequestProperty("Authorization","Client-ID 137cda6b5008a7c");
//                                    .addHeader("Authorization", "Client-ID 137cda6b5008a7c")

                //Connect to our url
                connection.connect();
                InputStreamReader streamReader = new
                        InputStreamReader( connection.getInputStream() );
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader( streamReader );
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append( inputLine );
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
//                String sampleJsonString = result.toString();
                try {
                    JSONObject rootObject = new JSONObject(result.toString());

                    sampleJsonarray = new JSONArray( rootObject.getString("data") );
                Log.d("sampleJsonarray",String.valueOf(sampleJsonarray));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute( result );
            createGridView();
        }
    }
}

