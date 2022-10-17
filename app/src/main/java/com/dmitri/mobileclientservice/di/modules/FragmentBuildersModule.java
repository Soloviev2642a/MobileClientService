package com.dmitri.mobileclientservice.di.modules;

import com.dmitri.mobileclientservice.ui.fragments.AuthFragment;
import com.dmitri.mobileclientservice.ui.fragments.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract AuthFragment contributeAuthFragment();
    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();
}
