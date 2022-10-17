package com.dmitri.mobileclientservice.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dmitri.mobileclientservice.data.database.dao.LoginEntryDAO;
import com.dmitri.mobileclientservice.data.model.LoginEntry;

@Database(entities = {LoginEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LoginEntryDAO loginEntryDAO();
}

