package com.dmitri.mobileclientservice.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dmitri.mobileclientservice.data.model.LoginEntry;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface LoginEntryDAO {
    @Query("SELECT * FROM loginEntry")
    Observable<List<LoginEntry>> getAll();

    @Insert
    void insertAll(LoginEntry... entries);

    @Delete
    void delete(LoginEntry entry);
}
