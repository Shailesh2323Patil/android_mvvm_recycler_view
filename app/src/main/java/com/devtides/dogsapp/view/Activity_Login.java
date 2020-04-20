package com.devtides.dogsapp.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;


import com.devtides.dogsapp.R;
import com.devtides.dogsapp.databinding.ActivityLoginBinding;
import com.devtides.dogsapp.model.Authentication;
import com.devtides.dogsapp.viewmodel.ViewModel_Login;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Activity_Login extends AppCompatActivity
{
    Context context;
    private ActivityLoginBinding binding;
    private ViewModel_Login viewModel_login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel_login = ViewModelProviders.of(this).get(ViewModel_Login.class);

        binding = DataBindingUtil.setContentView(Activity_Login.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewModelLogin(viewModel_login);

        context = this;

        setObservers();
    }

    private void setObservers()
    {
        viewModel_login.getAuthentication().observe(this, new Observer<Authentication>()
        {
            @Override
            public void onChanged(Authentication authentication)
            {
                if(TextUtils.isEmpty(Objects.requireNonNull(authentication).getStrUsername()))
                {
                    binding.idEditUserName.setError(context.getString(R.string.enter_email));
                    binding.idEditUserName.requestFocus();
                }
                else if(!authentication.isEmailValid())
                {
                    binding.idEditUserName.setError(context.getString(R.string.enter_valid_email));
                    binding.idEditUserName.requestFocus();
                }
                else if(TextUtils.isEmpty(Objects.requireNonNull(authentication).getStrPassword()))
                {
                    binding.idEditUserPassword.setError(context.getString(R.string.enter_password));
                    binding.idEditUserPassword.requestFocus();
                }
                else if(!authentication.isPasswordValid())
                {
                    binding.idEditUserPassword.setError(context.getString(R.string.enter_valid_password));
                    binding.idEditUserPassword.requestFocus();
                }
                else
                {
                    viewModel_login.executeLoginDetails(authentication);
                }
            }
        });

        viewModel_login.progressBar.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean progressBar) {

                if(progressBar != null && (progressBar instanceof Boolean))
                {
                    binding.idProgressBar.setVisibility(progressBar ? View.VISIBLE : View.GONE);
                }
            }
        });

        binding.idSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                binding.idEditUserName.setText("");
                binding.idEditUserPassword.setText("");
                binding.idSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
