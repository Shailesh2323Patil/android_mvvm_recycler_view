package com.devtides.dogsapp.viewmodel;

import android.util.Log;
import android.view.View;

import com.devtides.dogsapp.model.Authentication;
import com.devtides.dogsapp.model.retrofit.ApiService;
import com.google.gson.JsonObject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ViewModel_Login extends ViewModel
{
    String TAG = ViewModel_Login.class.toString();
    ApiService apiService = new ApiService();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<String> username = new MutableLiveData<String>();
    public MutableLiveData<String> password = new MutableLiveData<String>();

    public MutableLiveData<Authentication> authenticationMutableLiveData;

    public MutableLiveData<Boolean> progressBar = new MutableLiveData<Boolean>();

    public MutableLiveData<Authentication> getAuthentication()
    {
        if(authenticationMutableLiveData == null)
        {
            authenticationMutableLiveData = new MutableLiveData<>();
        }

        return authenticationMutableLiveData;
    }

    public void onClick(View view)
    {
        Authentication authentication = new Authentication(username.getValue(),password.getValue());

        authenticationMutableLiveData.setValue(authentication);
    }

    public void executeLoginDetails(Authentication authentication)
    {
        progressBar.setValue(true);
        compositeDisposable.add(
                apiService.apiLogin(authentication.getStrUsername(),authentication.getStrPassword())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject)
                    {
                        progressBar.setValue(false);
                        Log.d(TAG,jsonObject.toString());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        progressBar.setValue(false);
                        Log.e(TAG,e.getMessage());
                    }
                })
        );
    }
}
