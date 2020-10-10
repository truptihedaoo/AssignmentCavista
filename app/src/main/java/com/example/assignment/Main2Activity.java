

package com.example.assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
   /* MAin2Viewmodel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectInitialization();
        bindingOfWidgets();
    }

    private void bindingOfWidgets() {
        viewModel.setContentView(R.layout.activity_main2);
    }

    private void objectInitialization() {
        viewModel = new MAin2Viewmodel(Main2Activity.this);
    }*/

    OkHttpClient client;
    MediaType JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
    }

    public void makeGetRequest(View v) throws IOException {


//        Authenticator.setDefault(new Authenticator(){
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("Authorization","Client-ID 137cda6b5008a7c".toCharArray());
//            }});
//        HttpURLConnection c = (HttpURLConnection) new URL("https://api.imgur.com/3/gallery/search/1?q=shape").openConnection();
//        c.setUseCaches(false);
//        c.connect();

        Authenticator.setDefault (new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication ("Authorization", "password".toCharArray());
            }
        });


        GetTask task = new GetTask();

        task.execute();
    }

    public class GetTask extends AsyncTask {
        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                String getResponse = get("https://publicobject.com/helloworld.txt");
                Log.d("getResponse",getResponse);
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String getResponse) {
            System.out.println(getResponse);
        }

        public String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Client-ID 137cda6b5008a7c")
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                String getResponse = get("https://api.imgur.com/3/gallery/search/1?q=shape");
                Log.d("getResponse",getResponse);

                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }        }
    }

    public void makePostRequest(View v) throws IOException {
        PostTask task = new PostTask();
        task.execute();
    }

    public class PostTask extends AsyncTask {
        private Exception exception;

//        protected String doInBackground(String... urls) {
//            try {
//                String getResponse = post("http://www.roundsapp.com/post", bowlingJson("Me", "You"));
//                return getResponse;
//            } catch (Exception e) {
//                this.exception = e;
//                return null;
//            }
//        }

        protected void onPostExecute(String getResponse) {
            System.out.println(getResponse);
        }

        private String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        protected Object doInBackground(Object[] objects) {
            try {
                String getResponse = post("http://www.roundsapp.com/post", bowlingJson("Me", "You"));
                Log.d("getResponse",getResponse);

                GridView grid_game = findViewById(R.id.grid_game);
                grid_game.getAdapter();
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
    }
    public String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }
}


