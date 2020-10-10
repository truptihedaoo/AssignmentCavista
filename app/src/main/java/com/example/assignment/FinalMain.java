package com.example.assignment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.assignment.databinding.ActivityMainBinding;

public class FinalMain extends AppCompatActivity {
    FinalVM mViewModel;
    public static Context mContext;
    ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingOfWidgets();
    }

    private void bindingOfWidgets() {
        mContext = FinalMain.this;
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new FinalVM(((Activity) mContext), mBinding);
            }
        };
        mViewModel = ViewModelProviders.of(this, factory).get(FinalVM.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setSplashViewModel(mViewModel);

        mViewModel.setRecycler(this);

        mViewModel.hideKeyboard(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.hideKeyboard(this);
    }
}

