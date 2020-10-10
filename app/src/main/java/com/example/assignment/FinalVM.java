package com.example.assignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.assignment.databinding.ActivityMainBinding;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalVM extends ViewModel {
    ActivityMainBinding mBinding;
    public  Activity mContext;
    KProgressHUD progressHUD;

    Call<OrderWrapper> call = null;
    private static ArrayList<OrderList> orderList = new ArrayList<>();
    private static ArrayList<ArrayList<ImageList>> imageList = new ArrayList<ArrayList<ImageList>>();
    private MutableLiveData<ArrayList<OrderList>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ArrayList<ImageList>>> mutableLiveDataimage = new MutableLiveData<ArrayList<ArrayList<ImageList>>>();

    private String searchField = "vanilla";
    public static Handler h;
    JSONArray array;

    GridAdapterFinal productAdapter;


    public FinalVM(Activity mContext, ActivityMainBinding binding) {
        this.mBinding = binding;
        this.mContext = mContext;
        hideKeyboard(mContext);
        initSearchView();
    }


    public void initSearchView() {
                mBinding.searchView.setQueryHint("Search here");
        mBinding.searchView.setIconified(false);
//        mBinding.searchView.onActionViewExpanded();
        mBinding.searchView.clearFocus();
        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 2) {
                    if (call != null && call.isExecuted()) {
                        if (newText.trim().equals("")) {
                            return true;
                        }
                        call.cancel();
                    }
                    searchField = newText.trim();

                    notifyList();
                    orderList.clear();
                    imageList.clear();
                    orderList = new ArrayList<>();
                    imageList = new ArrayList<ArrayList<ImageList>>();
                    array = new JSONArray();
                    getMutableLiveData(searchField);
                }
                ImageView searchViewIcon = (ImageView) mBinding.searchView.findViewById(R.id.search_close_btn);

                searchViewIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBinding.searchView.setQuery("", false);
                        progressHUD.show();


                        orderList.clear();
                        imageList.clear();
                        orderList = new ArrayList<>();
                        imageList = new ArrayList<ArrayList<ImageList>>();
                        array = new JSONArray();
                        getMutableLiveData(searchField);
                        notifyList();
                    }
                });
                return true;

            }
        });
        mBinding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mBinding.searchView.setQuery("", false);
                searchField = "vanilla";
                progressHUD.show();
//                notifyList();
                orderList.clear();
                imageList.clear();
                orderList = new ArrayList<>();
                imageList = new ArrayList<ArrayList<ImageList>>();
                array = new JSONArray();
                getMutableLiveData(searchField);
                hideKeyboard(mContext);
                notifyList();
                return true;
            }
        });
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public LiveData<ArrayList<OrderList>> getAllOrder() {
        progressHUD = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(mContext.getResources().getColor(R.color.transparentColor))
                .setLabel(mContext.getResources().getString(R.string.please_wait))
                .setCancellable(false)
                .setDimAmount(0.5f);
        progressHUD.show();
        return getMutableLiveData(searchField);
    }

    public MutableLiveData<ArrayList<OrderList>> getMutableLiveData(String searchField) {
        RetrofitApiV2 retrofit = RestAdapter.createAPI();


        call = retrofit.getCashTest("1?q=" + searchField);

        call.enqueue(new Callback<OrderWrapper>() {
            @Override
            public void onResponse(Call<OrderWrapper> call, Response<OrderWrapper> response) {
                OrderWrapper mBlogWrapper = response.body();

                progressHUD.dismiss();
                OrderList orderModel = null;
                try {
                    if (response.isSuccessful()) {
                        if (mBlogWrapper != null) {

                            if (mBlogWrapper.getOrderList() != null) {
                                orderList = (ArrayList<OrderList>) mBlogWrapper.getOrderList();
                                mutableLiveData.setValue(orderList);

                                for (int i = 0; i < orderList.size(); i++) {
                                    imageList.add((ArrayList<ImageList>) orderList.get(i).getFoodList());

                                }

                                array = new JSONArray();


                                for (int i = 0; i < orderList.size(); i++) {
                                    if (orderList.get(i).getFoodList() != null && orderList.get(i).getFoodList().size() > 0) {
                                        for (int j = 0; j < orderList.get(i).getFoodList().size(); j++) {
                                            JSONObject object = new JSONObject();
                                            object.put("image", imageList.get(i).get(j).getLink());
                                            object.put("title", orderList.get(i).getTitle());
                                            array.put(object);
                                        }
                                    }
                                }


                                mutableLiveDataimage.setValue(imageList);


                            } else {
                                orderList = (ArrayList<OrderList>) mBlogWrapper.getOrderList();
                            }
                        }
                        notifyList();
                    } else {

                        progressHUD.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OrderWrapper> call, Throwable t) {
                progressHUD.dismiss();
            }
        });
        return mutableLiveData;
    }


    @SuppressLint("RestrictedApi")
    public void notifyList() {

        Log.d("sggdfg",String.valueOf(orderList));
        if (orderList != null || String.valueOf(orderList) != "[]") {
            mBinding.txtNoData.setVisibility(View.GONE);
            productAdapter = new GridAdapterFinal(mContext, array);
            // Set grid adapter into GridView
            mBinding.gridGame.setAdapter(productAdapter);
        } else {
            mBinding.gridGame.setVisibility(View.GONE);
            mBinding.txtNoData.setVisibility(View.VISIBLE);
        }
        progressHUD.dismiss();

    }

    public void setRecycler(FinalMain main) {

        hideKeyboard(main);
        getAllOrder().observe(main, new Observer<ArrayList<OrderList>>() {
            @Override
            public void onChanged(@Nullable ArrayList<OrderList> orderLists) {
//                notifyList();
//                orderList.clear();
//                imageList.clear();
//                orderList = new ArrayList<>();
//                imageList = new ArrayList<ArrayList<ImageList>>();
//                array = new JSONArray();
//                getMutableLiveData(searchField);
            }
        });
    }


}
