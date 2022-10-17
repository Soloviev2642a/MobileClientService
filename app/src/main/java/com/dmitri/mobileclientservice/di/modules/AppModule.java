package com.dmitri.mobileclientservice.di.modules;

import dagger.Module;

@Module(
    includes = {
        ViewModelModule.class,
        NetworkModule.class,
        DataModule.class
    }
)
public class AppModule {
}
