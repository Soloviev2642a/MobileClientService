package com.dmitri.mobileclientservice.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dmitri.mobileclientservice.data.model.LoginEntry;
import com.dmitri.mobileclientservice.data.model.User;
import com.dmitri.mobileclientservice.api.response.UserLoginResponse;
import com.dmitri.mobileclientservice.data.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";

    private Repository repository;

    public MutableLiveData<List<LoginEntry>> loginEntries =
            new MutableLiveData<>();

    @Inject
    public UserViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getLoginEntries() {
        Observable<List<LoginEntry>> response = repository.getLoginEntries();
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLoginEntriesObserver());
    }

    private Observer<List<LoginEntry>> getLoginEntriesObserver() {
        return new Observer<List<LoginEntry>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<LoginEntry> entries) {
                loginEntries.postValue(entries);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                loginEntries.postValue(null);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void saveLoginEntry(User user, UserLoginResponse userLoginResponse) {
        LoginEntry entry = new LoginEntry(user.getUser(), userLoginResponse.code);
        repository.saveLoginEntry(entry);
    }
}
