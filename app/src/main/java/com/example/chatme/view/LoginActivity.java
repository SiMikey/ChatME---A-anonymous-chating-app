package com.example.chatme.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.chatme.R;
import com.example.chatme.databinding.ActivityLoginBinding;
import com.example.chatme.viewmodel.ViewModel;

public class LoginActivity extends AppCompatActivity {

    ViewModel viewModel;
    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setVModel(viewModel);
    }
}