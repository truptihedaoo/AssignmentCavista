package com.example.assignment.final_Main;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.assignment.GridAdapter;
import com.example.assignment.RestAdapter;
import com.example.assignment.RetrofitApiV2;
import com.example.assignment.databinding.ActivityMainBinding;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class finalVM extends ViewModel {
    ActivityMainBinding binding;
    public static Context mContext;
    private JSONArray sampleJsonarray;
    private LinearLayoutManager linearLayoutManager;
    public finalVM(Activity mContext, ActivityMainBinding binding) {
        this.binding = binding;
        this.mContext = mContext;
    }

    public void apicall(String vanilla) {

        RetrofitApiV2 retrofit = RestAdapter.createAPI();


        Call call= retrofit.getCash();

        call.enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));

                    sampleJsonarray = new JSONArray(jsonObject.getString("data"));
//                    JSONObject obj = new JSONObject();
////                    JSONArray jsonArray = SingleProduct.getJSONArray("images");
////                    obj = new JSONObject(String.valueOf(sampleJsonarray.getJSONObject(0)));
//                    JSONObject obj21 = new JSONObject();
//                    for (int i = 0; i < sampleJsonarray.length(); i++) {
//                        obj21 = (sampleJsonarray.getJSONObject(i));
//
//                        JSONArray imageArray = obj21.getJSONArray("images");
//                        Log.d("samplearray", String.valueOf(imageArray));
//                    }

//                    JSONArray jsonArray = new JSONArray();
//                    for (int i = 0; i < sampleJsonarray.length(); i++) {
//                        JSONObject obj21;
//                        obj21 = new JSONObject(sampleJsonarray.getJSONObject(i));
//                        jsonArray.put(obj21);
//                    }


//                    Log.d("samplearray", String.valueOf(jsonArray));

                    createGridView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void createGridView() {
        // Create grid adapter
        GridAdapter productAdapter = new GridAdapter(mContext, sampleJsonarray);
        // Set grid adapter into GridView
        binding.gridGame.setAdapter(productAdapter);
    }

//    public LiveData<ArrayList<OrderList>> getAllOrder() {
//        mEvonixUtil.showLoader(mContext);
//        return getMutableLiveData();
//    }

    public void setRecycler(finalMain homeFragment) {
        /*//Comment: Client requirement-> take static statuses not from api -> 11Sept2019
        getStatus().observe(homeFragment, new Observer<List<OrderStatusWrapper.Status>>() {
            @Override
            public void onChanged(@Nullable List<OrderStatusWrapper.Status> statusList) {
                StatusAdapter adapter = new StatusAdapter(mContext, statusList);
                mBinding.recyclerView.setAdapter(adapter);
            }
        });*/

//        getAllOrder().observe(homeFragment, new Observer<ArrayList<OrderList>>() {
//            @Override
//            public void onChanged(@Nullable ArrayList<OrderList> orderLists) {
//                linearLayoutManager = new LinearLayoutManager(mContext);
//                listBinding.progressBarLoadMore.setVisibility(View.GONE);
//                listBinding.recyclerView.setLayoutManager(linearLayoutManager);
//                adapter = new HomeAdapter(mContext, orderLists);
//                listBinding.recyclerView.setAdapter(adapter);
//                listBinding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
//                        android.R.color.holo_blue_bright,
//                        android.R.color.holo_orange_light,
//                        android.R.color.holo_red_light);
//                listBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        listBinding.searchView.setVisibility(View.GONE);
//                        searchField = "";
//                        offSet = 0;
//                        getMutableLiveData();
//                    }
//                });
//
//                listBinding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//                    @Override
//                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                        if (offSet <= totalRecord) {
//                            offSet = offSet + visibleThreshold;
//                            getMutableLiveData();
//                        }
//                    }
//                });
//            }
//        });
    }


}
