package com.dmitri.mobileclientservice.di.modules;

import androidx.room.Room;

import com.dmitri.mobileclientservice.App;
import com.dmitri.mobileclientservice.api.MobileClientService;
import com.dmitri.mobileclientservice.data.database.AppDatabase;
import com.dmitri.mobileclientservice.data.repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Singleton
    @Provides
    AppDatabase providesDatabase(App app) {
        return Room.databaseBuilder(app, AppDatabase.class, "appDatabase").build();
    }

    @Singleton
    @Provides
    Repository providesRepository(MobileClientService service, AppDatabase database) {
        return new Repository(service, database);
    }
}