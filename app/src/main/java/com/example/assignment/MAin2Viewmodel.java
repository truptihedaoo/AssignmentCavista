package com.example.assignment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.example.assignment.databinding.ActivityMain2Binding;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;

public class MAin2Viewmodel extends ViewModel {

    ActivityMain2Binding binding;
    Activity activity;
    Call<JsonElement> call;
    public String searchField = "";
    Util util;
    JSONArray jsonArray;

    public MAin2Viewmodel(Activity activity) {
        this.activity = activity;
    }

//    public MAin2Viewmodel(Activity activity) {
//        super(activity);
//    }


    public void setContentView(int activity_main) {
        binding = DataBindingUtil.setContentView(activity, activity_main);
        util = new Util(activity);

        initSearchView();

    }

    private void initSearchView() {
        binding.searchView.setQueryHint("Search here");
        binding.searchView.setIconified(true);
        binding.searchView.onActionViewExpanded();
        binding.searchView.clearFocus();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 1 || newText.trim().length() == 0) {
                    if (call != null && call.isExecuted()) {
                        if (newText.trim().equals("")) {
                            return true;
                        }
                        call.cancel();
                    }
                    searchField = newText.trim();
                    jsonArray = new JSONArray();
                    if (util.isNetworkConnected()) {

                        downloadCuisinesList("search");
                    } else {
                        Toast.makeText(activity, "not connected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    searchField = "";
                    jsonArray = new JSONArray();
                    if (util.isNetworkConnected()) {

                        downloadCuisinesList("search");
                    } else {
                        Toast.makeText(activity, "not connected", Toast.LENGTH_SHORT).show();
                    }
                }

                ImageView searchViewIcon = (ImageView) binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);

                searchViewIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.searchView.setQuery("", false);
                        searchField = "";
                        jsonArray = new JSONArray();
                        if (util.isNetworkConnected()) {

                        downloadCuisinesList("");
                        } else {
                            Toast.makeText(activity, "not connected", Toast.LENGTH_SHORT).show();
                        }
//                        util.hideKeyboard();
                    }
                });
                return true;

            }
        });
    }

    public void downloadCuisinesList(final String searchtxt) {
        try {


            RetrofitApiV2 retrofit = RestAdapter.createAPI();

            call = retrofit.getCuisineType(
                    searchField
            );

            call.enqueue(new retrofit2.Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                    try {
                        JSONObject rootObject = new JSONObject(response.body().toString());
                        Log.d("rootobj",String.valueOf(rootObject));
//                        if (response.isSuccessful() && rootObject.has("status")) {
//                            switch (rootObject.getInt("status")) {
//                                case MyNetworkConstant.RESULT_OK:
//                                    jsonArray = rootObject.getJSONArray("cuisinStatus");
//                                    loadPizza(jsonArray);
//                                    break;
//                                case MyNetworkConstant.RESULT_FAIL:
//                                    rootObject = new JSONObject(response.errorBody().string());
//                                    mEvonixUtil.TES(rootObject.getString("errorMessage"));
//                                    break;
//                                case MyNetworkConstant.RESULT_LOGOUT:
//                                    rootObject = new JSONObject(response.errorBody().string());
//                                    mEvonixUtil.TES(rootObject.getString("errorMessage"));
//                                    activity.finish();
//                                    mEvonixUtil.setLogout();
//                                    activity.startActivity(new Intent(activity, SplashActivity.class));
//                                    break;
//                            }
//                        }

                    } catch (Exception e) {

//                        mEvonixUtil.LE(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    try {

                        if (searchtxt.equalsIgnoreCase("search")) {
                        } else {
//                            mEvonixUtil.TEL(R.string.error_to_api);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {

//            mEvonixUtil.LE(e.getMessage());
        }
    }


}
