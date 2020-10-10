package com.example.assignment.final_Main;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.assignment.GridAdapter;
import com.example.assignment.R;
import com.example.assignment.RestAdapter;
import com.example.assignment.RetrofitApiV2;
import com.example.assignment.databinding.ActivityMainBinding;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class finalMain extends AppCompatActivity {
/*
    private GridView gridView;
    private JSONArray sampleJsonarray;
    String url = "https://api.imgur.com/3/gallery/search/1?q=shapes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.grid_game);
        apicall("vanilla");

//        GridAsyncTask asyncTask = new GridAsyncTask();
//        asyncTask.execute( url );
    }

    private void createGridView() {
        // Create grid adapter
        GridAdapter productAdapter = new GridAdapter(this, sampleJsonarray);
        // Set grid adapter into GridView
        gridView.setAdapter(productAdapter);
    }


*/
    finalVM mViewModel;
    public static Context mContext;
    ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingOfWidgets();
    }

    private void bindingOfWidgets() {
        mContext = finalMain.this;
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new finalVM(((Activity) mContext), mBinding);
            }
        };
        mViewModel = ViewModelProviders.of(this, factory).get(finalVM.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setSplashViewModel(mViewModel);
        mViewModel.setRecycler(this);
    }

}

