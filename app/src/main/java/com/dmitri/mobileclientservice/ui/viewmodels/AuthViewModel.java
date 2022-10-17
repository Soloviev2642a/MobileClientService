package com.dmitri.mobileclientservice.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dmitri.mobileclientservice.data.model.User;
import com.dmitri.mobileclientservice.api.response.UserLoginResponse;
import com.dmitri.mobileclientservice.api.response.UserResponse;
import com.dmitri.mobileclientservice.data.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private Repository repository;

    private MutableLiveData<List<User>> users;
    private MutableLiveData<Boolean> loginStatus;
    public UserLoginResponse userLoginResponse;

    @Inject
    public AuthViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
        }
        return users;
    }

    public void requestUsers() {
        Observable<UserResponse> response = repository.getUsers();
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUsersObserver());
    }

    private Observer<UserResponse> getUsersObserver() {
        return new Observer<UserResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull UserResponse userResponse) {
                users.postValue(userResponse.users.listUsers);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                users.postValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public MutableLiveData<Boolean> getLoginStatus() {
        if (loginStatus == null) {
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }

    public void userLogin(String uid, String password) {
        Log.d(TAG, "Attempting to login with \nuid: " + uid + "\npassword: " + password);
        repository.userLogin(uid, password).enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, retrofit2.Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Login Successful: code = " + response.body().code);
                    userLoginResponse = response.body();
                    loginStatus.setValue(true);
                } else {
                    Log.d(TAG, "Login Failed: " + response);
                    loginStatus.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d(TAG, "Request Failed: " + t.getMessage());
                loginStatus.setValue(false);
            }
        });
    }
}
