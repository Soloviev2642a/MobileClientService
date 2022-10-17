package com.dmitri.mobileclientservice.data.repository;

import com.dmitri.mobileclientservice.api.MobileClientService;
import com.dmitri.mobileclientservice.data.database.AppDatabase;
import com.dmitri.mobileclientservice.data.model.LoginEntry;
import com.dmitri.mobileclientservice.api.response.UserLoginResponse;
import com.dmitri.mobileclientservice.api.response.UserResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class Repository {
    private MobileClientService service;
    private AppDatabase database;

    @Inject
    public Repository(MobileClientService service, AppDatabase database) {
        this.service = service;
        this.database = database;
    }

    public Observable<UserResponse> getUsers() {
        return service.getUsers();
    }

    public Call<UserLoginResponse> userLogin(String uid, String password) {
        return service.userLogin(uid, password, false, "");
    }

    public Observable<List<LoginEntry>> getLoginEntries() {
        return database.loginEntryDAO().getAll();
    }

    public void saveLoginEntry(LoginEntry entry) {
        database.loginEntryDAO().insertAll(entry);
    }

    public void deleteAllDatabaseTables() {
        Completable.fromRunnable(() ->
            database.clearAllTables())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
