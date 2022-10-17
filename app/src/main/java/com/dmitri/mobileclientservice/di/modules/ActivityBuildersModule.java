package com.dmitri.mobileclientservice.di.modules;

import com.dmitri.mobileclientservice.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
        modules = {
            FragmentBuildersModule.class
        }
    )
    public abstract MainActivity contributeMainActivity();
}
